package org.coffeemine.app.spring;

import com.vaadin.flow.router.Route;
import org.coffeemine.app.spring.auth.BasicAccessControl;
import org.coffeemine.app.spring.auth.LoginScreen;

@Route(value = "Login")
public class AuthView extends View {
    AuthView() {
        super();
        add(new LoginScreen(new BasicAccessControl(), () -> {}));
    }
}
