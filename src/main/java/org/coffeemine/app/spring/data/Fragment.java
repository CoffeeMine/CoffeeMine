package org.coffeemine.app.spring.data;

import com.vaadin.flow.component.JsonSerializable;
import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.db.NO2Serializable;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.dizitart.no2.Document;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Fragment implements JsonSerializable, NO2Serializable, ChangeTracker {
    private int id;
    private LocalDateTime begin = LocalDateTime.MIN;
    private LocalDateTime end = LocalDateTime.MIN;
    private ArrayList<Integer> users = new ArrayList<>();
    private Long lastModifiedTime;
    private int revision;

    public Fragment() {}

    public Fragment(int id, LocalDateTime begin, LocalDateTime end, ArrayList<Integer> users) {
        this.id = id;
        this.begin = begin;
        this.end = end;
        this.users = users;
        this.lastModifiedTime = Instant.now().toEpochMilli();
        this.revision = 1;
    }

    public int getId() {
        return id;
    }

    public LocalDateTime getBegin() {
        return begin;
    }

    public LocalDateTime getEnd() {
        return end;
    }

    public int getHours() {
        return (int) ChronoUnit.HOURS.between(begin, end);
    }

    @Override
    public String getType() {
        return "Work log";
    }

    @Override
    public String getMessage() {
        final var db = NitriteDBProvider.getInstance();
        final var task = db.getTasks4Project(db.getProject(CurrentUser.get().getCurrentProject())).filter(s -> s.getFragments().contains(id)).findFirst();
        return task.isPresent() ? getHours() + " hours for task " + task.get().getName() : "null";
    }

    @Override
    public Long getLastModifiedTime() {
        return lastModifiedTime;
    }

    @Override
    public int getRevision() {
        return revision;
    }

    public void setBegin(LocalDateTime begin) {
        this.begin = begin;
    }

    public void setEnd(LocalDateTime end) {
        this.end = end;
    }

    public ArrayList<Integer> getUsers() {
        return users;
    }

    @Override
    public JsonObject toJson() {
        final var factory = new JreJsonFactory();
        final var ret = factory.createObject();
        ret.put("id", id);
        ret.put("begin", begin.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));
        ret.put("end", end.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME));

        final var users = factory.createArray();
        for (int i = 0; i < this.users.size(); ++i)
            users.set(i, this.users.get(i));
        ret.put("users", users);

        return ret;
    }

    @Override
    public Fragment readJson(JsonObject value) {
        id = (int) value.getNumber("id");

        final var f = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
        begin = LocalDateTime.parse(value.getString("begin"), f);
        end = LocalDateTime.parse(value.getString("end"), f);

        final var jusers = value.getArray("users");
        users.ensureCapacity(jusers.length());
        for (int i = 0; i < jusers.length(); ++i)
            users.add(((int) jusers.getNumber(i)));

        return this;
    }

    @Override
    public Document asNO2Doc() {
        return Document.createDocument("id", id)
                .put("begin", begin)
                .put("end",  end)
                .put("users", users);
    }

    @Override
    public Fragment fromNO2Doc(Document doc) {
        id = doc.get("id", Integer.class);
        begin = doc.get("begin", LocalDateTime.class);
        end = doc.get("end", LocalDateTime.class);
        users = ((ArrayList<Integer>) doc.get("users"));
        lastModifiedTime = doc.getLastModifiedTime();
        revision = doc.getRevision();
        return this;
    }
}
