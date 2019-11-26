package org.coffeemine.app.spring;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.html.Div;

public abstract class View extends AppLayout {
    protected Div content_div = new Div();

    protected View(){
        this(new SideBar());
    }
    protected View(SideBar sidebar) {
        addToNavbar(new TopBar());
        addToDrawer(sidebar);
        setContent(content_div);
        setDrawerOpened(false);
    }
    public void add(Component... components){
        content_div.add(components);
    }
}
