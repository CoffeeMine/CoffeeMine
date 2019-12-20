package org.coffeemine.app.spring.auth;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Image;
import com.vaadin.flow.component.login.LoginForm;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

import org.coffeemine.app.spring.view.Overview;

@Route(value = "login")
public class LoginScreen extends VerticalLayout {
    public static final Boolean routable = false;

    LoginScreen() {
        this.setSizeFull();
        final var branding = new Image("./icons/icon-mono.png", "");
        branding.getStyle().set("position", "fixed");
        branding.getStyle().set("margin-left", "-100px");
        branding.getStyle().set("left", "50%");
        branding.getStyle().set("z-index", "-1");

        Div layout = new Div();
        layout.getStyle().set("margin", "auto");

        LoginForm login = new LoginForm();

        layout.add(login);
        this.add(branding, layout);

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
