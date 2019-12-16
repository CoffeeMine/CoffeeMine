package org.coffeemine.app.spring.db;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.coffeemine.app.spring.data.*;
import org.dizitart.no2.Nitrite;

import java.util.Random;
import java.util.stream.Stream;

import static org.dizitart.no2.filters.Filters.*;

public class NitriteDBProvider implements DBProvider {
    static private NitriteDBProvider instance = null;
    private Nitrite db;


    public NitriteDBProvider(String filename) {
        db = Nitrite.builder()
                .compressed()
                .filePath(filename)
                .openOrCreate();

        if(!db.hasCollection("CoffeeMine"))
            setupCollection();
    }

    public static void init(String filename){
        instance = new NitriteDBProvider(filename);
    }

    public static DBProvider getInstance() {
        return instance;
    }

    private void setupCollection() {
        db.getCollection("CoffeeMine");
        db.getCollection("users");
        db.getCollection("projects");
        db.getCollection("sprints");
        db.getCollection("tasks");
        db.getCollection("fragments");
    }

    @Override
    public void importJSONProject(String json) {
        final var factory = new JreJsonFactory();
        final JsonObject obj = factory.parse(json);

        db.getCollection("projects").insert(new Project().readJson(obj).asNO2Doc());

        final var jsprints = obj.getArray("sprints");
        final var db_sprints = db.getCollection("sprints");
        for (int i = 0; i < jsprints.length(); ++i)
            db_sprints.insert(new Sprint().readJson(jsprints.getObject(i)).asNO2Doc());

        final var jtasks = obj.getArray("tasks");
        final var db_tasks = db.getCollection("tasks");
        for (int i = 0; i < jtasks.length(); ++i)
            db_tasks.insert(new Task().readJson(jtasks.getObject(i)).asNO2Doc());

        final var jfrags = obj.getArray("fragments");
        final var db_fragments = db.getCollection("fragments");
        for (int i = 0; i < jfrags.length(); ++i)
            db_fragments.insert(new Fragment().readJson(jfrags.getObject(i)).asNO2Doc());

    }

    @Override
    public Stream<Project> getProjects() {
        return db.getCollection("projects")
                .find().toList().stream().map(d -> new Project().fromNO2Doc(d));
    }

    private Stream<ISprint> getSprints() {
        return db.getCollection("sprints")
                .find().toList().stream().map(d -> new Sprint().fromNO2Doc(d));
    }

    private Stream<ITask> getTasks() {
        return db.getCollection("tasks")
                .find().toList().stream().map(d -> new Task().fromNO2Doc(d));
    }

    private Stream<Fragment> getFragments() {
        return db.getCollection("fragments")
                .find().toList().stream().map(d -> new Fragment().fromNO2Doc(d));
    }

    @Override
    public Stream<ISprint> getSprints4Project(Project project) {
        return db.getCollection("sprints")
                .find(in("id", project.getSprints().toArray()))
                .toList().stream().map(d -> new Sprint().fromNO2Doc(d));
    }

    @Override
    public Stream<ITask> getTasks4Project(Project project) {
        final var task_ids = getSprints4Project(project)
                .flatMap(s -> s.getTasks().stream()).toArray();
        return db.getCollection("tasks")
                .find(in("id", task_ids))
                .toList().stream().map(d -> new Task().fromNO2Doc(d));
    }

    @Override
    public Stream<ITask> getTasks4Sprint(ISprint sprint) {
        return db.getCollection("tasks")
                .find(in("id", sprint.getTasks().toArray()))
                .toList().stream().map(d -> new Task().fromNO2Doc(d));
    }

    @Override
    public Stream<Fragment> getFragments4Task(ITask task) {
        return db.getCollection("fragments")
                .find(in("id", task.getFragments().toArray()))
                .toList().stream().map(d -> new Fragment().fromNO2Doc(d));
    }

    @Override
    public Stream<Fragment> getFragments4User(User user) {
        return db.getCollection("fragments")
                .find(elemMatch("users", eq("$", user.getId())))
                .toList().stream().map(d -> new Fragment().fromNO2Doc(d));
    }

    @Override
    public Stream<User> getUsers() {
        return db.getCollection("users").find().toList().stream().map(d -> new User().fromNO2Doc(d));
    }

    @Override
    public User getUser(int id) {
        return new User().fromNO2Doc(db.getCollection("users").find(eq("id", id)).firstOrDefault());
    }

    @Override
    public Project getProject(int id) {
        return new Project().fromNO2Doc(db.getCollection("projects").find(eq("id", id)).firstOrDefault());
    }

    @Override
    public void addProject(Project project) {
        db.getCollection("projects").insert(project.asNO2Doc());
        db.commit();
    }

    @Override
    public void addSprint(Sprint sprint) {
        db.getCollection("sprints").insert(sprint.asNO2Doc());
        db.commit();
    }

    @Override
    public void addTask(Task task) {
        db.getCollection("tasks").insert(task.asNO2Doc());
        db.commit();
    }

    @Override
    public void addFragment(Fragment fragment) {
        db.getCollection("fragments").insert(fragment.asNO2Doc());
        db.commit();
    }

    @Override
    public void addUser(User user) {
        db.getCollection("users").insert(user.asNO2Doc());
        db.commit();
    }

    @Override
    public Integer account_id(String name, String hashpass) {
        final var res = db.getCollection("users")
                .find(and(eq("account_name", name), eq("account_passhash", hashpass)));
        if(res.totalCount() == 1)
            return res.firstOrDefault().get("id", Integer.class);

        if(res.totalCount() > 1)
            throw new RuntimeException("DB has multiple users with the same names and passwords");
        return null;
    }

    @Override
    public Integer idFor(Class<?> c) {
        final Integer v = new Random().nextInt();

        if(c.equals(User.class))
            return getUsers().map(User::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Project.class))
            return getProjects().map(Project::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Sprint.class))
            return getSprints().map(ISprint::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Task.class))
            return getTasks().map(ITask::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Fragment.class))
            return getFragments().map(Fragment::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        throw new UnsupportedOperationException();
    }
}
