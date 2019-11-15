package org.coffeemine.app.spring;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.router.RouterLink;

public class TopBar extends HorizontalLayout {
    public TopBar(){
        final var router = UI.getCurrent().getRouter();
        final var routes = router.getRegistry().getRegisteredRoutes();

        routes.forEach(r -> add(new RouterLink(r.getNavigationTarget().getSimpleName(), r.getNavigationTarget())));
    }
}
