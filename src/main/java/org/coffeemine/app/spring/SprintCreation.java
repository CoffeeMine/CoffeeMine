package org.coffeemine.app.spring;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.Sprint;
import org.coffeemine.app.spring.data.Task;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import javax.print.attribute.standard.DialogOwner;
import java.security.PrivateKey;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SprintCreation {
    private Sprint newSprint;
    private Dialog dialog;
    private FormLayout newForm;
    private Button create;

    public SprintCreation() {
        newSprint = new Sprint();
        dialog = new Dialog();
        newForm = new FormLayout();
    }

    public void sprintCreating(FullCalendar AddedCalendar) {
        sprintCreating();
        create.addClickListener(event ->
                AddedCalendar.addEntries(new Entry(
                        Integer.toString(newSprint.getId()),
                        Integer.toString(newSprint.getId()),
                        newSprint.getStart().atStartOfDay(),
                        newSprint.getEnd().atStartOfDay(),
                        true,
                        true,
                        "red",
                        "")));
    }

    public void sprintCreating() {
        FormLayout newForm = new FormLayout();

        Text title = new Text("Sprint" + " x");
        DatePicker startTime = new DatePicker();
        startTime.setValue(LocalDate.now());
        DatePicker endTime = new DatePicker();
        endTime.setValue(LocalDate.now());
        create = new Button("Create");
        Button reset = new Button("Reset");
        create.addClickListener(event -> {
            newSprint.setStart(startTime.getValue());
            newSprint.setEnd((endTime.getValue()));
            Notification.show("Sprint " + "X" + " is now added");
            dialog.close();
        });
        //reset.addClickListener(event -> { });

        newForm.add(title);
        newForm.addFormItem(startTime, "Start Time");
        newForm.addFormItem(endTime, "End Time");
        newForm.add(create, reset);

        dialog.add(newForm);
        dialog.open();
    }

    public Sprint getNewSprint() {
        return newSprint;
    }

    public Dialog getDialog() {
        return dialog;
    }

    public FormLayout getNewForm() {
        return newForm;
    }
}
