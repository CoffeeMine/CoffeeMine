package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import org.coffeemine.app.spring.annonations.NavbarRoutable;

@Route(value = "")
@RouteAlias(value = "Overview")
@NavbarRoutable
@Theme(value = Material.class, variant = Material.DARK)
@CssImport("./styles/shared-styles.css")
@PageTitle("CoffeeMine")
@PWA(name = "CoffeeMine, your OpenSource Project Management Tool", shortName = "CoffeeMine")
public class Overview extends View {

    public Overview() {
    }

}
