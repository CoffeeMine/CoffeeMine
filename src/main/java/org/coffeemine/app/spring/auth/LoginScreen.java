package org.coffeemine.app.spring.auth;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.coffeemine.app.spring.view.Overview;

@Route(value = "login")
public class LoginScreen extends VerticalLayout {
    public static final Boolean routable = false;

    LoginScreen() {
        this.setSizeFull();

        Div layout = new Div();
        layout.getStyle().set("margin", "auto");

        LoginForm login = new LoginForm();

        layout.add(login);
        this.add(layout);

        BasicAccessControl loginControl = BasicAccessControl.getInstance();

        login.addLoginListener(e -> {
            if (loginControl.signIn(e.getUsername(), e.getPassword())) {
                this.getUI().ifPresent(ui -> ui.navigate(Overview.class));
            } else {
                login.setError(true);
                login.setEnabled(true);
            }
        });
    }
}
