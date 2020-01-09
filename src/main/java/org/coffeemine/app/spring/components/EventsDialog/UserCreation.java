package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.PasswordField;

import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.components.SpaceableTextField;

public class UserCreation extends Dialog {

    public UserCreation() {
        final var container = new VerticalLayout(new H2("Create a new user."));
        container.setPadding(false);
        container.setWidthFull();

        final var fullNameField = new SpaceableTextField("Full name");
        fullNameField.setRequired(true);
        fullNameField.setWidthFull();
        fullNameField.getStyle().set("margin", "0px");

        final var userNameField = new SpaceableTextField("Username");
        userNameField.setRequired(true);
        userNameField.setWidthFull();
        userNameField.getStyle().set("margin", "0px");

        final var emailField = new SpaceableTextField("Email");
        emailField.setRequired(true);
        emailField.setWidthFull();
        emailField.getStyle().set("margin", "0px");

        final var salaryField = new NumberField("Salary", "420");
        salaryField.setSuffixComponent(new Span("SEK/h"));
        salaryField.setWidthFull();
        salaryField.getStyle().set("margin", "0px");

        final var passwordField = new PasswordField("Password");
        passwordField.setRequired(true);
        passwordField.setWidthFull();
        passwordField.getStyle().set("margin", "0px");
        
        final var db = NitriteDBProvider.getInstance();
        final var createButton = new Button("Create", c -> {
            try {
                final var name = fullNameField.getOptionalValue().orElseThrow();
                final var email = emailField.getOptionalValue().orElseThrow();
                final var account_name = userNameField.getOptionalValue().orElseThrow();
                final var salary = salaryField.getOptionalValue().orElse(420d).floatValue();
                final var password = passwordField.getOptionalValue().orElseThrow();
            
                if (db.getUsers().filter(user -> user.getAccountName().equals(name)).count() == 0) {
                    final var newUser = new User(0, name, email, User.Status.ENABLED, salary, account_name, password);
                    newUser.setCurrentProject(CurrentUser.get().getCurrentProject());
                    db.addUser(newUser);
                    Notification.show("User \"" + account_name + "\" created", 2000, Notification.Position.BOTTOM_END);
                    close();
                }
            } catch(Exception e) {
                // TODO: add some error messages
            }
        });
        createButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        createButton.getStyle().set("margin", "15px 0px 0px auto");
        
        container.add(fullNameField, userNameField, emailField, salaryField, passwordField, createButton);
        add(container);
    }    
}
