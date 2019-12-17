package org.coffeemine.app.spring.data;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.dizitart.no2.Document;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;

public class Task implements ITask {
    private int id = -1;
    @NotNull
    private String name = "";
    @NotNull
    private String description = "";
    @NotNull
    private int hours;
    @NotNull
    private boolean completed;
    @NotNull
    private ArrayList<Integer> assignees = new ArrayList<>();
    @NotNull
    private ArrayList<Integer> fragments = new ArrayList<>();
    private ArrayList<String> commits;

    public Task() { }

    public Task(int id, @NotNull String name, @NotNull String description, @NotNull int hours, @NotNull boolean completed, @NotNull ArrayList<Integer> assignees, @NotNull ArrayList<Integer> fragments, ArrayList<String> commits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.hours = hours;
        this.completed = completed;
        this.assignees = assignees;
        this.fragments = fragments;
        this.commits = commits;
    }

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
    public @NotNull int getHours() { return hours; }

    @Override
    public void setHours (@NotNull int hours) { this.hours = hours; }

    @Override
    public @NotNull boolean getCompleted() { return completed; }

    @Override
    public void setCompleted(@NotNull boolean completed) { this.completed = completed; }

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
        ret.put("hours", hours);
        ret.put("completed", completed);
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
        hours = ((int) value.getNumber("hours"));
        completed = value.getBoolean("completed");

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

    @Override
    public Document asNO2Doc() {
        return Document.createDocument("id", id)
                .put("name", name)
                .put("description", description)
                .put("hours", hours)
                .put("completed", completed)
                .put("assignees", assignees)
                .put("fragments", fragments)
                .put("commits", commits);
    }

    @Override
    public Task fromNO2Doc(Document doc) {
        if(doc == null)
            return null;

        id = doc.get("id", Integer.class);
        name = doc.get("name", String.class);
        description = doc.get("description", String.class);
        hours = doc.get("hours", Integer.class);
        completed = doc.get("completed", Boolean.class);
        assignees = ((ArrayList<Integer>) doc.get("assignees"));
        fragments = ((ArrayList<Integer>) doc.get("fragments"));
        commits = ((ArrayList<String>) doc.get("commits"));
        return this;
    }
}