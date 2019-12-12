package org.coffeemine.app.spring.auth;

import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.DBProvider;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class BasicAccessControl implements AccessControl {
    private DBProvider db;
    private Integer userId = null;
    private static final BasicAccessControl instance = new BasicAccessControl();

    private BasicAccessControl() {
        db = NitriteDBProvider.getInstance();
    }

    public static BasicAccessControl getInstance() {
        return instance;
    }

    @Override
    public boolean signIn(String username, String password) {
        return ((userId = db.account_id(username, password)) != null);
    }

    @Override
    public boolean isUserSignedIn() {
        return userId != null;
    }

    @Override
    public boolean isUserInRole(User.Status role) {
        return userId != null && db.getUser(userId).getStatus() == role;
    }

    @Override
    public int getUserId() {
        return userId;
    }

}
