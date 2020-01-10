package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.components.SpaceableTextArea;
import org.coffeemine.app.spring.components.SpaceableTextField;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TaskModification extends Dialog {

    public TaskModification(int task_id, Consumer<ITask> callback) {
        super();
        final var task = NitriteDBProvider.getInstance().getTask(task_id);

        final var name = new SpaceableTextField();
        name.setValue(task.getName());
        final var desc = new SpaceableTextArea();
        desc.setPlaceholder("Please provide a task description here");
        desc.setValue(task.getDescription());

        final var assignees_sel = new /*Multiselect*/ComboBox<User>();
        assignees_sel.setItems(NitriteDBProvider.getInstance().getUsers().collect(Collectors.toList()));
        assignees_sel.setPlaceholder("Assigning to..");
        Set<User> currentAssignees = new HashSet<>();
        task.getAssignees().forEach(assigneesId -> currentAssignees.add(NitriteDBProvider.getInstance().getUser(assigneesId)));
        if (!currentAssignees.isEmpty())
            assignees_sel.setValue(currentAssignees.stream().findFirst().get());

        final var sprint_sel = new Select<>(NitriteDBProvider.getInstance().getSprints4Project(NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get())).collect(Collectors.toList()).toArray(ISprint[]::new));
        sprint_sel.setPlaceholder("Assigning to sprint..");
        sprint_sel.setItemLabelGenerator(s -> s == null ? "N/A" : s.getStart().format(DateTimeFormatter.ofPattern("uuuu MMM dd")));
        sprint_sel.setValue(sprint4task(task));

        final var save = new Button("Save", e -> {
            task.setName(name.getValue());
            final var old_sprint = sprint4task(task);
            old_sprint.getTasks().remove(task_id);
            NitriteDBProvider.getInstance().updateSprint(old_sprint);

            final var new_sprint = sprint_sel.getValue();
            new_sprint.getTasks().add(task_id);
            NitriteDBProvider.getInstance().updateSprint(new_sprint);


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
        form.addFormItem(assignees_sel, "Assigning to");
        form.addFormItem(sprint_sel, "For");
        form.addFormItem(desc, "Task Description");
        form.add(save, reset, delete);

        add(form);
        open();
    }

    private static ISprint sprint4task(ITask task) {
        return NitriteDBProvider.getInstance().getSprints()
                .filter(s -> s.getTasks().contains(task.getId())).findFirst().orElse(null);
    }

}
