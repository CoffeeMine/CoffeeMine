package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.TextFieldVariant;

import com.vaadin.flow.router.Route;

public class TopBar extends HorizontalLayout {
    static int tabIndex = 0;

    public TopBar() {
        this.setSizeFull();
        this.setMaxWidth("1300px");
        this.getStyle().set("margin", "auto");

        HorizontalLayout tabLayout = new HorizontalLayout();
        HorizontalLayout miscLayout = new HorizontalLayout();

        this.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        tabLayout.setSizeFull();
        tabLayout.add(RoutingTabs.getTabs());


        Button AccountButton = new Button("Account");

        AccountButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        AccountButton.addClickListener(e -> {
            // TODO: add functionality.
        });
        miscLayout.add(AccountButton);

        this.add(tabLayout, miscLayout);
    }
}
