package org.coffeemine.app.spring;

import org.coffeemine.app.spring.data.*;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TestingData {

    private TestingData() {
    }

    static void load() {
        final var db = NitriteDBProvider.getInstance();
        var defaultUser = new User(0, "Admoon", "admoon@foobar.xyz", User.Status.ADMIN, 250.0f, "admoon", "Foobar");
        db.addUser(new User(1, "J", "admoon@foobar.xyz", User.Status.ENABLED, 250.0f, "a", "Foobar"));
        db.addUser(new User(2, "R", "admoon@foobar.xyz", User.Status.ENABLED, 250.0f, "b", "Foobar"));
        db.addUser(new User(3, "B", "admoon@foobar.xyz", User.Status.ENABLED, 250.0f, "c", "Foobar"));
        db.addUser(new User(4, "Z", "admoon@foobar.xyz", User.Status.ENABLED, 250.0f, "d", "Foobar"));
        db.addUser(new User(5, "C", "admoon@foobar.xyz", User.Status.ENABLED, 250.0f, "e", "Foobar"));

        final var proj_id = db.addProject(new Project("CoffeeMine", -1));

        defaultUser.setCurrentProject(proj_id);

        final var defaultUserId = db.addUser(defaultUser);

        db.addTrackItem(new TrackItem(3, "Bob is broken", "Bob doesn't work since the update", defaultUserId, -1, TrackItem.Type.BUG, true, TrackItem.Status.OPEN, TrackItem.Resolution.UNRESOLVED, LocalDateTime.now(), null));
        db.addTrackItem(new TrackItem(45, "Change color", "Green is better than yellow", defaultUserId, defaultUserId, TrackItem.Type.FEATURE_REQUEST, true, TrackItem.Status.IN_PROGRESS, TrackItem.Resolution.FIXED, LocalDateTime.now(), LocalDateTime.now()));

        var users = new ArrayList<Integer>();
        users.add(defaultUserId);

        db.addRisk(new Risk (1,"No or poor business case", "Commercial", "Select", "Select"));
        db.addRisk(new Risk (2,"More than one customer", "Commercial", "Select", "Select"));
        db.addRisk(new Risk (5,"Ill-defined scope", "Commercial", "Select", "Select"));
        db.addRisk(new Risk (6,"Unclear payment schedule", "Commercial", "Select", "Select"));
        db.addRisk(new Risk (8,"Unclear customer structure", "Relationship", "Select", "Select"));
        db.addRisk(new Risk (10,"Internal customer politics", "Relationship", "Select", "Select"));
        db.addRisk(new Risk (11,"Multiple stakeholders", "Relationship", "Select", "Select"));
        db.addRisk(new Risk (13,"Unwillingness to change", "Relationship", "Select", "Select"));
        db.addRisk(new Risk (15,"Requirements not agreed", "Requirements", "Select", "Select"));
        db.addRisk(new Risk (16,"Requirements incomplete", "Requirements", "Select", "Select"));
        db.addRisk(new Risk(18, "Ambiguity in requirements", "Requirements", "Select", "Select"));
        db.addRisk(new Risk(21, "Acceptance criteria not agreed", "Requirements", "Select", "Select"));
        db.addRisk(new Risk(26, "Developers lack key skills", "Planning and Resource", "Select", "Select"));
        db.addRisk(new Risk(32, "Unfamiliar system software", "Technical", "Select", "Select"));
        db.addRisk(new Risk(33, "Lack of technical support", "Technical", "Select", "Select"));
        db.addRisk(new Risk(35, "New/unproven technology used", "Technical", "Select", "Select"));
        db.addRisk(new Risk(37, "Suppliers in poor financial state", "Subcontract", "Select", "Select"));
        db.addRisk(new Risk(39, "No choice of supplier", "Subcontract", "Select", "Select"));

        var tasks = new ArrayList<Integer>();
        tasks.add(db.addTask(new Task(-1, "Hello0", "Task1",8, true, users, new ArrayList<>(), new ArrayList<>())));
        tasks.add(db.addTask(new Task(-1, "Hello1", "Task2",10, true, users, new ArrayList<>(), new ArrayList<>())));
        tasks.add(db.addTask(new Task(-1, "Hello2", "Task3", 9,true, users, new ArrayList<>(), new ArrayList<>())));
        tasks.add(db.addTask(new Task(-1, "Hello3", "Task4", 5,false, users, new ArrayList<>(), new ArrayList<>())));

        final var now = LocalDate.now();
        final var start = now.minus(1, ChronoUnit.WEEKS);
        final var end = now.plus(1, ChronoUnit.WEEKS);

        final var sprint_id = db.addSprint(new Sprint(-1, start, end, -1, tasks));

        db.getProject(proj_id).addSprint(sprint_id);
    }
}
