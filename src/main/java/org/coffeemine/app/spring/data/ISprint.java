package org.coffeemine.app.spring.data;

import org.coffeemine.app.spring.db.NO2Serializable;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public interface ISprint extends Serializable, NO2Serializable {
    int getId();
    @NotNull Date getStart();
    void setStart(@NotNull Date start);
    @NotNull Date getEnd();
    void setEnd(@NotNull Date end);
    int getMeeting();
    void setMeeting(int meeting_id);
    @NotNull ArrayList<Integer> getTasks();
}
