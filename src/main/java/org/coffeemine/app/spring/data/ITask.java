package org.coffeemine.app.spring.data;

import com.vaadin.flow.component.JsonSerializable;
import org.coffeemine.app.spring.db.NO2Serializable;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public interface ITask extends JsonSerializable, NO2Serializable {
    int getId();

    @NotNull String getName();

    void setName(@NotNull String name);

    @NotNull String getDescription();

    void setDescription(@NotNull String description);

    int getHours();

    void setHours(int hours);

    boolean isCompleted();

    void setCompleted(boolean completed);

    @NotNull ArrayList<Integer> getAssignees();

    @NotNull ArrayList<Integer> getFragments();

    ArrayList<String> getCommits();

    void setCommits(ArrayList<String> commits);
}
