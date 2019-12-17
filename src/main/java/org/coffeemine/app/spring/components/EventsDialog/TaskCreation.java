package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.util.function.Consumer;

public class TaskCreation extends Dialog {
    private ITask task = new Task();

    public TaskCreation(Consumer<ITask> callback) {
        super();

        final var name = new TextField();
        final var desc = new TextArea();
        desc.setPlaceholder("Please provide task description here");
        final var assignees_sel = new Select<>("Bob", "John", "Rick", "Mahaa", "Tylo");
        assignees_sel.setPlaceholder("Assigning to..");
        final var sprint_sel = new Select<>("Sprint 1", "Sprint 2", "Sprint 3");
        sprint_sel.setPlaceholder("Assigning to sprint..");
        final var create_btn = new Button("Create", event -> {
            task.setName(name.getValue());
            task.setDescription(desc.getValue());
            NitriteDBProvider.getInstance().addTask(task);
            callback.accept(task);
            Notification notification = new Notification(
                    "Added task #" + task.getId() +
                            " " + task.getName(),
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
            close();
        });
        Button reset = new Button("Reset");

        final var form = new FormLayout();
        form.add("New Task");
        form.addFormItem(name, "Task Name");
        form.addFormItem(assignees_sel, "Task assign");
        form.addFormItem(sprint_sel, "Sprint assign");
        form.addFormItem(desc, "Task Description");
        form.add(create_btn, reset);

        add(form);
        open();
    }
}
