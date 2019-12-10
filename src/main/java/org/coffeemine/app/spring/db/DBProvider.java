package org.coffeemine.app.spring.db;

import org.coffeemine.app.spring.data.*;

import java.util.stream.Stream;

public interface DBProvider {
    void importJSONProject(String json);
    Stream<Project> getProjects();
    Stream<ISprint> getSprints4Project(Project project);
    Stream<ITask> getTasks4Project(Project project);
    Stream<ITask> getTasks4Sprint(ISprint sprint);
    Stream<Fragment> getFragments4Task(ITask task);
    Stream<Fragment> getFragments4User(User task);
    Stream<User> getUsers();
    User getUser(int id);
    void addUser(User user);
    Integer account_id(String name, String hashpass);

    Integer idFor(Class<?> c);
}
