package org.coffeemine.app.spring.data;

import com.vaadin.flow.component.JsonSerializable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public interface ITask extends JsonSerializable {
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
