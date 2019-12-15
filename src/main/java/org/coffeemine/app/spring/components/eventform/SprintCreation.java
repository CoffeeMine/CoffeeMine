package org.coffeemine.app.spring.components.eventform;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import org.coffeemine.app.spring.data.Sprint;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.LocalDate;

public class SprintCreation extends EventForm{
    private Sprint sprint;

    public SprintCreation() {
        super();
        sprint = new Sprint();
    }

    public void sprintCreating(FullCalendar AddedCalendar) {
        sprintCreating();
        getSave().addClickListener(event ->{
            Entry newEntry = new Entry(
                    Integer.toString(sprint.getId()),
                    Integer.toString(sprint.getId()),
                    sprint.getStart().atStartOfDay(),
                    sprint.getEnd().plusDays(1).atStartOfDay(),
                    true,
                    true,
                    "red",
                    "");
            newEntry.setRenderingMode(Entry.RenderingMode.BACKGROUND);
            AddedCalendar.addEntry(newEntry);
        });
    }

    public void sprintCreating() {
        Text title = new Text("Sprint" + " x");
        DatePicker startTime = new DatePicker();
        startTime.setValue(LocalDate.now());
        DatePicker endTime = new DatePicker();
        endTime.setValue(LocalDate.now());
        getSave().setText("Create");
        getSave().addClickListener(event -> {
            sprint.setId(NitriteDBProvider.getInstance().idFor(sprint.getClass()));
            sprint.setStart(startTime.getValue());
            sprint.setEnd((endTime.getValue()));
            NitriteDBProvider.getInstance().addSprint(sprint);
            Notification notification = new Notification(
                    "Sprint " + sprint.getId() + " is now added",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
            getDialog().close();
        });
        Button reset = new Button("Reset");

        getNewForm().add(title);
        getNewForm().addFormItem(startTime, "Start Time");
        getNewForm().addFormItem(endTime, "End Time");
        getNewForm().add(getSave(), reset);

        getDialog().add(getNewForm());
        getDialog().open();
    }

    public Sprint getNewSprint() {
        return sprint;
    }
}
