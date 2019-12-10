package org.coffeemine.app.spring.auth;

import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import org.coffeemine.app.spring.auth.BasicAccessControl;
import org.coffeemine.app.spring.auth.LoginScreen;

public interface ProtectedView extends BeforeEnterObserver {
    @Override
    public default void beforeEnter(BeforeEnterEvent event) {
        if (!BasicAccessControl.getInstance().isUserSignedIn()) {
            event.forwardTo(LoginScreen.class);
        }
    }
}
