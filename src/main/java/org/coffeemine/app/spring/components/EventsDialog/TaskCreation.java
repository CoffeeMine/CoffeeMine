package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.textfield.TextArea;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.components.SpaceableTextField;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.data.ITask;
import org.coffeemine.app.spring.data.Task;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.function.Consumer;
import java.util.stream.Collectors;

public class TaskCreation extends Dialog {
    private ITask task;

    public TaskCreation(Consumer<ITask> callback) {
        super();

        final var name = new SpaceableTextField();
        final var desc = new TextArea();
        desc.setPlaceholder("Please provide task description here");

        final var workTime = new NumberField();
        workTime.setValue(0d);
        workTime.setHasControls(true);
        workTime.setStep(1);
        workTime.setMin(0);

        final var assignees_sel = new /*Multiselect*/ComboBox<User>();
        assignees_sel.setItems(NitriteDBProvider.getInstance().getUsers().collect(Collectors.toList()));
        assignees_sel.setPlaceholder("Assigning to..");

        final var sprint_sel = new Select<ISprint>();
        sprint_sel.setItems(NitriteDBProvider.getInstance().getSprints4Project(NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get())).collect(Collectors.toList()));
        sprint_sel.setItemLabelGenerator(a -> a.getStart().format(DateTimeFormatter.ofPattern("uuuu MMM dd")));
        sprint_sel.setPlaceholder("Assigning to sprint..");

        final var create_btn = new Button("Create", event -> {
            ArrayList<Integer> usersId = new ArrayList<>();
            //assignees_sel.getSelectedItem().forEach(user -> usersId.add(user.getId()));
            usersId.add(assignees_sel.getValue().getId());
            final var task = new Task(-1, name.getValue(), desc.getValue(), true, workTime.getValue().intValue(), usersId, new ArrayList<>(), new ArrayList<>());
            sprint_sel.getValue().getTasks().add(NitriteDBProvider.getInstance().addTask(task));
            callback.accept(task);
            Notification notification = new Notification(
                    "Added task #" + task.getId() +
                            " " + task.getName(),
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            notification.open();
            close();
        });
        Button reset = new Button("Reset");

        final var form = new FormLayout();
        form.add("New Task");
        form.addFormItem(name, "Task Name");
        form.addFormItem(workTime, "Estimate hours");
        form.addFormItem(assignees_sel, "Task assign");
        form.addFormItem(sprint_sel, "Sprint assign");
        form.addFormItem(desc, "Task Description");
        form.add(create_btn, reset);

        add(form);
        open();
    }
}
