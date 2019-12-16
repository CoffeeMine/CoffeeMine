package org.coffeemine.app.spring.data;

import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.dizitart.no2.Document;

import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Sprint implements ISprint {
    private int id = -1;
    @NotNull
    private LocalDate start = LocalDate.EPOCH;
    @NotNull
    private LocalDate end = LocalDate.EPOCH;
    private int meeting_id = -1;
    @NotNull
    private ArrayList<Integer> tasks = new ArrayList<>();

    public Sprint() { }

    public Sprint(int id, @NotNull LocalDate start, @NotNull LocalDate end, int meeting_id, @NotNull ArrayList<Integer> tasks) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.meeting_id = meeting_id;
        this.tasks = tasks;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public @NotNull LocalDate getStart() {
        return start;
    }

    @Override
    public void setStart(@NotNull LocalDate start) {
        this.start = start;
    }

    @Override
    public @NotNull LocalDate getEnd() {
        return end;
    }

    @Override
    public void setEnd(@NotNull LocalDate end) {
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

    @Override
    public JsonObject toJson() {
        final var factory = new JreJsonFactory();
        final var ret = factory.createObject();
        ret.put("id", id);
        ret.put("start", start.format(DateTimeFormatter.BASIC_ISO_DATE));
        ret.put("end", end.format(DateTimeFormatter.BASIC_ISO_DATE));

        if(meeting_id != -1)
            ret.put("meeting_id", meeting_id);

        final var tasks = factory.createArray();
        for (int i = 0; i < this.tasks.size(); ++i)
            tasks.set(i, this.tasks.get(i));

        return ret;
    }

    @Override
    public Sprint readJson(JsonObject value) {
        id = ((int) value.getNumber("id"));
        start = LocalDate.parse(value.getString("start"));
        end = LocalDate.parse(value.getString("end"));


        final var jtask = value.getArray("tasks");
        tasks.ensureCapacity(jtask.length());
        for (int i = 0; i < jtask.length(); ++i)
            tasks.add(((int) jtask.getNumber(i)));

        if (value.hasKey("meeting_id")){
            meeting_id = (int)value.getNumber("meeting_id");
        } else
            meeting_id = -1;

        return this;
    }

    @Override
    public Document asNO2Doc() {
        return Document.createDocument("id", id)
                .put("start", start)
                .put("end", end)
                .put("meeting_id", meeting_id)
                .put("tasks", tasks);
    }

    @Override
    public Sprint fromNO2Doc(Document doc) {
        if(doc == null)
            return null;

        id = doc.get("id", Integer.class);
        start = doc.get("start", LocalDate.class);
        end = doc.get("end", LocalDate.class);
        meeting_id = doc.get("meeting_id", Integer.class);
        tasks = ((ArrayList<Integer>) doc.get("tasks"));
        return this;
    }
}
