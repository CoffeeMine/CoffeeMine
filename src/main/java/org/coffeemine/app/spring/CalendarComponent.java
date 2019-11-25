package org.coffeemine.app.spring;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.FullCalendar;

public class CalendarComponent extends VerticalLayout {

    private static FullCalendar systemCalendar;

    public CalendarComponent(){
        systemCalendar = new FullCalendar();
        add(systemCalendar);
    }

    static void calendarGoNext(){
        systemCalendar.next();
    }

    static void calendarGoPrevious(){
        systemCalendar.previous();
    }

    static void calendarGoToday(){
        systemCalendar.today();
    }

    static void changeCalendarView(String viewName){
        switch(viewName) {
            case "Day":
                systemCalendar.changeView(CalendarViewImpl.DAY_GRID_DAY);
                break;
            case "Week":
                systemCalendar.changeView(CalendarViewImpl.DAY_GRID_WEEK);
                break;
            default:
                systemCalendar.changeView(CalendarViewImpl.DAY_GRID_MONTH);
                break;
        }
    }
}