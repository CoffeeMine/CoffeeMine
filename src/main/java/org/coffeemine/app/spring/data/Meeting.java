package org.coffeemine.app.spring.data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class Meeting implements Serializable {


    public enum Type {
        SPRINT;
    }
    private int id;

    private Type type;
    private int bound_to;
    @NotNull
    private Date planned_start;
    @NotNull
    private Date planned_end;
    private Date start;
    private Date end;
    @NotNull
    private ArrayList<String> topics = new ArrayList<>();
    @NotNull
    private ArrayList<Integer> attendees = new ArrayList<>();
    @NotNull
    private String notes = "";

    public Meeting() {}

    public Meeting(int id, Type type, int bound_to, @NotNull Date planned_start, @NotNull Date planned_end, Date start, Date end, @NotNull ArrayList<String> topics, @NotNull ArrayList<Integer> attendees, @NotNull String notes) {
        this.id = id;
        this.type = type;
        this.bound_to = bound_to;
        this.planned_start = planned_start;
        this.planned_end = planned_end;
        this.start = start;
        this.end = end;
        this.topics = topics;
        this.attendees = attendees;
        this.notes = notes;
    }

    public int getId() {
        return id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public int getSprintBinding() {
        return bound_to;
    }

    public void setSprintBinding(int bound_to) {
        this.bound_to = bound_to;
    }

    public Date getPlannedStart() {
        return planned_start;
    }

    public void setPlannedStart(Date planned_start) {
        this.planned_start = planned_start;
    }

    public Date getPlannedEnd() {
        return planned_end;
    }

    public void setPlannedEnd(Date planned_end) {
        this.planned_end = planned_end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public ArrayList<Integer> getAttendees() {
        return attendees;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
