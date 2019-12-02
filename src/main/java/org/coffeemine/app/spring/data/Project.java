package org.coffeemine.app.spring.data;

import java.util.ArrayList;

public class Project {
    private String name = "Unnamed";
    private ArrayList<Integer> sprints = new ArrayList<>();

    public Project() {}
    public Project(String name) { this.name = name; }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Integer> getSprints() {
        return sprints;
    }
}
