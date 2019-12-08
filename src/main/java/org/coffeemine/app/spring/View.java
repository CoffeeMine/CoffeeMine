package org.coffeemine.app.spring;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.router.BeforeEnterEvent;
import com.vaadin.flow.router.BeforeEnterObserver;

import org.coffeemine.app.spring.auth.BasicAccessControl;
import org.coffeemine.app.spring.auth.LoginScreen;

public abstract class View extends AppLayout implements BeforeEnterObserver {
    protected Div content_div = new Div();

    protected View() {
        addToNavbar(new TopBar());
        addToDrawer(new SideBar());
        setContent(content_div);
        setDrawerOpened(false);
    }

    public void add(Component... components) {
        content_div.add(components);
    }

    @Override
    public void beforeEnter(BeforeEnterEvent event) {
        if (!BasicAccessControl.getInstance().isUserSignedIn()) {
            event.forwardTo(LoginScreen.class);
        }
    }
}
