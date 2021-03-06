package org.coffeemine.app.spring.db;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.coffeemine.app.spring.data.*;
import org.dizitart.no2.Document;
import org.dizitart.no2.Nitrite;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.dizitart.no2.filters.Filters.*;

public class NitriteDBProvider implements DBProvider {
    static private NitriteDBProvider instance = null;
    private Nitrite db;
    private boolean first_run = false;

    public NitriteDBProvider(String filename) {
        db = Nitrite.builder()
                .compressed()
                .filePath(filename)
                .openOrCreate();

        if (!db.hasCollection("CoffeeMine")) {
            setupCollection();
            first_run = true;
        }
    }

    public static void init(String filename) {
        if (instance == null)
            instance = new NitriteDBProvider(filename);
    }

    public static NitriteDBProvider getInstance() {
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

    public boolean is_first_run() {
        return first_run;
    }

    @Override
    public void importJSONProject(String json) {
        final var factory = new JreJsonFactory();
        final JsonObject obj = factory.parse(json);

        addProject(new Project().readJson(obj));

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

        db.commit();
    }

    public String exportJSONProject(Project project) {
        final var factory = new JreJsonFactory();
        final var obj = project.toJson();

        {
            final var jsprints = factory.createArray();
            final var sprints = getSprints4Project(project).collect(Collectors.toList());
            int i = 0;
            while (i < sprints.size())
                jsprints.set(i, sprints.get(i++).toJson());

            obj.put("sprints", jsprints);
        }

        {
            final var jtasks = factory.createArray();
            final var tasks = getTasks4Project(project).collect(Collectors.toList());
            int i = 0;
            while (i < tasks.size())
                jtasks.set(i, tasks.get(i++).toJson());

            obj.put("tasks", jtasks);
        }

        {
            final var jfrags = factory.createArray();
            final var frags = getTasks4Project(project).flatMap(this::getFragments4Task).collect(Collectors.toList());
            int i = 0;
            while (i < frags.size())
                jfrags.set(i, frags.get(i++).toJson());

            obj.put("fragments", jfrags);
        }

        return obj.toJson();
    }

    @Override
    public Stream<Project> getProjects() {
        return db.getCollection("projects")
                .find().toList().stream().map(d -> new Project().fromNO2Doc(d));
    }

    public Stream<ISprint> getSprints() {
        return db.getCollection("sprints")
                .find().toList().stream().map(d -> new Sprint().fromNO2Doc(d));
    }

    public Stream<ITask> getTasks() {
        return db.getCollection("tasks")
                .find().toList().stream().map(d -> new Task().fromNO2Doc(d));
    }

    private Stream<Fragment> getFragments() {
        return db.getCollection("fragments")
                .find().toList().stream().map(d -> new Fragment().fromNO2Doc(d));
    }

    @Override
    public Stream<ISprint> getSprints4Project(Project project) {
        if (project.getSprints().isEmpty())
            return Stream.empty();
        return db.getCollection("sprints")
                .find(in("id", project.getSprints().toArray()))
                .toList().stream().map(d -> new Sprint().fromNO2Doc(d));
    }

    @Override
    public Stream<ITask> getTasks4Project(Project project) {
        final var task_ids = getSprints4Project(project)
                .flatMap(s -> s.getTasks().stream()).toArray();
        if (task_ids.length == 0)
            return Stream.empty();
        return db.getCollection("tasks")
                .find(in("id", task_ids))
                .toList().stream().map(d -> new Task().fromNO2Doc(d));
    }

    @Override
    public Stream<ITask> getTasks4Sprint(ISprint sprint) {
        if (sprint.getTasks().isEmpty())
            return Stream.empty();
        return db.getCollection("tasks")
                .find(in("id", sprint.getTasks().toArray()))
                .toList().stream().map(d -> new Task().fromNO2Doc(d));
    }

    @Override
    public Stream<Fragment> getFragments4Task(ITask task) {
        if (task.getFragments().isEmpty())
            return Stream.empty();
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
    public Project getCurrentProject(User user) {
        if (user == null)
            return null;
        final var currentProject = getProject(user.getCurrentProject());
        return (currentProject != null) ? currentProject : getProjects().findAny().get();
    }

    @Override
    public ISprint getCurrentSprint(Project project) {
        var now = LocalDate.now();
        return this.getSprints4Project(project)
                .dropWhile(sprint -> !sprint.getStart().isBefore(now))
                .min(Comparator.comparing(ISprint::getStart))
                .orElse(null);
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
    public void updateUser(User user) {
        db.getCollection("users").update(eq("id", user.getId()), user.asNO2Doc());
    }

    @Override
    public Project getProject(int id) {
        return new Project().fromNO2Doc(db.getCollection("projects").find(eq("id", id)).firstOrDefault());
    }

    @Override
    public void updateProject(Project project) {
        db.getCollection("projects").update(eq("id", project.getId()), project.asNO2Doc());
    }

    @Override
    public int addProject(Project project) {
        final var doc = project.asNO2Doc();
        final var id = idFor(Project.class);
        doc.replace("id", id);
        db.getCollection("projects").insert(doc);
        db.commit();
        return id;
    }

    @Override
    public int addFragment(Fragment fragment) {
        final var doc = fragment.asNO2Doc();
        final var id = idFor(Fragment.class);
        doc.replace("id", id);
        db.getCollection("fragments").insert(doc);
        db.commit();
        return id;
    }

    @Override
    public int addUser(User user) {
        final var doc = user.asNO2Doc();
        final var id = user.getId() == -1 ? idFor(User.class) : user.getId();
        doc.replace("id", id);
        db.getCollection("users").insert(doc);
        db.commit();
        return id;
    }

    @Override
    public ISprint getSprint(int id) {
        return new Sprint().fromNO2Doc(db.getCollection("sprints").find(eq("id", id)).firstOrDefault());
    }

    @Override
    public int addSprint(ISprint sprint) {
        final var doc = sprint.asNO2Doc();
        final var id = idFor(ISprint.class);
        doc.replace("id", id);
        db.getCollection("sprints").insert(doc);
        db.commit();
        return id;
    }

    @Override
    public void removeSprint(int id) {
        db.getCollection("sprints").remove(eq("id", id));
    }

    @Override
    public void updateSprint(ISprint sprint) {
        db.getCollection("sprints").update(eq("id", sprint.getId()), sprint.asNO2Doc());
    }

    @Override
    public ITask getTask(int id) {
        return new Task().fromNO2Doc(db.getCollection("tasks").find(eq("id", id)).firstOrDefault());
    }

    @Override
    public int addTask(ITask task) {
        final var doc = task.asNO2Doc();
        final var id = idFor(ITask.class);
        doc.replace("id", id);
        db.getCollection("tasks").insert(doc);
        db.commit();
        return id;
    }

    @Override
    public void removeTask(int id) {
        db.getCollection("tasks").remove(eq("id", id));
    }

    @Override
    public void updateTask(ITask task) {
        db.getCollection("tasks").update(eq("id", task.getId()), task.asNO2Doc());
    }

    public Stream<TrackItem> getTrackItems() {
        return db.getCollection("trackitems").find().toList().stream().map(d -> ((TrackItem) d.get("obj")));
    }

    public void addTrackItem(TrackItem item) {
        item.setId(idFor(TrackItem.class));
        final var doc = Document.createDocument("obj", item);
        doc.put("id", item.getId());
        db.getCollection("trackitems").insert(doc);
    }

    public void updateTrackItem(TrackItem item) {
        final var doc = Document.createDocument("obj", item);
        doc.put("id", item.getId());
        db.getCollection("trackitems").update(eq("id", item.getId()), doc);
        db.commit();
    }

    public Stream<Risk> getRisks(){
        return db.getCollection("risks").find().toList().stream().map(d -> ((Risk) d.get("obj")));
    }

    public void addRisk (Risk risk){
        risk.setId(idFor(Risk.class));
        final var doc = Document.createDocument("obj", risk);
        doc.put("id", risk.getId());
        db.getCollection("risks").insert(doc);
    }

    public void updateRisk(Risk risk){
        final var doc = Document.createDocument("obj", risk);
        doc.put("id", risk.getId());
        db.getCollection("risks").update(eq("id", risk.getId()), doc);
        db.commit();
    }

    public void removeRisk(int id){db.getCollection("risks").remove(eq("id", id));}

    @Override
    public Integer account_id(String name, String hashpass) {
        final var res = db.getCollection("users")
                .find(and(eq("account_name", name), eq("account_passhash", hashpass)));
        if (res.totalCount() == 1)
            return res.firstOrDefault().get("id", Integer.class);

        if (res.totalCount() > 1)
            throw new RuntimeException("DB has multiple users with the same names and passwords");
        return null;
    }

    private int idFor(Class<?> c) {
        final int v = Math.abs(new Random().nextInt());

        if(c.equals(User.class))
            return getUsers().map(User::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if(c.equals(Project.class))
            return getProjects().map(Project::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if (c.equals(ISprint.class))
            return getSprints().map(ISprint::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if (c.equals(ITask.class))
            return getTasks().map(ITask::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if (c.equals(Fragment.class))
            return getFragments().map(Fragment::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if (c.equals(TrackItem.class))
            return getTrackItems().map(TrackItem::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        if (c.equals((Risk.class)))
            return getRisks().map(Risk::getId).anyMatch(id -> id.equals(v)) ? idFor(c) : v;

        throw new UnsupportedOperationException();
    }
}
