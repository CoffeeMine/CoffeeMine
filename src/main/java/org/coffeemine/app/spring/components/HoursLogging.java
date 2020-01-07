package org.coffeemine.app.spring.components;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import org.coffeemine.app.spring.data.Fragment;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.data.*;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import java.time.LocalDate;
import java.util.stream.Collectors;

public class HoursLogging extends Dialog {

    private ITask task = null;
    public HoursLogging() {

        // TODO: show Sprint selector
        // TODO: show Task selector (only show tasks for selected sprint)
        final var db = NitriteDBProvider.getInstance();
        final var start_time = new DatePicker(LocalDate.now());
        final var end_time = new DatePicker(LocalDate.now());

        final var log = new Button("Apply");
        log.addClickListener(event -> open());
        log.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        final var create = new Button("Create", e -> {
            final var fragment = new Fragment();
            fragment.setBegin(start_time.getValue());
            fragment.setEnd(end_time.getValue());
            // TODO: save selected task
            final var id = db.addFragment(fragment);
            task.addFragment(id);
            Notification.show(
                    "Task is now added!",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            close();
        });


        create.setEnabled(false);
        final var selectSprint = new Select<>();
        final var selectTask = new Select<>();
        if (CurrentUser.get() != null) {
            final var currentProject = db.getProject(CurrentUser.get().getCurrentProject());
            selectSprint.setItemLabelGenerator(e ->
                    Integer.toString(((ISprint) e).getId()));
            selectSprint.setItems(db.getSprints4Project(currentProject).collect(Collectors.toList()));
            selectSprint.addValueChangeListener(event -> {
                selectTask.removeAll();
                selectTask.setItemLabelGenerator(e -> ((ITask) e).getName());
                selectTask.setItems(db.getTasks4Sprint(((ISprint) event.getValue())).collect(Collectors.toList()));
            });
            selectTask.addValueChangeListener(event -> {
                task = (ITask) event.getValue();
                create.setEnabled(true);
            });
            final var form = new FormLayout();
            form.addFormItem(selectSprint, "Choose a sprint:");
            form.addFormItem(selectTask, "Choose a task:");
            form.addFormItem(start_time, "Start time: ");
            form.addFormItem(end_time, "End time: ");
            final var cancel = new Button("Cancel", e -> close());
            form.add(create, cancel);
            add(form);
            open();
        }
    }
}
