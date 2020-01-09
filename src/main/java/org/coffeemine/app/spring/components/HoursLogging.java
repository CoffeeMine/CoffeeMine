package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;

import org.coffeemine.app.spring.data.Fragment;

import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.NumberField;

import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.data.*;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.Collectors;

public class HoursLogging extends Dialog {

    private ITask task = null;

    public HoursLogging() {

        final var db = NitriteDBProvider.getInstance();
        final var workedTime = new NumberField();
        workedTime.setValue(0d);
        workedTime.setHasControls(true);
        workedTime.setStep(1);
        workedTime.setMin(0);

        final var log = new Button("Apply");
        log.addClickListener(event -> open());
        log.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);

        final var create = new Button("Create", e -> {
            if (workedTime.getOptionalValue().isPresent()) {
                final var end_time = LocalDateTime.now();
                final var start_time = end_time
                        .minusHours(Double.valueOf(workedTime.getOptionalValue().get()).longValue());

                final var fragment = new Fragment(0, start_time, end_time,
                        new ArrayList<>(Arrays.asList(CurrentUser.get().getId())));
                task.addFragment(db.addFragment(fragment));
                Notification.show("Successfully logged", 1100, Notification.Position.BOTTOM_END);
                close();
            }
        });

        create.setEnabled(false);
        final var selectSprint = new Select<>();
        final var selectTask = new Select<>();
        if (CurrentUser.get() != null) {
            final var currentProject = db.getProject(CurrentUser.get().getCurrentProject());
            selectSprint.setItemLabelGenerator(
                    e -> ((ISprint) e).getStart().format(DateTimeFormatter.ofPattern("uuuu MMM dd")));
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
            form.setResponsiveSteps(new ResponsiveStep("0px", 1, LabelsPosition.ASIDE));
            form.getStyle().set("max-width", "fit-content");
            
            form.addFormItem(selectSprint, "Choose a sprint:");
            form.addFormItem(selectTask, "Choose a task:");
            form.addFormItem(workedTime, "Worked hours: ");
            final var cancel = new Button("Cancel", e -> close());
            form.add(create, cancel);
            add(form);
            open();
        }
    }
}
