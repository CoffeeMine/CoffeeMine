package org.coffeemine.app.spring.data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public interface ISprint extends Serializable {
    int getId();
    @NotNull Date getStart();
    void setStart(@NotNull Date start);
    @NotNull Date getEnd();
    void setEnd(@NotNull Date end);
    int getMeeting();
    void setMeeting(int meeting_id);
    @NotNull ArrayList<Integer> getTasks();
}
