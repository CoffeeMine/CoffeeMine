package org.coffeemine.app.spring.db;

import org.coffeemine.app.spring.data.*;
import org.dizitart.no2.Nitrite;

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

    }

    @Override
    public Stream<Project> getProjects() {
        return db.getCollection("projects")
                .find().toList().stream().map(d -> new Project().fromNO2Doc(d));
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
}
