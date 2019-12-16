package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class TaskCreation extends Dialog {
    private Task task;

    public TaskCreation() {
        super();
        task = new Task();
        FormLayout newForm = new FormLayout();

        TextField taskName = new TextField();
        Text taskId = new Text(NitriteDBProvider.getInstance().idFor(task.getClass()).toString());
        TextArea description = new TextArea();
        description.setPlaceholder("Please provide task description here");
        Select<String> assignPeople = new Select<>("Bob", "John", "Rick", "Mahaa", "Tylo");
        assignPeople.setPlaceholder("Assigning to..");
        Select<String> assignSprint = new Select<>("Sprint 1", "Sprint 2", "Sprint 3");
        assignSprint.setPlaceholder("Assigning to sprint..");
        Button create = new Button("Create");
        create.addClickListener(event -> {
            task.setId(Integer.parseInt(taskId.getText()));
            task.setName(taskName.getValue());
            task.setDescription(description.getValue());
            NitriteDBProvider.getInstance().addTask(task);
            Notification notification = new Notification(
                    "Task #" + task.getId() +
                            " " + task.getName() +
                            " is now added",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
            this.close();
        });
        Button reset = new Button("Reset");

        newForm.add("New Task");
        newForm.addFormItem(taskName, "Task Name");
        newForm.addFormItem(taskId, "Task ID");
        newForm.addFormItem(assignPeople, "Task assign");
        newForm.addFormItem(assignSprint, "Sprint assign");
        newForm.addFormItem(description, "Task Description");
        newForm.add(create, reset);

        this.add(newForm);
    }

    public Task getNewTask() { return task; }
}
