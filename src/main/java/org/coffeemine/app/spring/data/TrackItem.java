package org.coffeemine.app.spring.data;

import java.time.LocalDateTime;

public class TrackItem {
    public enum Type {
        BUG,
        FEATURE_REQUEST,
    }
    public enum Status {
        OPEN,
        IN_PROGRESS,
        RESOLVED,
        REOPENED,
        CLOSED,
        POSTPONED
    }
    public enum Resolution {
        UNRESOLVED,
        FIXED,
        WONTFIX,
        IMPLEMENTED,
        WONT_IMPLEMENT,
        INVALID
    }

    private int id;
    private String name;
    private String description;
    private int reporter;
    private int assignee;

    private Type type;
    private boolean confirmed;
    private Status status;
    private Resolution resolution;
    private LocalDateTime opened;
    private LocalDateTime resolved;


    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getReporter() {
        return reporter;
    }

    public void setReporter(int reporter) {
        this.reporter = reporter;
    }

    public int getAssignee() {
        return assignee;
    }

    public void setAssignee(int assignee) {
        this.assignee = assignee;
    }
}
