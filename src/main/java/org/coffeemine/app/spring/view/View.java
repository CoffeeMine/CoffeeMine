package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;
import org.coffeemine.app.spring.auth.ProtectedView;

public abstract class View extends AppLayout implements ProtectedView {
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
}
