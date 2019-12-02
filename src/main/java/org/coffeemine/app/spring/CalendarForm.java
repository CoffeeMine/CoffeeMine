package org.coffeemine.app.spring;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CalendarForm extends VerticalLayout {

    private FullCalendar systemCalendar;
    private Text date;

    public CalendarForm(){
        systemCalendar = new FullCalendar();
        date = new Text(LocalDateTime.now().getYear() + "  " + LocalDateTime.now().getMonth().toString());
        systemCalendar.setFirstDay(DayOfWeek.MONDAY);
        systemCalendar.setBusinessHours();
        add(date);
        add(systemCalendar);
    }

    void calendarGoNext(){
        systemCalendar.next();
    }

    void calendarGoPrevious(){
        systemCalendar.previous();
    }

    void calendarGoToday(){
        systemCalendar.today();
    }

    void changeCalendarView(String viewName){
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

    void createNewTask(){
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

    void calendarGoToDate(LocalDate date) {
        systemCalendar.gotoDate(date);
    }
}