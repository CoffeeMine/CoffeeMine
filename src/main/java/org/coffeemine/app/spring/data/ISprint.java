package org.coffeemine.app.spring.data;

import com.vaadin.flow.component.JsonSerializable;
import org.coffeemine.app.spring.db.NO2Serializable;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.ArrayList;

public interface ISprint extends JsonSerializable, NO2Serializable, ChangeTracker {
    int getId();
    @NotNull LocalDate getStart();
    void setStart(@NotNull LocalDate start);
    @NotNull LocalDate getEnd();
    void setEnd(@NotNull LocalDate end);
    int getMeeting();
    void setMeeting(int meeting_id);
    @NotNull ArrayList<Integer> getTasks();
}
