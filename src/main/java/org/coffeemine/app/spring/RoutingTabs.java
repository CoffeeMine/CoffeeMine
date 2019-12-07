package org.coffeemine.app.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.AfterNavigationEvent;
import com.vaadin.flow.router.AfterNavigationObserver;
import com.vaadin.flow.router.RouteData;

import org.coffeemine.app.spring.annonations.Routable;

public class RoutingTabs extends Tabs implements AfterNavigationObserver {

    private Map<Tab, RouteData> tabRoute;
    private Map<RouteData, Tab> routeTab;

    public RoutingTabs() {
        List<RouteData> routes = UI.getCurrent().getRouter().getRegistry().getRegisteredRoutes();

        this.tabRoute = new HashMap<>();
        this.routeTab = new HashMap<>();

        routes.forEach(r -> {
            if (r.getNavigationTarget().isAnnotationPresent(Routable.class)) {
                Tab tab = new Tab(r.getNavigationTarget().getSimpleName());

                this.add(tab);
                this.tabRoute.put(tab, r);
                this.routeTab.put(r, tab);
            }
        });

        this.addSelectedChangeListener(e -> {
            this.getUI().ifPresent(ui -> {
                ui.navigate(this.tabRoute.get(this.getSelectedTab()).getNavigationTarget());
            });
        });
    }

    @Override
    public void afterNavigation(AfterNavigationEvent event) {
        var currentPath = event.getLocation().getPath();
        var routes = UI.getCurrent().getRouter().getRegistry().getRegisteredRoutes();
        for (var x : routes) {
            if (x.getUrl().equals(currentPath) && x.getNavigationTarget().isAnnotationPresent(Routable.class)) {
                this.setSelectedTab(this.routeTab.get(x));
            }
        }
    }
}
