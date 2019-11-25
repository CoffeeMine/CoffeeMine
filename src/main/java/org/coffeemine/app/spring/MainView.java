package org.coffeemine.app.spring;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.server.PWA;
import com.vaadin.flow.theme.Theme;
import com.vaadin.flow.theme.lumo.Lumo;
import org.springframework.beans.factory.annotation.Autowired;

@Route
@Theme(value = Lumo.class, variant = Lumo.DARK)
@PageTitle("CoffeeMine")
@PWA(name = "CoffeeMine, your OpenSource Project Management Tool", shortName = "CoffeeMine")
public class MainView extends AppLayout {

    public MainView(@Autowired MessageBean bean) {
        addToNavbar(new TopBar());
        final var content_div = new Div();
        setContent(content_div);
        content_div.add(new H1("CoffeeMine Boards"));
        Button button = new Button("Click me!",
                e -> Notification.show(bean.getMessage()));
        addToDrawer(button);
        setDrawerOpened(false);
    }

}

