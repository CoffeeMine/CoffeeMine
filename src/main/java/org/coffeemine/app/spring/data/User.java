package org.coffeemine.app.spring.data;

import org.coffeemine.app.spring.db.NO2Serializable;
import org.dizitart.no2.Document;

public class User implements NO2Serializable {

    public enum Status {
        EXTERNAL,
        ADMIN,
        ENABLED,
        DISABLED,
    }

    private int id;
    private String name;
    private float hourly_salary = 0;
    private Status status;
    private int current_proj = -1;

    private String account_name;
    private String account_passhash;

    public User(){}

    public User(int id, String name, Status status) {
        this(id, name, status, 0.0f, null, null);
    }

    public User(int id, String name, Status status, float hourly_salary, String account_name, String account_passhash) {
        this.id = id;
        this.name = name;
        this.hourly_salary = hourly_salary;
        this.status = status;
        this.account_name = account_name;
        this.account_passhash = account_passhash;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getHourlySalary() {
        return hourly_salary;
    }

    public void setHourlySalary(float hourly_salary) {
        this.hourly_salary = hourly_salary;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getCurrentProject() {
        return this.current_proj;
    }

    public void setCurrentProject(int proj) {
        this.current_proj = proj;
    }  

    public String getAccountName() {
        return account_name;
    }

    public void setAccountName(String account_name) {
        this.account_name = account_name;
    }

    public String getAccountPasshash() {
        return account_passhash;
    }

    public void setAccountPasshash(String account_passhash) {
        this.account_passhash = account_passhash;
    }

    @Override
    public Document asNO2Doc() {
        return Document.createDocument("id", id)
                .put("name", name)
                .put("salary", hourly_salary)
                .put("status", status)
                .put("current_proj", current_proj)
                .put("account_name", account_name)
                .put("account_passhash", account_passhash);
    }

    @Override
    public User fromNO2Doc(Document doc) {
        if(doc == null)
            return null;

        id = doc.get("id", Integer.class);
        name = doc.get("name", String.class);
        hourly_salary = doc.get("salary", Float.class);
        status = doc.get("status", User.Status.class);
        current_proj = doc.get("current_proj", Integer.class);
        account_name = doc.get("account_name", String.class);
        account_passhash = doc.get("account_passhash", String.class);
        return this;
    }

    public String toString(){
        return name;
    }
}
