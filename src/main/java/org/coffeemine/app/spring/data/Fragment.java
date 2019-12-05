package org.coffeemine.app.spring.data;

import com.vaadin.flow.component.JsonSerializable;
import elemental.json.JsonObject;
import org.coffeemine.app.spring.db.NO2Serializable;
import org.dizitart.no2.Document;

import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.time.format.FormatStyle;
import java.util.ArrayList;

import static java.time.format.DateTimeFormatter.ofLocalizedDate;

public class Fragment implements JsonSerializable, NO2Serializable {
    private int id;
    private LocalDate begin = LocalDate.EPOCH;
    private LocalDate end = LocalDate.EPOCH;
    private ArrayList<Integer> users = new ArrayList<>();

    public int getId() {
        return id;
    }

    public LocalDate getBegin() {
        return begin;
    }

    public LocalDate getEnd() {
        return end;
    }

    public void setBegin(LocalDate begin) {
        this.begin = begin;
    }

    public void setEnd(LocalDate end) {
        this.end = end;
    }

    public ArrayList<Integer> getUsers() {
        return users;
    }

    @Override
    public JsonObject toJson() {
        return null;
    }

    @Override
    public Fragment readJson(JsonObject value) {
        id = (int) value.getNumber("id");

        final var f = ofLocalizedDate(FormatStyle.SHORT);;
        begin = ZonedDateTime.parse(value.getString("begin"), f).toLocalDate();
        end = ZonedDateTime.parse(value.getString("end"), f).toLocalDate();

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
        begin = doc.get("begin", LocalDate.class);
        end = doc.get("end", LocalDate.class);
        users = ((ArrayList<Integer>) doc.get("users"));
        return this;
    }
}
