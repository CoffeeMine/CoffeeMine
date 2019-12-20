package org.coffeemine.app.spring.userprofile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;

import org.coffeemine.app.spring.auth.BasicAccessControl;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.auth.ProtectedView;
import org.coffeemine.app.spring.components.ProjectList;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.view.Overview;

@Route(value = "Profile")
public class UserProfile extends VerticalLayout implements ProtectedView {
    public UserProfile() {
        this.setHeightFull();
        this.setPadding(false);
        VerticalLayout userprofile = new VerticalLayout();
        userprofile.setHeightFull();
        userprofile.addClassName("userprofile");
        userprofile.setMaxWidth("800px");
        // copy this to center
        userprofile.getStyle().set("margin", "auto");
        this.add(userprofile);
        Button back = new Button("back");
        back.addClickListener(e -> UI.getCurrent().navigate(Overview.class));
        back.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        HorizontalLayout usernameTitle = new HorizontalLayout();
        usernameTitle.getStyle().set("margin-left", "auto");
        usernameTitle.getStyle().set("margin-right", "auto");

        User user = CurrentUser.get();
        String userName = (user == null) ? "No User" : user.getName();

        H2 title = new H2(userName);
        title.getStyle().set("padding", "0px");
        title.getStyle().set("margin-top", "auto");
        title.getStyle().set("margin-bottom", "auto");
        // circle capital letter A
        Span letter = new Span(userName.substring(0, 1));
        letter.addClassName("lettericon");
        usernameTitle.add(letter, title);
        userprofile.add(back);
        userprofile.add(usernameTitle);
        HorizontalLayout info = new HorizontalLayout();
        info.setWidthFull();
        userprofile.add(info);
        // details
        VerticalLayout details = new VerticalLayout();
        details.setWidthFull();
        details.add(new H3("Account details"));
        FormLayout accountDetails = new FormLayout();
        accountDetails.addFormItem(new TextField(), "Full name：");
        accountDetails.addFormItem(new TextField(), "Email:");
        details.add(accountDetails);
        VerticalLayout states = new VerticalLayout();
        states.setWidthFull();
        states.add(new H3("Account states "));
        FormLayout accountStates = new FormLayout();
        accountStates.addFormItem(new Span("0.0"), "Hourly salary");
        accountStates.addFormItem(new Span("0.0"), "Total hours worked: ");
        states.add(accountStates);
        info.add(details, states);
        H3 currentProject = new H3("Current projects");
        Button save = new Button("save");
        save.getStyle().set("margin-left", "auto");
        save.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        final var currentProjects = new ProjectList(ProjectList.Modes.LARGE);
        currentProjects.setHeight("100%");
        userprofile.add(save);
        userprofile.add(currentProject);
        userprofile.add(currentProjects);
        Button logOut = new Button("Log out", e -> BasicAccessControl.getInstance().signOut());
        logOut.getStyle().set("margin-left", "auto");
        logOut.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        userprofile.add(logOut);
    }
}