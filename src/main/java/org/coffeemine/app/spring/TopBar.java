package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

import com.vaadin.flow.router.Route;

public class TopBar extends HorizontalLayout {

    public TopBar() {
        this.setWidthFull();
        this.setHeight("48px");
        this.setClassName("mono_font");
        this.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);

        HorizontalLayout brandLayout = new HorizontalLayout();
        brandLayout.getStyle().set("margin-right", "auto");

        HorizontalLayout tabLayout = new HorizontalLayout();

        HorizontalLayout miscLayout = new HorizontalLayout();
        miscLayout.getStyle().set("margin-left", "auto");

        H3 brandName = new H3("CoffeeMine");
        brandName.getStyle().set("margin", "auto");
        brandName.getStyle().set("padding-left", "10px");

        brandLayout.add(brandName);

        tabLayout.add(RoutingTabs.getTabs());

        Button AccountButton = new Button("Account");

        AccountButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        AccountButton.addClickListener(e -> {
            // TODO: add functionality.
        });

        miscLayout.add(AccountButton);

        this.add(brandLayout, tabLayout, miscLayout);
    }
}
