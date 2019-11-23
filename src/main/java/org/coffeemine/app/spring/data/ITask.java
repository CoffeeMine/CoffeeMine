package org.coffeemine.app.spring.data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;

public interface ITask extends Serializable {
    int getId();
    @NotNull String getName();
    void setName(@NotNull String name);
    @NotNull String getDescription();
    void setDescription(@NotNull String description);
    @NotNull ArrayList<Integer> getAssignees();
    @NotNull ArrayList<Integer> getFragments();
    ArrayList<String> getCommits();
    void setCommits(ArrayList<String> commits);
}
