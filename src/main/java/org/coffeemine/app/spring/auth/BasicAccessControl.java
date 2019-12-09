package org.coffeemine.app.spring.auth;

import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.DBProvider;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class BasicAccessControl implements AccessControl {
    private DBProvider db;
    private Integer acc_id = null;
    private static final BasicAccessControl instance = new BasicAccessControl();

    private BasicAccessControl() {
        db = NitriteDBProvider.getInstance();
    }

    public static BasicAccessControl getInstance() {
        return instance;
    }

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
