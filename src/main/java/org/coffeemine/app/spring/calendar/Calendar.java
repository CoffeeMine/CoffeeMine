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
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.components.EventsDialog.SprintCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskDetail;
import org.coffeemine.app.spring.data.Fragment;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.View;
import org.vaadin.stefan.fullcalendar.CalendarViewImpl;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

import static java.time.temporal.ChronoUnit.DAYS;

@Route
@NavbarRoutable
@CssImport("./styles/material-full-calendar.css")
class Calendar extends View {
    private FullCalendar calendar = new FullCalendar();

    public Calendar() {
        super();

        final var date_picker = new DatePicker(LocalDate.now());
        date_picker.setPlaceholder("Date within this month");
        date_picker.setClearButtonVisible(true);
        date_picker.addValueChangeListener(event -> {
            if (event.getValue() != null)
                calendar.gotoDate(event.getValue());
        });

        final var today = new Button("Today", event -> {
            calendar.today();
            date_picker.setValue(LocalDate.now());
        });
        final var previous = new Button("<< Previous", event -> {
            calendar.previous();
            date_picker.setValue(date_picker.getValue().minusDays(1));
        });
        final var next = new Button("Next >>", event -> {
            calendar.next();
            date_picker.setValue(date_picker.getValue().plusDays(1));
        });

        final var select_view = new Select<>("Month", "Week", "Day");
        select_view.setPlaceholder("View as..");
        select_view.setValue("Month");
        select_view.addValueChangeListener(event -> changeCalendarView(event.getValue()));

        final var new_sprint = new Button("New sprint", e -> createSprint());
        final var new_task = new Button("New task", e -> createTask());

        final var event_creation = new Div(new_sprint, new_task);
        event_creation.getStyle().set("margin-left", "auto");

        final var controls = new HorizontalLayout();
        controls.setWidthFull();
        controls.add(previous, today, next, date_picker, select_view, event_creation);

        calendar.getStyle().set("z-index", "0");
        calendar.setFirstDay(DayOfWeek.MONDAY);
        calendar.setBusinessHours();
        calendar.addEntryClickedListener(event -> openTask(event.getEntry()));
        calendar.setHeight(1000);

        updateEvents();

        final var container = new VerticalLayout(controls, calendar);
        add(container);
    }

    void updateEvents() {
        calendar.removeAllEntries();
        final var colorsSet = new ArrayList<String>();
        colorsSet.add("mediumseagreen");
        colorsSet.add("tomato");
        colorsSet.add("orange");
        colorsSet.add("dodgerblue");
        colorsSet.add("gray");
        colorsSet.add("slateblue");
        colorsSet.add("violet");
        NitriteDBProvider.getInstance().getSprints4Project(NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get())).map(sprint ->
                new Entry(
                        "S" + sprint.getId(),
                        Integer.toString(sprint.getId()),
                        sprint.getStart().atStartOfDay(),
                        sprint.getEnd().plusDays(1).atStartOfDay(),
                        true,
                        true,
                        colorsSet.get(new Random().nextInt(colorsSet.size() - 1)),
                        ""))
                .peek(entry -> entry.setRenderingMode(Entry.RenderingMode.BACKGROUND))
                .forEach(calendar::addEntry);
        NitriteDBProvider.getInstance().getTasks4Project(NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get())).map(task ->
                new Entry(
                        "T" + task.getId(),
                        task.getName(),
                        NitriteDBProvider.getInstance().getFragments4Task(task).map(Fragment::getBegin).min(Comparator.naturalOrder()).orElse(LocalDateTime.now()),
                        NitriteDBProvider.getInstance().getFragments4Task(task).map(Fragment::getEnd).max(Comparator.naturalOrder()).orElse(LocalDateTime.now().plus(10, DAYS)),
                        true,
                        true,
                        colorsSet.get(new Random().nextInt(colorsSet.size() - 1)),
                        task.getDescription()))
                .forEach(calendar::addEntry);
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
        new TaskDetail(Integer.parseInt(currentEntry.getId().substring(1)), t -> updateEvents());
    }
}
