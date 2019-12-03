package org.coffeemine.app.spring.data;

public class User {
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

    private String account_name;
    private String account_passhash;

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
}
