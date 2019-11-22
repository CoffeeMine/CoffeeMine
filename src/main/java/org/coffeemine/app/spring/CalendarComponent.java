package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.stefan.fullcalendar.FullCalendar;

public class CalendarComponent extends VerticalLayout {
    public CalendarComponent(){
        FullCalendar systemCalendar = new FullCalendar();
        Button today = new Button("Today",e -> systemCalendar.today());
        Button previous = new Button("<< Previous", e -> systemCalendar.previous());
        Button next = new Button("next >>",e -> systemCalendar.next());
        add(previous);
        add(today);
        add(next);
        add(systemCalendar);
    }
}