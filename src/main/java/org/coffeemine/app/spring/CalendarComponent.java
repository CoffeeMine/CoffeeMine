package org.coffeemine.app.spring;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

public class CalendarComponent extends VerticalLayout {

    private static FullCalendar systemCalendar;

    public CalendarComponent(){
        systemCalendar = new FullCalendar();
        systemCalendar.setFirstDay(DayOfWeek.MONDAY);
        systemCalendar.setBusinessHours();
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
                Notification.show(viewName + " view now");
                break;
            case "Week":
                systemCalendar.changeView(CalendarViewImpl.DAY_GRID_WEEK);
                Notification.show(viewName + " view now");
                break;
            default:
                systemCalendar.changeView(CalendarViewImpl.DAY_GRID_MONTH);
                Notification.show(viewName + " view now");
                break;
        }
    }

    static void createNewTask(){
        Entry newEvent = new Entry(
                "0101",
                "Nice Calendar View",
                LocalDateTime.now(),
                LocalDateTime.of(2019,11,28,12,00),
                true,
                true,
                " Deep blue",
                "This is a demo task");
        systemCalendar.addEntries(newEvent);
        Notification.show("Task " + "<Nice Calendar View>" + " is now added");
    }
}