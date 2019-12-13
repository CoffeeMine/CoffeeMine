package org.coffeemine.app.spring.components.eventform;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.Task;
import org.vaadin.stefan.fullcalendar.Entry;
import org.vaadin.stefan.fullcalendar.FullCalendar;

import java.time.LocalDateTime;

public class TaskCreation extends EventForm{
    private Task task;
    private static int TASK_COUNTER = 0;

    public TaskCreation() {
        super();
        task = new Task();
    }

    public void taskCreating(FullCalendar AddedCalendar) {
        taskCreating();
        getSave().addClickListener(event -> {
            Entry newEntry = new Entry(
                    Integer.toString(task.getId()),
                    task.getName(),
                    LocalDateTime.now(),
                    LocalDateTime.of(2019, 11, 28, 12, 00),
                    true,
                    true,
                    " dodgerblue",
                    task.getDescription());
            AddedCalendar.addEntry(newEntry);
            TASK_COUNTER++;
        });
    }

    public void taskCreating() {
        TextField taskName = new TextField();
        Text taskId = new Text(SprintCreation.getSprintCounter() + TaskCreation.getTaskCounter());
        TextArea description = new TextArea();
        description.setPlaceholder("Please provide task description here");
        Select<String> assignPeople = new Select<>("Bob", "John", "Rick", "Mahaa", "Tylo");
        assignPeople.setPlaceholder("Assigning to..");
        Select<String> assignSprint = new Select<>("Sprint 1", "Sprint 2", "Sprint 3");
        assignSprint.setPlaceholder("Assigning to sprint..");
        getSave().setText("Create");
        getSave().addClickListener(event -> {
            task.setId(Integer.parseInt(SprintCreation.getSprintCounter() + TaskCreation.getTaskCounter()));
            task.setName(taskName.getValue());
            task.setDescription(description.getValue());
            Notification sprintNotification = new Notification("Task " + task.getName() + " is now added",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            sprintNotification.open();
            getDialog().close();
        });
        Button reset = new Button("Reset");

        getNewForm().add("New Task");
        getNewForm().addFormItem(taskName, "Task Name");
        getNewForm().addFormItem(taskId, "Task ID");
        getNewForm().addFormItem(assignPeople, "Task assign");
        getNewForm().addFormItem(assignSprint, "Sprint assign");
        getNewForm().addFormItem(description, "Task Description");
        getNewForm().add(getSave(), reset);

        getDialog().add(getNewForm());
        getDialog().open();
    }

    public Task getNewTask() { return task; }

    public void setNewTask(Task newTask) {
        this.task = newTask;
    }

    public static String getTaskCounter(){
        return Integer.toString(TASK_COUNTER);
    }
}
