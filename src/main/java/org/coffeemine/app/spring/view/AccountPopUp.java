package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Hr;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.coffeemine.app.spring.components.LetterIcon;
import org.coffeemine.app.spring.userprofile.UserProfile;

class AccountPopUp extends VerticalLayout {

    public AccountPopUp() {
        this.addClassNames("accountinfo", "mono_font");
        this.setVisible(false);

        this.setAlignItems(Alignment.CENTER);

        this.setWidth("350px");
        String userName = "John Bob";
        H3 accountFullname = new H3(userName);
        LetterIcon letterIcon = new LetterIcon(userName.substring(0, 1));
        Text currentProject = new Text("CoffeeMine");

        Hr line = new Hr();
        line.setWidth("80%");

        VerticalLayout projectsList = new VerticalLayout();
        projectsList.setSizeFull();
        projectsList.add(new Text("projects:"));
        for (int i : new int[] { 1, 2, 3 }) {
            HorizontalLayout test = new HorizontalLayout();
            test.addClassName("projectitem");
            test.setAlignItems(Alignment.CENTER);
            test.add(new Text("XYZ Project " + Integer.toString(i)));
            projectsList.add(test);
        }

        HorizontalLayout buttons = new HorizontalLayout();
        buttons.setWidthFull();

        Button detailsButton = new Button("Details", e -> UI.getCurrent().navigate(UserProfile.class));
        detailsButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        Button logoutButton = new Button("Log out");
        logoutButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        logoutButton.getStyle().set("margin-left", "auto");

        buttons.add(detailsButton, logoutButton);

        this.add(letterIcon, accountFullname, currentProject, line, projectsList, buttons);
    }
}