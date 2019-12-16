package org.coffeemine.app.spring.calendar;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.router.Route;

import org.coffeemine.app.spring.components.EventsDialog.SprintCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskDetail;
import org.coffeemine.app.spring.data.Sprint;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.View;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Route
@NavbarRoutable
@CssImport("./styles/material-full-calendar.css")
class Calendar extends View {
    private FullCalendar systemCalendar;
    private LocalDate dateLog;
    private HorizontalLayout calendarController;
    private VerticalLayout calendar;

    Calendar(){
        super();
        this.calendarController = new HorizontalLayout();
        this.calendarController.setWidthFull();
        this.calendar = new VerticalLayout();
        dateLog = LocalDate.now();

        DatePicker datePicker = new DatePicker();
        datePicker.setPlaceholder("Date within this month");
        datePicker.setClearButtonVisible(true);
        datePicker.setValue(dateLog);
        datePicker.addValueChangeListener(event -> systemCalendar.gotoDate(event.getValue()));

        Button today = new Button("Today", event -> {
            systemCalendar.today();
            dateLog = LocalDate.now();
            datePicker.setValue(dateLog);
        });
        Button previous = new Button("<< Previous", event -> {
            systemCalendar.previous();
            dateLog = dateLog.minusMonths(1);
            datePicker.setValue(dateLog);
        });
        Button next = new Button("next >>",event -> {
            systemCalendar.next();
            dateLog = dateLog.plusMonths(1);
            datePicker.setValue(dateLog);
        });

        Select<String> selectView = new Select<>("Month", "Week", "Day");
        selectView.setPlaceholder("View as..");
        selectView.addValueChangeListener(event -> this.changeCalendarView(event.getValue()));

        Button newSprint = new Button("New sprint", event -> this.createNewSprint());
        Button newTask = new Button("New task", event -> this.createNewTask());

        Div newEventCreation = new Div();
        newEventCreation.add(newSprint);
        newEventCreation.add(newTask);
        newEventCreation.getStyle().set("margin-left","auto");

        calendarController.add(previous);
        calendarController.add(today);
        calendarController.add(next);
        calendarController.add(datePicker);
        calendarController.add(selectView);
        calendarController.add(newEventCreation);

        systemCalendar = new FullCalendar();
        systemCalendar.getStyle().set("z-index", "0");
        systemCalendar.setFirstDay(DayOfWeek.MONDAY);
        systemCalendar.setBusinessHours();
        systemCalendar.addEntryClickedListener(event -> this.maintainTask(event.getEntry()));
        updateCalendar();

        calendar.add(calendarController);
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
        newSprint.open();
        newSprint.addDetachListener(event -> {
            updateCalendar();
        });
    }

    void createNewTask(){
        TaskCreation newTask = new TaskCreation();
        newTask.open();
        newTask.addDetachListener(event -> {
            updateCalendar();
        });
    }

    void maintainTask(Entry currentEntry){
        TaskDetail taskDetail = new TaskDetail(Integer.parseInt(currentEntry.getId()));
        taskDetail.open();
        taskDetail.addDetachListener(event -> {
            updateCalendar();
        });
    }

    void updateCalendar(){
        Object[] sprints = NitriteDBProvider.getInstance().getSprints().toArray();
        Object[] tasks = NitriteDBProvider.getInstance().getTasks().toArray();
        for (Object sprint : sprints) {
            Sprint castToSprint = (Sprint)sprint;
            Entry newEntry = new Entry(
                    Integer.toString(castToSprint.getId()),
                    Integer.toString(castToSprint.getId()),
                    castToSprint.getStart().atStartOfDay(),
                    castToSprint.getEnd().plusDays(1).atStartOfDay(),
                    true,
                    true,
                    "red",
                    "");
            newEntry.setRenderingMode(Entry.RenderingMode.BACKGROUND);
            systemCalendar.addEntry(newEntry);
        }
        for (Object task : tasks) {
            Task castToTask = (Task) task;
            Entry newEntry = new Entry(
                    Integer.toString(castToTask.getId()),
                    castToTask.getName(),
                    LocalDateTime.now(),
                    LocalDateTime.of(2019, 11, 28, 12, 00),
                    true,
                    true,
                    " dodgerblue",
                    castToTask.getDescription());
            systemCalendar.addEntry(newEntry);
        }
    }
}
