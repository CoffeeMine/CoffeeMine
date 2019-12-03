package org.coffeemine.app.spring.db;

import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.data.Project;
import org.coffeemine.app.spring.data.User;

import java.util.ArrayList;
import java.util.stream.Stream;

public interface DBProvider {
    void importJSONProject(String json);
    ArrayList<Project> getProjects();
    Stream<ISprint> getSprints4Project(Project project);
    Stream<ITask> getTasks4Project(Project project);
    Stream<ITask> getTasks4Sprint(ISprint sprint);
    ArrayList<User> getUsers();
    User getUser(int id);
    Integer account_id(String name, String hashpass);
}
