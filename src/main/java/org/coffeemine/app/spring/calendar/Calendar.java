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
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.components.EventsDialog.SprintCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskDetail;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.View;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Route
@NavbarRoutable
@CssImport("./styles/material-full-calendar.css")
class Calendar extends View {
    private FullCalendar calendar = new FullCalendar();

    public Calendar(){
        super();
        final var today = new Button("Today", event -> calendar.today());
        final var previous = new Button("<< Previous", event -> calendar.previous());
        final var next = new Button("Next >>",event -> calendar.next());

        final var date_picker = new DatePicker();
        date_picker.setPlaceholder("Date within this month");
        date_picker.setClearButtonVisible(true);
        date_picker.addValueChangeListener(event -> calendar.gotoDate(event.getValue()));

        final var select_view = new Select<>("Month", "Week", "Day");
        select_view.setPlaceholder("View as..");
        select_view.addValueChangeListener(event -> changeCalendarView(event.getValue()));

        final var new_sprint = new Button("New sprint", e -> createSprint());
        final var new_task = new Button("New task", e -> createTask());

        final var event_creation = new Div(new_sprint, new_task);
        event_creation.getStyle().set("margin-left", "auto");

        final var controls = new HorizontalLayout();
        controls.setWidthFull();
        controls.add(previous, today, next, date_picker, select_view, event_creation);

        calendar.getStyle().set("z-index", "0");
        final var date = new Text(LocalDateTime.now().format(DateTimeFormatter.ofPattern("d MMM yyyy")));
        calendar.setFirstDay(DayOfWeek.MONDAY);
        calendar.setBusinessHours();
        calendar.addEntryClickedListener(event -> openTask(event.getEntry()));

        updateEvents();

        final var container = new VerticalLayout(controls, date, calendar);
        add(container);
    }

    void updateEvents() {
        calendar.removeAllEntries();
        NitriteDBProvider.getInstance().getSprints().forEach(sprint -> {
            final var entry = new Entry(
                    Integer.toString(sprint.getId()),
                    Integer.toString(sprint.getId()),
                    sprint.getStart().atStartOfDay(),
                    sprint.getEnd().plusDays(1).atStartOfDay(),
                    true,
                    true,
                    "red",
                    "");
            entry.setRenderingMode(Entry.RenderingMode.BACKGROUND);
            calendar.addEntry(entry);
        });
        NitriteDBProvider.getInstance().getTasks().forEach(task -> {
            final var entry = new Entry(
                    Integer.toString(task.getId()),
                    task.getName(),
                    LocalDateTime.now(),
                    LocalDateTime.of(2019, 11, 28, 12, 0),
                    true,
                    true,
                    " dodgerblue",
                    task.getDescription());
            calendar.addEntry(entry);
        });
    }

    void changeCalendarView(String view_name){
        switch(view_name) {
            case "Day":
                calendar.changeView(CalendarViewImpl.DAY_GRID_DAY);
                break;
            case "Week":
                calendar.changeView(CalendarViewImpl.DAY_GRID_WEEK);
                break;
            default:
                calendar.changeView(CalendarViewImpl.DAY_GRID_MONTH);
        }
        Notification.show(view_name + " view now");
    }

    private void createSprint(){
        new SprintCreation(s -> updateEvents());
    }

    private void createTask(){
        new TaskCreation(t -> updateEvents());
    }

    private void openTask(Entry currentEntry) {
        System.out.println("Calendar.openTask");
        new TaskDetail(Integer.parseInt(currentEntry.getId()), t -> updateEvents());
    }
}
