package org.coffeemine.app.spring.db;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.coffeemine.app.spring.data.*;

import java.util.ArrayList;
import java.util.stream.Stream;

/**
 * Dumb in-memory database
 * */
public class BasicDBProvider implements DBProvider {
    static private BasicDBProvider instance = new BasicDBProvider();

    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<Sprint> sprints = new ArrayList<>();
    private ArrayList<Task> tasks = new ArrayList<>();
    private ArrayList<Fragment> fragments = new ArrayList<>();
    private ArrayList<User> users = new ArrayList<>();

    @Override
    public void importJSONProject(String json) {
        final var factory = new JreJsonFactory();
        final JsonObject obj = factory.parse(json);

        projects.add(new Project().readJson(obj));

        final var jtasks = obj.getArray("tasks");
        tasks.ensureCapacity(jtasks.length());
        for (int i = 0; i < jtasks.length(); ++i)
            tasks.add(new Task().readJson(jtasks.getObject(i)));

        final var jfrags = obj.getArray("fragments");
        fragments.ensureCapacity(jfrags.length());
        for (int i = 0; i < jfrags.length(); ++i)
            fragments.add(new Fragment().readJson(jfrags.getObject(i)));

    }

    @Override
    public ArrayList<Project> getProjects() {
        return projects;
    }

    @Override
    public Stream<ISprint> getSprints4Project(Project project) {
        return sprints.stream().filter(sprint -> project.getSprints().contains(sprint.getId())).map(t -> t);
    }

    @Override
    public Stream<ITask> getTasks4Project(Project project) {
        final var sprints = getSprints4Project(project);
        return tasks.stream().filter(task -> sprints.anyMatch(s -> s.getTasks().contains(task.getId()))).map(t -> t);
    }

    @Override
    public Stream<ITask> getTasks4Sprint(ISprint sprint) {
        return tasks.stream().filter(task -> sprint.getTasks().contains(task.getId())).map(t -> t);
    }

    @Override
    public Stream<Fragment> getFragments4Task(ITask task) {
        return fragments.stream().filter(fragment -> task.getFragments().contains(fragment.getId()));
    }

    @Override
    public ArrayList<User> getUsers() {
        return users;
    }

    @Override
    public User getUser(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Integer account_id(String name, String hashpass) {
        final var acc = users.stream()
                .filter(u -> (u.getAccountName().equalsIgnoreCase(name) && u.getAccountPasshash().equals(hashpass)))
                .findFirst();
        return acc.isPresent() ? acc.get().getId() : null;
    }


    public static DBProvider getInstance(){
        return instance;
    }
}
