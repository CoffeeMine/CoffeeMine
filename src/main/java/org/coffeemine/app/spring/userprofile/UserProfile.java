package org.coffeemine.app.spring.userprofile;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.WildcardParameter;

import org.coffeemine.app.spring.auth.BasicAccessControl;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.auth.LoginScreen;
import org.coffeemine.app.spring.auth.ProtectedView;
import org.coffeemine.app.spring.components.AddProjectDialog;
import org.coffeemine.app.spring.components.ProjectList;
import org.coffeemine.app.spring.components.EventsDialog.TaskDetail;
import org.coffeemine.app.spring.components.EventsDialog.UserCreation;
import org.coffeemine.app.spring.components.SpaceableTextField;
import org.coffeemine.app.spring.data.ChangeTracker;
import org.coffeemine.app.spring.data.Fragment;
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

        final var db = NitriteDBProvider.getInstance();

        VerticalLayout userprofile = new VerticalLayout();
        userprofile.addClassName("userprofile");

        final var topControl = new HorizontalLayout();
        topControl.setPadding(false);
        topControl.setWidthFull();

        Button back = new Button("back");
        back.addClickListener(e -> UI.getCurrent().navigate(Overview.class));
        back.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        topControl.add(back);

        if (CurrentUser.get().getStatus().equals(User.Status.ADMIN)) {
            final var addUserButton = new Button("Add User", e -> {
                final var userCreator = new UserCreation();
                userCreator.open();
            });
            addUserButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
            addUserButton.getStyle().set("margin-left", "auto");
            topControl.add(addUserButton);
        }

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
        final var fullNameInput = new SpaceableTextField();
        fullNameInput.setValue(user.getName());

        final var emailInput = new SpaceableTextField();
        emailInput.setValue(user.getEmail());

        accountDetails.addFormItem(fullNameInput, "Full name：");
        accountDetails.addFormItem(emailInput, "Email:");

        details.add(new H3("Account details"), accountDetails);

        VerticalLayout states = new VerticalLayout();
        states.setWidthFull();

        FormLayout accountStates = new FormLayout();
        accountStates.addFormItem(new Span(Float.toString(user.getHourlySalary())), "Hourly salary");
        accountStates.addFormItem(new Span(Integer.toString(db.getFragments4User(user).mapToInt(Fragment::getHours).sum())), "Total hours worked: ");

        states.add(new H3("Account states "), accountStates);
        info.add(details, states);

        Button save = new Button("save", e -> {
            user.setName(fullNameInput.getOptionalValue().orElse(user.getName()));
            user.setEmail(emailInput.getOptionalValue().orElse(user.getEmail()));
            db.updateUser(user);
            Notification.show("Updated user succesfully.", 2000, Notification.Position.BOTTOM_END);
        });

        if (CurrentUser.get().getId() != user.getId()) {
            save.setEnabled(false);
            fullNameInput.setEnabled(false);
            emailInput.setEnabled(false);
        }

        save.getStyle().set("margin-left", "auto");
        save.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        final var particpation = new HorizontalLayout();
        particpation.setWidthFull();
        particpation.setHeight("26rem");
        
        final var projects = new VerticalLayout();
        projects.setWidth("50%");

        ProjectList currentProjects = new ProjectList(ProjectList.Modes.LARGE);

        // TODO: somehow align all this properly.
        final var projectsHeader = new Div();
        projectsHeader.getStyle().set("margin", "calc(0.4 * var(--material-h5-font-size)) 0px");
        projectsHeader.getStyle().set("max-height", "var(--material-h5-font-size)");
        projectsHeader.setWidthFull();

        final var projectsText = new Span("Current Projects");
        projectsText.getStyle().set("font-size", "var(--material-h5-font-size)");
        projectsHeader.add(projectsText);

        if (CurrentUser.get().getStatus().equals(User.Status.ADMIN)) {
            final var newProjectButton = new Button("New Project", e -> {
                final var createProjectDialog = new AddProjectDialog();
                createProjectDialog.open();
            });
            newProjectButton.getStyle().set("float", "right");
            projectsHeader.add(newProjectButton);
        }
        projects.add(projectsHeader, currentProjects);

        final var tasks = new VerticalLayout(new H3("Assigned tasks"));
        tasks.setWidth("50%");

        final var allTasks = new VerticalLayout();
        allTasks.addClassName("projectlist");
        db.getTasks().sorted((a, b) -> -Long.compare(((ChangeTracker) a).getLastModifiedTime(), ((ChangeTracker) b).getLastModifiedTime()))
        .forEach(task -> {
            if (task.getAssignees().contains(user.getId())) {
                final var taskItem = new Div(new Span(task.getName()));
                taskItem.addClassNames("projectitem", "projectitem-small");
                taskItem.addClickListener(e -> {
                    final var detailDialog = new TaskDetail(task.getId(), t -> {
                        UI.getCurrent().navigate(LoginScreen.class);
                        UI.getCurrent().navigate(UserProfile.class, Integer.toString(user.getId()));
                    });
                    detailDialog.open();
                });
                allTasks.add(taskItem);
            }
        });

        tasks.add(allTasks);
        particpation.add(projects, tasks);

        Button logOut = new Button("Log out", e -> BasicAccessControl.getInstance().signOut());
        logOut.getStyle().set("margin", "auto 0px 0px auto");
        logOut.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);

        userprofile.add(topControl, usernameTitle, info, save, particpation, logOut);
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
