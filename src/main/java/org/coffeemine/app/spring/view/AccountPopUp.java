package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.coffeemine.app.spring.auth.BasicAccessControl;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.components.LetterIcon;
import org.coffeemine.app.spring.components.ProjectList;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.userprofile.UserProfile;

class AccountPopUp extends VerticalLayout {

    public AccountPopUp() {
        final var db = NitriteDBProvider.getInstance();

        this.addClassNames("accountinfo", "mono_font");
        this.setVisible(false);

        this.setAlignItems(Alignment.CENTER);

        this.setWidth("350px");

        User user = CurrentUser.get();
        var userName = "No User";
        var currentProjectName = "No Project";
        if(user != null) {
            userName = user.getName();
            currentProjectName = db.getCurrentProject(user).getName();
        }
        H3 accountFullname = new H3(userName);
        LetterIcon letterIcon = new LetterIcon(userName.substring(0, 1));
        Text currentProject = new Text(currentProjectName);

        Hr line = new Hr();
        line.setWidth("80%");

        final var projectsList = new ProjectList(ProjectList.Modes.SMALL, 3, new Text("projects:"));

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setWidthFull();

        Button detailsButton = new Button("Profile", e -> UI.getCurrent().navigate(UserProfile.class, "current"));
        detailsButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        Button logoutButton = new Button("Log out", e -> BasicAccessControl.getInstance().signOut());
        logoutButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        logoutButton.getStyle().set("margin-left", "auto");

        buttons.add(detailsButton, logoutButton);

        this.add(letterIcon, accountFullname, currentProject, line, projectsList, buttons);
    }
}