package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class TaskModification extends FormLayout {
    private Task currentTask;
    private Button save;
    private Button delete;

    public TaskModification(int currentTaskId){
        super();
        currentTask = NitriteDBProvider.getInstance().getTask(currentTaskId);

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
        save = new Button("Save");
        save.addClickListener(event -> {
            currentTask.setName(taskName.getValue());
            currentTask.setDescription(description.getValue());
            Notification notification = new Notification(
                    "Task " + currentTask.getName() + " is now saved",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        });
        Button reset = new Button("Reset");
        delete = new Button("Delete");
        delete.addClickListener(event -> {
            NitriteDBProvider.getInstance().removeTask(currentTask.getId());
            Notification notification = new Notification(
                    "Task " + currentTask.getName() + " is now deleted",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
        });

        //Adding the elements for the form
        this.add("Modifying Task #"+currentTask.getId()+" "+currentTask.getName());
        this.addFormItem(taskName, "Task Name");
        this.addFormItem(taskId, "Task ID");
        this.addFormItem(assignSprint, "Sprint assign");
        this.addFormItem(description, "Task Description");
        this.add(save, reset, delete);
    }

    public void closeParent(Dialog parent){
        save.addClickListener(event -> parent.close());
        delete.addClickListener(event -> parent.close());
    }

}
