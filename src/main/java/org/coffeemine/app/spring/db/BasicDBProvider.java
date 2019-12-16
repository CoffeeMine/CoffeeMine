package org.coffeemine.app.spring.db;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.coffeemine.app.spring.data.*;

import java.util.ArrayList;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Dumb in-memory database
 * */
public class BasicDBProvider implements DBProvider {
    static private BasicDBProvider instance = new BasicDBProvider();

    private ArrayList<Project> projects = new ArrayList<>();
    private ArrayList<ISprint> sprints = new ArrayList<>();
    private ArrayList<ITask> tasks = new ArrayList<>();
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
    public Stream<Project> getProjects() {
        return projects.stream();
    }

    @Override
    public Stream<ISprint> getSprints() {
        return null;
    }

    @Override
    public Stream<ITask> getTasks() {
        return null;
    }

    @Override
    public Stream<ISprint> getSprints4Project(Project project) {
        return sprints.stream().filter(sprint -> project.getSprints().contains(sprint.getId()));
    }

    @Override
    public Stream<ITask> getTasks4Project(Project project) {
        final var task_ids = getSprints4Project(project)
                .flatMap(s -> s.getTasks().stream())
                .collect(Collectors.toList());
        return tasks.stream().filter(task -> task_ids.contains(task.getId()));
    }

    @Override
    public Stream<ITask> getTasks4Sprint(ISprint sprint) {
        return tasks.stream().filter(task -> sprint.getTasks().contains(task.getId()));
    }

    @Override
    public Stream<Fragment> getFragments4Task(ITask task) {
        return fragments.stream().filter(fragment -> task.getFragments().contains(fragment.getId()));
    }

    @Override
    public Stream<Fragment> getFragments4User(User user) {
        return fragments.stream().filter(fragment -> fragment.getUsers().contains(user.getId()));
    }

    @Override
    public Stream<User> getUsers() {
        return users.stream();
    }

    @Override
    public User getUser(int id) {
        return users.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public int addUser(User user) {
        users.add(user);
        return user.getId();
    }

    @Override
    public ISprint getSprint(int id) {
        return sprints.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public int addSprint(ISprint sprint) {
        sprints.add(sprint);
        return sprint.getId();
    }

    @Override
    public ITask getTask(int id) {
        return tasks.stream().filter(u -> u.getId() == id).findFirst().orElse(null);
    }

    @Override
    public int addTask(ITask task) {
        tasks.add(task);
        return task.getId();
    }

    @Override
    public Integer account_id(String name, String hashpass) {
        final var acc = users.stream()
                .filter(u -> (u.getAccountName().equalsIgnoreCase(name) && u.getAccountPasshash().equals(hashpass)))
                .findFirst();
        return acc.isPresent() ? acc.get().getId() : null;
    }

    public Integer idFor(Class<?> c) {
        final Integer v = new Random().nextInt();

        if(c.equals(User.class))
            return getUsers().map(User::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(ISprint.class))
            return sprints.stream().map(ISprint::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(ITask.class))
            return tasks.stream().map(ITask::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Fragment.class))
            return fragments.stream().map(Fragment::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        throw new UnsupportedOperationException();
    }

    public static DBProvider getInstance(){
        return instance;
    }
}
