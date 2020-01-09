package org.coffeemine.app.spring.db;

import org.coffeemine.app.spring.data.*;

import java.util.stream.Stream;

public interface DBProvider {
    void importJSONProject(String json);
    Stream<Project> getProjects();
    Stream<ISprint> getSprints();
    Stream<ITask> getTasks();

    Stream<ISprint> getSprints4Project(Project project);

    Stream<ITask> getTasks4Project(Project project);

    Stream<ITask> getTasks4Sprint(ISprint sprint);

    Stream<Fragment> getFragments4Task(ITask task);

    Stream<Fragment> getFragments4User(User user);

    Project getCurrentProject(User user);

    ISprint getCurrentSprint(Project project);

    Stream<User> getUsers();

    User getUser(int id);

    void updateUser(User use);

    Project getProject(int id);

    void updateProject(Project project);

    int addProject(Project project);

    int addFragment(Fragment fragment);

    int addUser(User user);

    ISprint getSprint(int id);

    int addSprint(ISprint sprint);

    void removeSprint(int id);

    void updateSprint(ISprint sprint);

    ITask getTask(int id);

    int addTask(ITask task);

    void removeTask(int id);

    void updateTask(ITask task);

    Integer account_id(String name, String hashpass);
}
