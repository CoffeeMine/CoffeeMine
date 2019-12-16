package org.coffeemine.app.spring.db;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.coffeemine.app.spring.data.*;

import java.time.LocalDate;
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
    public Stream<Project> getProjects() {
        return projects.stream();
    }

    @Override
    public Stream<ISprint> getSprints4Project(Project project) {
        return sprints.stream().filter(sprint -> project.getSprints().contains(sprint.getId())).map(t -> t);
    }

    @Override
    public Stream<ITask> getTasks4Project(Project project) {
        final var task_ids = getSprints4Project(project)
                .flatMap(s -> s.getTasks().stream())
                .collect(Collectors.toList());
        return tasks.stream().filter(task -> task_ids.contains(task.getId())).map(t -> t);
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
    public Stream<Fragment> getFragments4User(User user) {
        return fragments.stream().filter(fragment -> fragment.getUsers().contains(user.getId()));
    }

    @Override
    public Project getCurrentProject(User user) {
        return this.getProject(user.getCurrentProject());
    }
    
    @Override
    public ISprint getCurrentSprint(Project project) {
        var now = LocalDate.now();
        return this.sprints.stream()
                .dropWhile(sprint -> !(sprint.getStart().isBefore(now) && sprint.getEnd().isAfter(now))).findFirst()
                .orElse(null);
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
    public Project getProject(int id) {
        return projects.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Sprint getSprint(int id) {
        return sprints.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    @Override
    public Task getTask(int id) {
        return tasks.stream().filter(s -> s.getId() == id).findFirst().orElse(null);
    }

    @Override
    public void addProject(Project project) {
        projects.add(project);
    }

    @Override
    public void addSprint(Sprint sprint) {
        sprints.add(sprint);
    }

    @Override
    public void addTask(Task task) {
        tasks.add(task);
    }
  
    @Override
    public void addFragment(Fragment fragment) {
        fragments.add(fragment);
    }

    @Override
    public void addUser(User user) {
        users.add(user);
    }

    @Override
    public Integer account_id(String name, String hashpass) {
        final var acc = users.stream()
                .filter(u -> (u.getAccountName().equalsIgnoreCase(name) && u.getAccountPasshash().equals(hashpass)))
                .findFirst();
        return acc.isPresent() ? acc.get().getId() : null;
    }

    @Override
    public Integer idFor(Class<?> c) {
        final Integer v = new Random().nextInt();

        if(c.equals(User.class))
            return getUsers().map(User::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Project.class))
            return getProjects().map(Project::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Sprint.class))
            return sprints.stream().map(ISprint::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Task.class))
            return tasks.stream().map(ITask::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Fragment.class))
            return fragments.stream().map(Fragment::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        throw new UnsupportedOperationException();
    }

    public static DBProvider getInstance(){
        return instance;
    }
}
