package org.coffeemine.app.spring.calendar;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import org.coffeemine.app.spring.components.SprintCreation;
import org.coffeemine.app.spring.components.TaskCreation;
import org.coffeemine.app.spring.view.View;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDateTime;

@Route
@NavbarRoutable
@CssImport("./styles/material-full-calendar.css")
class Calendar extends View {
    private FullCalendar systemCalendar;
    private Text date;
    HorizontalLayout calendarController;
    VerticalLayout calendar;

    Calendar(){
        super();
        this.calendarController = new HorizontalLayout();
        this.calendar = new VerticalLayout();

        Button today = new Button("Today", event -> systemCalendar.today());
        Button previous = new Button("<< Previous", event -> systemCalendar.previous());
        Button next = new Button("next >>",event -> systemCalendar.next());

        DatePicker datePicker = new DatePicker();
        datePicker.setPlaceholder("Date within this month");
        datePicker.setClearButtonVisible(true);
        datePicker.addValueChangeListener(event -> systemCalendar.gotoDate(event.getValue()));

        Select<String> selectView = new Select<>("Month", "Week", "Day");
        selectView.setPlaceholder("View as..");
        selectView.addValueChangeListener(event -> this.changeCalendarView(event.getValue()));

        Button newSprint = new Button("New sprint", event -> this.createNewSprint());

        Button newTask = new Button("New task", event -> this.createNewTask());

        Div newEventCreation = new Div();
        newEventCreation.add(newSprint);
        newEventCreation.add(newTask);
        newEventCreation.getStyle().set("marginLeft","auto");

        calendarController.add(previous);
        calendarController.add(today);
        calendarController.add(next);
        calendarController.add(datePicker);
        calendarController.add(selectView);
        calendarController.add(newEventCreation);

        systemCalendar = new FullCalendar();
        systemCalendar.getStyle().set("z-index", "0");
        date = new Text(LocalDateTime.now().getYear() + "  "
                + LocalDateTime.now().getMonth().toString() + "  " +
                LocalDateTime.now().getDayOfMonth());
        systemCalendar.setFirstDay(DayOfWeek.MONDAY);
        systemCalendar.setBusinessHours();

        calendar.add(calendarController);
        calendar.add(date);
        calendar.add(systemCalendar);

        add(calendar);
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

    void createNewSprint(){
        SprintCreation newSprint = new SprintCreation();
        newSprint.sprintCreating(systemCalendar);

    }

    void createNewTask(){
        TaskCreation newTask = new TaskCreation();
        newTask.taskCreating(systemCalendar);
    }
}
