package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs;

public class SideBar extends VerticalLayout {

    public SideBar() {
        this.setClassName("sidebar");
        this.setSizeFull();

        VerticalLayout tabLayout = new VerticalLayout();
        HorizontalLayout miscLayout = new HorizontalLayout();
        miscLayout.setWidthFull();
        Button closeButton = new Button(new Icon(VaadinIcon.CLOSE_BIG));
        closeButton.getStyle().set("border-radius", "1000px");

        closeButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        // miscLayout.add(closeButton);
        miscLayout.setJustifyContentMode(FlexComponent.JustifyContentMode.END);

        Tabs routingTabs = new RoutingTabs();
        routingTabs.setWidthFull();
        routingTabs.setOrientation(Tabs.Orientation.VERTICAL);
        tabLayout.add(routingTabs);
        tabLayout.setPadding(false);
        tabLayout.setMargin(false);

        this.add(miscLayout, tabLayout);
    }
}
