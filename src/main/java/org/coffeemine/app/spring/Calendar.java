package org.coffeemine.app.spring;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.Route;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Route
@CssImport("./styles/material-full-calendar.css")
class Calendar extends View{
    private FullCalendar systemCalendar;
    private Text date;
    HorizontalLayout calendarController;
    VerticalLayout calendar;
    FormLayout newForm;

    Calendar(){
        super();
        this.calendarController = new HorizontalLayout();
        this.calendar = new VerticalLayout();
        this.newForm = new FormLayout();

        Button today = new Button("Today", event -> systemCalendar.today());
        Button previous = new Button("<< Previous", event -> systemCalendar.previous());
        Button next = new Button("next >>",event -> systemCalendar.next());
        Select<String> selectView = new Select<>("Month", "Week", "Day");
        selectView.setPlaceholder("View as..");
        selectView.addValueChangeListener(event -> this.changeCalendarView(event.getValue()));
        Button newTask = new Button("New task", event -> this.newTaskForm());
        newTask.getStyle().set("margin-left","auto");
        DatePicker datePicker = new DatePicker();

        datePicker.setPlaceholder("Date within this month");
        datePicker.setClearButtonVisible(true);
        datePicker.addValueChangeListener(event -> systemCalendar.gotoDate(event.getValue()));

        calendarController.add(previous);
        calendarController.add(today);
        calendarController.add(next);
        calendarController.add(selectView);
        calendarController.add(datePicker);
        calendarController.add(newTask);

        systemCalendar = new FullCalendar();
        date = new Text(LocalDateTime.now().getYear() + "  "
                + LocalDateTime.now().getMonth().toString() + "  " +
                LocalDateTime.now().getDayOfMonth());
        systemCalendar.setFirstDay(DayOfWeek.MONDAY);
        systemCalendar.setBusinessHours();

        calendar.add(calendarController);
        calendar.add(newForm);
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

    void newTaskForm() {
        Dialog dialog = new Dialog();
        dialog.add(new Label("Close me with the esc-key or an outside click"));

        TextField taskName = new TextField();
        DatePicker startTime = new DatePicker();
        startTime.setValue(LocalDate.now());
        DatePicker endTime = new DatePicker();
        endTime.setValue(LocalDate.now());
        TextArea description = new TextArea();
        description.setPlaceholder("Please provide task description here");
        Select<String> assignPeople = new Select<>("Bob", "John", "Rick", "Mahaa", "Tylo");
        assignPeople.setPlaceholder("Assigning to..");
        Select<String> assignSprint = new Select<>("Sprint 1", "Sprint 2", "Sprint 3");
        assignSprint.setPlaceholder("Assigning to sprint..");
        //selectView.addValueChangeListener(event ->);
        Button save = new Button("Create");
        Button reset = new Button("Reset");
        save.addClickListener(event -> {
            this.createNewTask();
            dialog.close();
        });
        //reset.addClickListener(event -> { });

        newForm.removeAll();
        newForm.addFormItem(taskName, "Task Name");
        newForm.addFormItem(assignPeople, "Task assign");
        newForm.addFormItem(startTime,"Start Time");
        newForm.addFormItem(endTime,"End Time");
        newForm.addFormItem(assignSprint, "Sprint assign");
        newForm.addFormItem(description, "Task Description");
        newForm.add(save, reset);

        dialog.add(newForm);
        dialog.open();
    }
}
