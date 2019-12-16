package org.coffeemine.app.spring;

import org.coffeemine.app.spring.db.NitriteDBProvider;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import org.coffeemine.app.spring.data.Project;
import org.coffeemine.app.spring.data.Sprint;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.data.User;

public class TestingData {

    private TestingData() {
    }

    static void load() {
        var db = NitriteDBProvider.getInstance();
        var defaultUser = new User(0, "Admoon", User.Status.ADMIN, -1.0f, "admoon", "Foobar");
        var defaultProject = new Project("CoffeeMine", db.idFor(Project.class));

        defaultUser.setCurrentProject(defaultProject.getId());
        
        db.addUser(defaultUser);
        db.addProject(defaultProject);

        var users = new ArrayList<Integer>();
        users.add(0);
        var task = new Task(db.idFor(Task.class), "Hello", "Task1", users, new ArrayList<Integer>(), new ArrayList<String>());
        var task1 = new Task(db.idFor(Task.class), "Hello1", "Task2", users, new ArrayList<Integer>(), new ArrayList<String>());
        var task2 = new Task(db.idFor(Task.class), "Hello2", "Task3", users, new ArrayList<Integer>(), new ArrayList<String>());
        var task3 = new Task(db.idFor(Task.class), "Hello3", "Task4", users, new ArrayList<Integer>(), new ArrayList<String>());
        db.addTask(task);
        db.addTask(task1);
        db.addTask(task2);
        db.addTask(task3);

        var tasks = new ArrayList<Integer>();
        tasks.add(task.getId());
        tasks.add(task1.getId());
        tasks.add(task2.getId());
        tasks.add(task3.getId());

        var now = LocalDate.now();
        var start = now.minus(1, ChronoUnit.WEEKS);
        var end = now.plus(1, ChronoUnit.WEEKS);

        var testsprint = new Sprint(db.idFor(Sprint.class), start, end, -1, tasks);
            db.addSprint(testsprint);

        defaultProject.addSprint(testsprint.getId());
    }
}
