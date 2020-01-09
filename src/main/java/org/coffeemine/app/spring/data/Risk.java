package org.coffeemine.app.spring.data;

import java.io.Serializable;

public class Risk implements Serializable {

    private int id;
    private String description;
    private String type;
    private String impact;
    private String likelihood;

    public Risk(int id, String description, String type, String impact, String likelihood) {
        this.id = id;
        this.description = description;
        this.type = type;
        this.impact = impact;
        this.likelihood = likelihood;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImpact() {
        return impact;
    }

    public void setImpact(String impact) {
        this.impact = impact;
    }

    public String getLikelihood() {
        return likelihood;
    }

    public void setLikelihood(String likelihood) {
        this.likelihood = likelihood;
    }
}
