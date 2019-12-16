package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.util.function.Consumer;


public class TaskModification extends Dialog {

    public TaskModification(int task_id, Consumer<ITask> callback) {
        super();
        delete = new Button("Delete");
        this.currentTask = currentTask;
    }

    public void taskEditing(FullCalendar AddedCalendar){
        taskEditing();
        delete.addClickListener(event -> {
            AddedCalendar.removeEntry(currentEntry);
            NitriteDBProvider.getInstance().removeTask(currentTask.getId());
            Notification notification = new Notification(
                    "Task " + currentTask.getName() + " is now deleted",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
            getDialog().close();
        });
    }

    public void taskEditing(){
        //Elements for the form
        TextField taskName = new TextField();
        taskName.setValue(currentTask.getName());
        NumberField taskId = new NumberField();
        taskId.setValue((double) currentTask.getId());
        TextArea description = new TextArea();
        description.setPlaceholder("Please provide task description here");
        description.setValue(currentTask.getDescription());
        Select<String> assignSprint = new Select<>("Sprint 1", "Sprint 2", "Sprint 3");
        assignSprint.setPlaceholder("Assigning to sprint..");
        assignSprint.setValue("   ");
        getSave().setText("Save");
        getSave().addClickListener(event -> {
            currentTask.setName(taskName.getValue());
            currentTask.setDescription(description.getValue());
            Notification notification = new Notification(
                    "Task " + currentTask.getName() + " is now saved",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
            getDialog().close();
        });
        Button reset = new Button("Reset");

        final var form = new FormLayout();
        form.add("Modifying Task #" + task.getId() + " " + task.getName());
        form.addFormItem(name, "Task Name");
        form.addFormItem(sprint_sel, "For");
        form.addFormItem(desc, "Task Description");
        form.add(save, reset, delete);

        add(form);
    }

}
