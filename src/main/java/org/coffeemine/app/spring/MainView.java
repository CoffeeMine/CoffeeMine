package org.coffeemine.app.spring;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

@Route
@Theme(value = Material.class, variant = Material.DARK)
@CssImport("./styles/shared-styles.css")
@PageTitle("CoffeeMine")
@PWA(name = "CoffeeMine, your OpenSource Project Management Tool", shortName = "CoffeeMine")
public class MainView extends AppLayout {

        addToNavbar(new TopBar());
    public MainView() {
        final var content_div = new Div();
        setContent(content_div);
        content_div.add(new H1("CoffeeMine Boards"));

        final var sidebar = new SideBar();
        addToDrawer(sidebar);

    }

}

