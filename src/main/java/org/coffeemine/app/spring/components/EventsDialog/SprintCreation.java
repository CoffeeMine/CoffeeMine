package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import org.coffeemine.app.spring.data.Sprint;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.LocalDate;

public class SprintCreation extends Dialog {
    private Sprint sprint;

    public SprintCreation() {
        super();
        sprint = new Sprint();
        FormLayout newForm = new FormLayout();

        Text title = new Text("Sprint" + " x");

        DatePicker startTime = new DatePicker();
        startTime.setValue(LocalDate.now());

        DatePicker endTime = new DatePicker();
        endTime.setValue(LocalDate.now());

        Button create = new Button("Create");
        create.addClickListener(event -> {
            sprint.setId(NitriteDBProvider.getInstance().idFor(sprint.getClass()));
            sprint.setStart(startTime.getValue());
            sprint.setEnd((endTime.getValue()));
            NitriteDBProvider.getInstance().addSprint(sprint);
            Notification notification = new Notification(
                    "Sprint " + sprint.getId() + " is now added",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
            this.close();
        });
        Button reset = new Button("Reset");

        newForm.add(title);
        newForm.addFormItem(startTime, "Start Time");
        newForm.addFormItem(endTime, "End Time");
        newForm.add(create, reset);

        this.add(newForm);
    }

    public Sprint getNewSprint() {
        return sprint;
    }
}
