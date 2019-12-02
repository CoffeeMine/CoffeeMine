package org.coffeemine.app.spring.db;

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
    private ArrayList<User> users = new ArrayList<>();

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


    static BasicDBProvider getInstance(){
        return instance;
    }
}
