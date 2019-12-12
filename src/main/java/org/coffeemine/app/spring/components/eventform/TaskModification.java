package org.coffeemine.app.spring.components.eventform;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.Task;

public class TaskModification extends EventForm{
    private Task currentTask;

    public TaskModification(String idOfCurrentTask){
        super();
        currentTask = new Task();
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
            Notification sprintNotification = new Notification(
                    "Task " + currentTask.getName() + " is now saved",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            sprintNotification.open();
            getDialog().close();
        });
        Button reset = new Button("Reset");

        //Adding the elements for the form
        getNewForm().addFormItem(taskName, "Task Name");
        getNewForm().addFormItem(taskId, "Task ID");
        getNewForm().addFormItem(assignSprint, "Sprint assign");
        getNewForm().addFormItem(description, "Task Description");
        getNewForm().add(getSave(), reset);

        getDialog().add(getNewForm());
        getDialog().open();
    }
}
