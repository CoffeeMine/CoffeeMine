package org.coffeemine.app.spring.auth;

import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.BasicDBProvider;
import org.coffeemine.app.spring.db.DBProvider;

public class BasicAccessControl implements AccessControl {
    private DBProvider db = BasicDBProvider.getInstance();
    private Integer acc_id = null;

    @Override
    public boolean signIn(String username, String password) {
        return ((acc_id = db.account_id(username, password)) != null);
    }

    @Override
    public boolean isUserSignedIn() {
        return acc_id != null;
    }

    @Override
    public boolean isUserInRole(User.Status role) {
        return acc_id != null && db.getUser(acc_id).getStatus() == role;
    }

}
