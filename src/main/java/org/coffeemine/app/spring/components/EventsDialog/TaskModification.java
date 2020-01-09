package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.components.SpaceableTextField;
import org.coffeemine.app.spring.components.SpaceableTextArea;

import java.util.function.Consumer;


public class TaskModification extends Dialog {

    public TaskModification(int task_id, Consumer<ITask> callback) {
        super();
        final var task = NitriteDBProvider.getInstance().getTask(task_id);

        final var name = new SpaceableTextField();
        name.setValue(task.getName());
        final var desc = new SpaceableTextArea();
        desc.setPlaceholder("Please provide a task description here");
        desc.setValue(task.getDescription());
        final var sprint_sel = new Select<>("Sprint 1", "Sprint 2", "Sprint 3");
        sprint_sel.setPlaceholder("Assigning to sprint..");
        sprint_sel.setValue("   ");

        final var save = new Button("Save", e -> {
            task.setName(name.getValue());
            task.setDescription(desc.getValue());
            NitriteDBProvider.getInstance().updateTask(task);
            callback.accept(task);
            Notification.show(
                    "Saved task " + task.getName(),
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            close();
        });

        final var reset = new Button("Reset");

        final var delete = new Button("Delete", e -> {
            NitriteDBProvider.getInstance().removeTask(task.getId());
            callback.accept(null);
            Notification.show(
                    "Deleted task " + task.getName(),
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            close();
        });


        final var form = new FormLayout();
        form.add("Modifying Task #" + task.getId() + " " + task.getName());
        form.addFormItem(name, "Task Name");
        form.addFormItem(sprint_sel, "For");
        form.addFormItem(desc, "Task Description");
        form.add(save, reset, delete);

        add(form);
        open();
    }

}
