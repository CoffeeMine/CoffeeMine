package org.coffeemine.app.spring.data;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TrackItem implements Serializable {
    public enum Type {
        BUG("Bug"),
        FEATURE_REQUEST("Feature Request");

        private String val;

        Type(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
    }

    public enum Status {
        OPEN("Open"),
        IN_PROGRESS("In Progress"),
        RESOLVED("Resolved"),
        REOPENED("Reopened"),
        CLOSED("Closed"),
        POSTPONED("Postponed");

        private String val;

        Status(String val) {
            this.val = val;
        }

        public String getVal() {
            return val;
        }
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

    public TrackItem(int id, String name, String description, int reporter, int assignee, Type type, boolean confirmed,
                     Status status, Resolution resolution, LocalDateTime opened, LocalDateTime resolved) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.reporter = reporter;
        this.assignee = assignee;
        this.type = type;
        this.confirmed = confirmed;
        this.status = status;
        this.resolution = resolution;
        this.opened = opened;
        this.resolved = resolved;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public boolean isConfirmed() {
        return confirmed;
    }

    public void setConfirmed(boolean confirmed) {
        this.confirmed = confirmed;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Resolution getResolution() {
        return resolution;
    }

    public void setResolution(Resolution resolution) {
        this.resolution = resolution;
    }

    public LocalDateTime getOpened() {
        return opened;
    }

    public void setOpened(LocalDateTime opened) {
        this.opened = opened;
    }

    public LocalDateTime getResolved() {
        return resolved;
}

    public void setResolved(LocalDateTime resolved) {
        this.resolved = resolved;
    }

}
