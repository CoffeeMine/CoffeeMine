package org.coffeemine.app.spring.auth;

import org.coffeemine.app.spring.data.User;

/**
 * Simple interface for authentication and authorization checks.
 */
public interface AccessControl {

    boolean signIn(String username, String password);

    boolean isUserSignedIn();

    boolean isUserInRole(User.Status role);

    int getUserId();
}
