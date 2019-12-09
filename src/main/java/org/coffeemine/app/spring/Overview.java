package org.coffeemine.app.spring;

import com.vaadin.flow.component.charts.model.Pane;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.NativeButton;
import com.vaadin.flow.component.listbox.ListBox;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.material.Material;

import java.awt.*;

@Route(value = "")
@RouteAlias(value = "Overview")
@Theme(value = Material.class, variant = Material.DARK)
@CssImport("./styles/shared-styles.css")
@PageTitle("CoffeeMine")
@PWA(name = "CoffeeMine, your OpenSource Project Management Tool", shortName = "CoffeeMine")
public class Overview extends View {

    public Overview() {
        super();
        add(new H1("Tasks for sprint #3"));

        Div layout = new Div();
        layout.getStyle().set("overflow-x", "auto");
        this.add(layout);

        HorizontalLayout taskLayout = new HorizontalLayout();
        taskLayout.setSizeUndefined();
        layout.add(taskLayout);

        Taskbox one = new Taskbox("Task one", "Lorem ipsum");
        taskLayout.add(one);

        Taskbox two = new Taskbox("Task two or maybe ten who knows", "Lorem ipsum and a lot of other things as well");
        taskLayout.add(two);

        Taskbox three = new Taskbox("Task three", "Lorem ipsum");
        taskLayout.add(three);

        Taskbox four = new Taskbox("Task four", "Lorem ipsum");
        taskLayout.add(four);

        Taskbox five = new Taskbox("Task five", "Lorem ipsum");
        taskLayout.add(five);

        Taskbox six = new Taskbox("Task six", "Lorem ipsum");
        taskLayout.add(six);




    }

}
