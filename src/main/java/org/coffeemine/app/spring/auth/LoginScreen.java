package org.coffeemine.app.spring.auth;

import com.vaadin.flow.component.ClickEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.PasswordField;
import com.vaadin.flow.component.textfield.TextField;

import java.io.Serializable;


/**
 * UI content when the user is not logged in yet.
 */
public class LoginScreen extends Div {

    private TextField username;
    private PasswordField password;
    private Button login;
    private Button forgotPassword;
    private LoginListener loginListener;
    private AccessControl accessControl;

    public LoginScreen(AccessControl accessControl, LoginListener loginListener) {
        this.loginListener = loginListener;
        this.accessControl = accessControl;
        buildUI();
        username.focus();
    }

    private void buildUI() {
        addClassName("login-screen");

        final var loginForm = buildLoginForm();

        final var centeringLayout = new VerticalLayout();
        centeringLayout.addClassName("centering-layout");
        centeringLayout.add(loginForm);
        centeringLayout.setAlignItems(FlexComponent.Alignment.CENTER);

        add(centeringLayout);
    }

    private Component buildLoginForm() {
        final var loginForm = new FormLayout();

        loginForm.addClassName("login-form");
        loginForm.setSizeUndefined();

        loginForm.add(username = new TextField("Username", "admin"));
        username.setWidth("15em");
        loginForm.add(password = new PasswordField("Password"));
        password.setWidth("15em");
        password.setPlaceholder("Write anything");
        final var buttons = new Div();
        buttons.addClassName("buttons");
        loginForm.add(buttons);

        buttons.add(login = new Button("Login"));
        login.setDisableOnClick(true);
        login.addClickListener((ClickEvent<Button> event) -> {
                try {
                    login();
                } finally {
                    login.setEnabled(true);
                }
            }
        );
        login.addClickShortcut(Key.ENTER);

        buttons.add(forgotPassword = new Button("Forgot password?", (ClickEvent<Button> event) -> {
            showNotification(new Notification("Hint: Try anything"));
        }));
        return loginForm;
    }

    private void login() {
        if (accessControl.signIn(username.getValue(), password.getValue())) {
            loginListener.loginSuccessful();
        } else {
            showNotification(new Notification("Login failed\nPlease check your username and password and try again."));
            username.focus();
        }
    }

    private void showNotification(Notification notification) {
        notification.setDuration(2000);
        notification.setPosition(Notification.Position.MIDDLE);
        notification.open();
    }

    public interface LoginListener extends Serializable {
        void loginSuccessful();
    }
}