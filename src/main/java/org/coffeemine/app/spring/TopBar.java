package org.coffeemine.app.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteData;
import com.vaadin.flow.router.Router;

public class TopBar extends HorizontalLayout {
    static int tabIndex = 0;

    public TopBar() {
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
            tabIndex = routingTabs.getSelectedIndex();
            routingTabs.getUI().ifPresent(ui -> ui.navigate(tabRoute.get(routingTabs.getSelectedTab()).getNavigationTarget()));
        });

        routingTabs.setSelectedIndex(tabIndex);
        add(routingTabs); 
    }
}
