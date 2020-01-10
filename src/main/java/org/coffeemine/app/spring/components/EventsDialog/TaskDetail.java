package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;

public class TaskDetail extends Dialog {
    private ITask task;

    public TaskDetail(int id, Consumer<ITask> on_edit) {
        task = NitriteDBProvider.getInstance().getTask(id);

        final var name = new Text(task.getName());
        final var id_txt = new Text(Integer.toString(task.getId()));
        final var description = new Text(task.getDescription());
        final var estimate_work_hours = new Text(Integer.toString(task.getHours()));
        final var bound_assignees = new Text(String.join(", ", task.getAssignees().stream().map(t -> NitriteDBProvider.getInstance().getUser(t).getName()).toArray(String[]::new)));
        final var bound_sprint = new Text(NitriteDBProvider.getInstance().getSprints()
                .filter(s -> s.getTasks().contains(task.getId())).findFirst().map(ISprint::getStart)
                .map(start -> "Sprint " + start.format(DateTimeFormatter.ofPattern("uuuu MMM dd")))
                .orElse("N/A"));

        final var edit = new Button("Edit", e -> {
            new TaskModification(task.getId(), on_edit);
            close();
        });

        final var form = new FormLayout();
        form.add("Task #" + task.getId() + " " + task.getName());
        form.addFormItem(name, "Task Name");
        form.addFormItem(id_txt, "Task ID");
        form.addFormItem(estimate_work_hours, "Estimate hours");
        form.addFormItem(bound_assignees, "Assigning to");
        form.addFormItem(bound_sprint, "Sprint assign");
        form.addFormItem(description, "Task Description");
        form.add(edit);

        add(form);
        open();
    }
}
