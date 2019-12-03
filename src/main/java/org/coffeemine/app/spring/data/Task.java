package org.coffeemine.app.spring.data;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;

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

    @Override
    public JsonObject toJson() {
        final var factory = new JreJsonFactory();
        final var ret = factory.createObject();
        ret.put("id", id);
        ret.put("name", name);
        ret.put("description", description);
        final var assignees = factory.createArray();
        for (int i = 0; i < this.assignees.size(); ++i)
            assignees.set(i, this.assignees.get(i));

        final var fragments = factory.createArray();
        for (int i = 0; i < this.fragments.size(); ++i)
            fragments.set(i, this.fragments.get(i));

        if(commits != null){
            final var commits = factory.createArray();
            for (int i = 0; i < this.commits.size(); ++i)
                commits.set(i, this.commits.get(i));
        }

        return ret;
    }

    @Override
    public Task readJson(JsonObject value) {
        id = ((int) value.getNumber("id"));
        name = value.getString("name");
        description = value.getString("description");

        final var jassign = value.getArray("assignees");
        assignees.ensureCapacity(jassign.length());
        for (int i = 0; i < jassign.length(); ++i)
            assignees.add(((int) jassign.getNumber(i)));

        final var jfrag = value.getArray("fragments");
        fragments.ensureCapacity(jfrag.length());
        for (int i = 0; i < jfrag.length(); ++i)
            fragments.add(((int) jfrag.getNumber(i)));

        if (value.hasKey("commits")){
            if (commits == null)
                commits = new ArrayList<>();
            final var jcomm = value.getArray("commits");
            commits.ensureCapacity(jcomm.length());
            for (int i = 0; i < jcomm.length(); ++i)
                commits.add(jcomm.getString(i));
        } else
            commits = null;

        return this;
    }

}