package org.coffeemine.app.spring.db;

import org.coffeemine.app.spring.data.*;

import java.util.ArrayList;
import java.util.stream.Stream;

public interface DBProvider {
    void importJSONProject(String json);
    ArrayList<Project> getProjects();
    Stream<ISprint> getSprints4Project(Project project);
    Stream<ITask> getTasks4Project(Project project);
    Stream<ITask> getTasks4Sprint(ISprint sprint);
    Stream<Fragment> getFragments4Task(ITask task);
    Stream<Fragment> getFragments4User(User task);
    ArrayList<User> getUsers();
    User getUser(int id);
    Integer account_id(String name, String hashpass);
}
