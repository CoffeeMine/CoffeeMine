package org.coffeemine.app.spring.data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Date;

public class Sprint implements ISprint {
    private int id;
    @NotNull
    private Date start;
    @NotNull
    private Date end;
    private int meeting_id = -1;
    @NotNull
    private ArrayList<Integer> tasks = new ArrayList<>();

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull Date getStart() {
        return start;
    }

    @Override
    public void setStart(@NotNull Date start) {
        this.start = start;
    }

    @Override
    public @NotNull Date getEnd() {
        return end;
    }

    @Override
    public void setEnd(@NotNull Date end) {
        this.end = end;
    }

    @Override
    public int getMeeting() {
        return meeting_id;
    }

    @Override
    public void setMeeting(int meeting_id) {
        this.meeting_id = meeting_id;
    }

    @Override
    public @NotNull ArrayList<Integer> getTasks() {
        return tasks;
    }
}
