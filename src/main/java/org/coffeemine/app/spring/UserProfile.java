package org.coffeemine.app.spring;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

    @Route
    class UserProfile extends VerticalLayout {
        public UserProfile() {
            this.setWidthFull();
            this.setMaxWidth("1200px");
            Button back = new Button("back");
            back.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
            this.add(back);

            // Header
            H2 title = new H2("Account Name");
            Icon user = VaadinIcon.USER.create();
            user.setSize("5em");
            add(back);
            add(user);
            this.add(title);

            HorizontalLayout info = new HorizontalLayout();
            info.setWidthFull();
            this.add(info);
            // details
            VerticalLayout details = new VerticalLayout();
            details.setWidthFull();
            details.add(new H3("Account details"));

            FormLayout accountDetails = new FormLayout();
            accountDetails.addFormItem(new TextField(), "Full name:");
            accountDetails.addFormItem(new TextField(), "Email:");
            details.add(accountDetails);

            VerticalLayout states = new VerticalLayout();
            states.setWidthFull();
            states.add(new H3("Account states"));
            FormLayout accountStates = new FormLayout();
            accountStates.addFormItem(new Span("0.0"),"Salary per hour");
            accountStates.addFormItem(new Span("0.0"),"Total hours worked");
            states.add(accountStates);
            info.add(details, states);

            Icon clock = VaadinIcon.CLOCK.create();
            clock.setSize("4em");
            Button save = new Button("save");
            save.getStyle().set("margin-left", "auto");
            save.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
            FormLayout projectsName = new FormLayout();
            projectsName.addFormItem(new TextField(), "Current project:");
            add(clock);
            add(save);
            add(projectsName);
            Button logOut = new Button("Log out");
            logOut.getStyle().set("margin-left", "auto");
            logOut.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
            add(logOut);
        }
    }