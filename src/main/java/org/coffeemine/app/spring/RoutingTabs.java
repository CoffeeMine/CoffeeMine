package org.coffeemine.app.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.Router;

public class RoutingTabs {

    public static Tabs getTabs() {
        Router router = UI.getCurrent().getRouter();
        List<RouteData> routes = router.getRegistry().getRegisteredRoutes();

        Map<Tab, RouteData> tabRoute = new HashMap<>();
        Tabs routingTabs = new Tabs();

        routes.forEach(r -> {
            Tab tab = new Tab(r.getNavigationTarget().getSimpleName());
            routingTabs.add(tab);
            tabRoute.put(tab, r);
        });

        routingTabs.addSelectedChangeListener(e -> {
            routingTabs.getUI().ifPresent(ui -> {
                routingTabs.setSelectedIndex(routingTabs.getSelectedIndex());
                ui.navigate(tabRoute.get(routingTabs.getSelectedTab()).getNavigationTarget());
            });
        });

        routingTabs.setSelectedIndex(routingTabs.getSelectedIndex());

        return routingTabs;
    }
}
