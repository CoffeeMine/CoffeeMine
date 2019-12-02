package org.coffeemine.app.spring;

import com.vaadin.flow.router.Route;

@Route
public class Calendar extends View {
    CalendarForm calendarForm;

    Calendar(){
        super();
        calendarForm = new CalendarForm();
        add(new CalendarComponent(calendarForm));
        add(calendarForm);
    }
}
