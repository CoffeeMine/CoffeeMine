package org.coffeemine.app.spring;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class Calendar extends VerticalLayout {

    Calendar(){
        add(new CalendarController());
        add(new CalendarComponent());
    }
}
