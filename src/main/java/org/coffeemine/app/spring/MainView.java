package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;

@Route
@PageTitle("CoffeMine")
@PWA(name = "CoffeeMine, your OpenSource Project Management Tool", shortName = "CoffeeMine")
public class MainView extends VerticalLayout {

    public MainView(@Autowired MessageBean bean) {

        add(new H1("CoffeMine Boards"));
        Button button = new Button("Click me!",
                e -> Notification.show(bean.getMessage()));
        add(button);
    }

}

