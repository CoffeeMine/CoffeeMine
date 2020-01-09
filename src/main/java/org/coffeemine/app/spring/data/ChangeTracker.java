package org.coffeemine.app.spring.data;

public interface ChangeTracker {
    public String getType();
    public String getMessage();
    public Long getLastModifiedTime();
    public int getRevision();
}
