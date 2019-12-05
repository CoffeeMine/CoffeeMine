package org.coffeemine.app.spring.data;

import com.vaadin.flow.component.JsonSerializable;
import elemental.json.JsonObject;
import elemental.json.impl.JreJsonFactory;
import org.coffeemine.app.spring.db.NO2Serializable;
import org.dizitart.no2.Document;

import java.util.ArrayList;

public class Project implements JsonSerializable, NO2Serializable {
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

    @Override
    public JsonObject toJson() {
        final var factory = new JreJsonFactory();
        final var ret = factory.createObject();
        ret.put("name", name);

        final var sprints = factory.createArray();
        for (int i = 0; i < this.sprints.size(); ++i)
            sprints.set(i, this.sprints.get(i));

        return ret;
    }

    @Override
    public Project readJson(JsonObject value) {
        name = value.getString("name");

        final var jsprints = value.getArray("fragments");
        sprints.ensureCapacity(jsprints.length());
        for (int i = 0; i < jsprints.length(); ++i)
            sprints.add(((int) jsprints.getNumber(i)));

        return this;
    }

    @Override
    public Document asNO2Doc() {
        return Document.createDocument("name", name)
                .put("sprints", sprints);
    }

    @Override
    public Project fromNO2Doc(Document doc) {
        if(doc == null)
            return null;

        name = doc.get("name", String.class);
        sprints = ((ArrayList<Integer>) doc.get("sprints"));
        return this;
    }
}
