package org.coffeemine.app.spring.userprofile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;

import org.coffeemine.app.spring.auth.BasicAccessControl;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.auth.ProtectedView;
import org.coffeemine.app.spring.components.ProjectList;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.Overview;

@Route(value = "Profile")
public class UserProfile extends VerticalLayout implements ProtectedView, HasUrlParameter<String> {
    private User user;

    public UserProfile() {
    }

    public void generate() {
        removeAll();
        setHeightFull();
        setPadding(false);
        setAlignItems(Alignment.CENTER);

        VerticalLayout userprofile = new VerticalLayout();
        userprofile.addClassName("userprofile");

        Button back = new Button("back");
        back.addClickListener(e -> UI.getCurrent().navigate(Overview.class));
        back.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        HorizontalLayout usernameTitle = new HorizontalLayout();
        usernameTitle.setWidthFull();
        usernameTitle.setAlignItems(Alignment.CENTER);

        String userName = (user == null) ? "No User" : user.getName();

        H2 title = new H2(userName);
        title.getStyle().set("padding", "0px");
        title.getStyle().set("margin", "auto auto auto 0px");

        Span letter = new Span(userName.substring(0, 1));
        letter.addClassName("lettericon");
        letter.getStyle().set("margin-left", "auto");

        usernameTitle.add(letter, title);

        HorizontalLayout info = new HorizontalLayout();
        info.setWidthFull();

        VerticalLayout details = new VerticalLayout();
        details.setWidthFull();

        FormLayout accountDetails = new FormLayout();
        accountDetails.addFormItem(new TextField(), "Full name：");
        accountDetails.addFormItem(new TextField(), "Email:");

        details.add(new H3("Account details"), accountDetails);

        VerticalLayout states = new VerticalLayout();
        states.setWidthFull();

        FormLayout accountStates = new FormLayout();
        accountStates.addFormItem(new Span("0.0"), "Hourly salary");
        accountStates.addFormItem(new Span("0.0"), "Total hours worked: ");

        states.add(new H3("Account states "), accountStates);
        info.add(details, states);

        Button save = new Button("save");
        save.getStyle().set("margin-left", "auto");
        save.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        final var particpation = new HorizontalLayout();
        particpation.setWidthFull();
        
        final var projects = new VerticalLayout(new H3("Current projects"));
        projects.setWidth("50%");

        ProjectList currentProjects = new ProjectList(ProjectList.Modes.LARGE);
        projects.add(currentProjects);

        final var tasks = new VerticalLayout(new H3("Assigned tasks"));
        tasks.setWidth("50%");

        final var allTasks = new VerticalLayout();
        allTasks.addClassName("projectlist");
        final var db = NitriteDBProvider.getInstance();
        db.getTasks().forEach(task -> {
            if (task.getAssignees().contains(user.getId())) {
                final var taskItem = new Div(new Span(task.getName()));
                taskItem.addClassNames("projectitem", "projectitem-small");
                allTasks.add(taskItem);
            }
        });

        tasks.add(allTasks);
        particpation.add(projects, tasks);

        Button logOut = new Button("Log out", e -> BasicAccessControl.getInstance().signOut());
        logOut.getStyle().set("margin", "auto 0px 0px auto");
        logOut.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);

        userprofile.add(back, usernameTitle, info, save, particpation, logOut);
        add(userprofile);
    }

    @Override
    public void setParameter(BeforeEvent event, @WildcardParameter String parameter) {
        if (!parameter.isEmpty()) {
            if (parameter.equals("current")) {
                user = CurrentUser.get();
            } else {
                try {
                    user = NitriteDBProvider.getInstance().getUser(Integer.parseInt(parameter));
                } catch (Exception e) {
                }
            }
        }
        if (user != null) {
            generate();
        }
    }
}
