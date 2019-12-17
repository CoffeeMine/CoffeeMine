package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.db.NitriteDBProvider;

@Route(value = "")
@RouteAlias(value = "Overview")
@NavbarRoutable
@Theme(value = Material.class, variant = Material.DARK)
@CssImport("./styles/shared-styles.css")
@PageTitle("CoffeeMine")
@PWA(name = "CoffeeMine, your OpenSource Project Management Tool", shortName = "CoffeeMine")
public class Overview extends View {

    public Overview() {
        if (CurrentUser.get() != null) {
            var currentProject = NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get());
            var currentSprint = NitriteDBProvider.getInstance().getCurrentSprint(currentProject);

            add(new H1("Tasks Of Current Sprint"), new TasksView(currentSprint));
        }
    }
}
