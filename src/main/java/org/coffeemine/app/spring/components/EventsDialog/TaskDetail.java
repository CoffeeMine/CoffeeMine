package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class TaskDetail extends Dialog {
    private Task currentTask;

    public TaskDetail(int id){
        currentTask = NitriteDBProvider.getInstance().getTask(id);
        FormLayout newForm = new FormLayout();

        Text taskName = new Text(currentTask.getName());
        Text taskId = new Text(Integer.toString(currentTask.getId()));
        Text description = new Text(currentTask.getDescription());
        Text assignSprint = new Text("Sprint X");

        Button edit = new Button("Edit");
        edit.addClickListener(event -> {
            TaskModification taskModification = new TaskModification(currentTask.getId());
            taskModification.closeParent(this);
            this.removeAll();
            this.add(taskModification);
        });

        newForm.add("Task #"+currentTask.getId()+" "+currentTask.getName());
        newForm.addFormItem(taskName, "Task Name");
        newForm.addFormItem(taskId, "Task ID");
        newForm.addFormItem(assignSprint, "Sprint assign");
        newForm.addFormItem(description, "Task Description");
        newForm.add(edit);

        this.add(newForm);
    }
}
