package org.coffeemine.app.spring;

import com.vaadin.flow.router.Route;

@Route
public class Calendar extends View {
    Calendar(){
        super();
        add(new CalendarController());
        add(new CalendarComponent());
    }
}
