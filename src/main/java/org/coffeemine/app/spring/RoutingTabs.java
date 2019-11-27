package org.coffeemine.app.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.RouteData;

public class RoutingTabs extends Tabs {

    public RoutingTabs() {
        List<RouteData> routes = UI.getCurrent().getRouter().getRegistry().getRegisteredRoutes();

        Map<Tab, RouteData> tabRoute = new HashMap<>();

        routes.forEach(r -> {
            Tab tab = new Tab(r.getNavigationTarget().getSimpleName());
            this.add(tab);
            tabRoute.put(tab, r);
        });

        this.addSelectedChangeListener(e -> {
            this.getUI().ifPresent(ui -> {
                ui.navigate(tabRoute.get(this.getSelectedTab()).getNavigationTarget());
            });
        });
    }
}
