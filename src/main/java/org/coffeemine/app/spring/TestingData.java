package org.coffeemine.app.spring;

import org.coffeemine.app.spring.data.Project;
import org.coffeemine.app.spring.data.Sprint;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class TestingData {

    private TestingData() {
    }

    static void load() {
        final var db = NitriteDBProvider.getInstance();
        var defaultUser = new User(0, "Admoon", User.Status.ADMIN, -1.0f, "admoon", "Foobar");
        final var proj_id = db.addProject(new Project("CoffeeMine", -1));

        defaultUser.setCurrentProject(proj_id);

        db.addUser(defaultUser);

        var users = new ArrayList<Integer>();
        users.add(0);

        var tasks = new ArrayList<Integer>();
        tasks.add(db.addTask(new Task(-1, "Hello0", "Task1", 10, true, users, new ArrayList<>(), new ArrayList<>())));
        tasks.add(db.addTask(new Task(-1, "Hello1", "Task2", 16, true, users, new ArrayList<>(), new ArrayList<>())));
        tasks.add(db.addTask(new Task(-1, "Hello2", "Task3", 1, true, users, new ArrayList<>(), new ArrayList<>())));
        tasks.add(db.addTask(new Task(-1, "Hello3", "Task4", 3, false, users, new ArrayList<>(), new ArrayList<>())));

        final var now = LocalDate.now();
        final var start = now.minus(1, ChronoUnit.WEEKS);
        final var end = now.plus(1, ChronoUnit.WEEKS);

        final var sprint_id = db.addSprint(new Sprint(-1, start, end, -1, tasks));

        db.getProject(proj_id).addSprint(sprint_id);
    }
}
