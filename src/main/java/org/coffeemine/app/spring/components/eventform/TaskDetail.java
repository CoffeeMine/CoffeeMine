package org.coffeemine.app.spring.components.eventform;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class TaskDetail extends EventForm{
    private Task currentTask;

    public TaskDetail(int id){
        currentTask = NitriteDBProvider.getInstance().getTask(id);
    }

    public void taskDetail(){
        Text taskName = new Text(currentTask.getName());
        Text taskId = new Text(Integer.toString(currentTask.getId()));
        Text description = new Text(currentTask.getDescription());
        Text assignSprint = new Text("Sprint X");

        Button edit = new Button("Edit");
        edit.addClickListener(event -> {
            getDialog().close();
            TaskModification taskModification = new TaskModification(currentTask);
            taskModification.taskEditing();
        });

        getNewForm().removeAll();
        getNewForm().add("Task #"+currentTask.getId()+" "+currentTask.getName());
        getNewForm().addFormItem(taskName, "Task Name");
        getNewForm().addFormItem(taskId, "Task ID");
        getNewForm().addFormItem(assignSprint, "Sprint assign");
        getNewForm().addFormItem(description, "Task Description");
        getNewForm().add(edit);

        getDialog().removeAll();
        getDialog().add(getNewForm());
        getDialog().open();
    }
}
