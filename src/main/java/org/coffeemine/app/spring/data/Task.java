package org.coffeemine.app.spring.data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class Task implements ITask {
    private int id;
    @NotNull
    private String name = "";
    @NotNull
    private String description = "";
    @NotNull
    private ArrayList<Integer> assignees = new ArrayList<>();
    @NotNull
    private ArrayList<Integer> fragments = new ArrayList<>();
    private ArrayList<String> commits;

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull String getName() {
        return name;
    }

    @Override
    public void setName(@NotNull String name) {
        this.name = name;
    }

    @Override
    public @NotNull String getDescription() {
        return description;
    }

    @Override
    public void setDescription(@NotNull String description) {
        this.description = description;
    }

    @Override
    public @NotNull ArrayList<Integer> getAssignees() {
        return assignees;
    }

    @Override
    public @NotNull ArrayList<Integer> getFragments() {
        return fragments;
    }

    @Override
    public ArrayList<String> getCommits() {
        return commits;
    }

    @Override
    public void setCommits(ArrayList<String> commits) {
        this.commits = commits;
    }
}
