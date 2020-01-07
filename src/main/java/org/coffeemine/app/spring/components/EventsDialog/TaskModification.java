package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.util.ArrayList;
import java.util.function.Consumer;


public class TaskModification extends Dialog {

    public TaskModification(int task_id, Consumer<ITask> callback) {
        super();
        final var task = NitriteDBProvider.getInstance().getTask(task_id);

        final var name = new TextField();
        name.setValue(task.getName());
        final var desc = new TextArea();
        desc.setPlaceholder("Please provide a task description here");
        desc.setValue(task.getDescription());

        String[] sprints = new String[(int) NitriteDBProvider.getInstance().getSprints4Project(NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get())).count()];
        for(int i=1; i<= sprints.length; i++){
            sprints[i-1]= "Sprint " + i;
        }
        final var sprint_sel = new Select<>(sprints);
        sprint_sel.setPlaceholder("Assigning to sprint..");
        sprint_sel.setValue(task.getAssignSprint());

        final var save = new Button("Save", e -> {
            task.setName(name.getValue());
            task.setAssignSprint(sprint_sel.getValue());
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
