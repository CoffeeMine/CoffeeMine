package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.Task;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.LocalDateTime;

public class TaskCreation {
    private Task newTask;
    private Dialog dialog;
    private FormLayout newForm;
    private Button create;

    public TaskCreation() {
        newTask = new Task();
        dialog = new Dialog();
        newForm = new FormLayout();
    }

    public void taskCreating(FullCalendar AddedCalendar) {
        taskCreating();
        create.addClickListener(event -> AddedCalendar.addEntries(new Entry(
                "0101",
                newTask.getName(),
                LocalDateTime.now(),
                LocalDateTime.of(2019, 11, 28, 12, 00),
                true,
                true,
                " Deep blue",
                newTask.getDescription())));
    }

    public void taskCreating() {
        TextField taskName = new TextField();
        NumberField taskId = new NumberField();
        TextArea description = new TextArea();
        description.setPlaceholder("Please provide task description here");
        Select<String> assignPeople = new Select<>("Bob", "John", "Rick", "Mahaa", "Tylo");
        assignPeople.setPlaceholder("Assigning to..");
        Select<String> assignSprint = new Select<>("Sprint 1", "Sprint 2", "Sprint 3");
        assignSprint.setPlaceholder("Assigning to sprint..");
        create = new Button("Create");
        Button reset = new Button("Reset");
        create.addClickListener(event -> {
            newTask.setName(taskName.getValue());
            newTask.setDescription(description.getValue());
            Notification.show("Task " + newTask.getName() + " is now added");
            dialog.close();
        });
        //reset.addClickListener(event -> { });

        newForm.addFormItem(taskName, "Task Name");
        newForm.addFormItem(taskId, "Task ID");
        newForm.addFormItem(assignPeople, "Task assign");
        newForm.addFormItem(assignSprint, "Sprint assign");
        newForm.addFormItem(description, "Task Description");
        newForm.add(create, reset);

        dialog.add(newForm);
        dialog.open();
    }

    public Task getNewTask() { return newTask; }

    public Dialog getDialog() { return dialog; }

    public FormLayout getNewForm() { return newForm; }
}
