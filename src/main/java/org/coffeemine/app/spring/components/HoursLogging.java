package org.coffeemine.app.spring.components;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import org.coffeemine.app.spring.data.Fragment;

import java.time.LocalDate;

public class HoursLogging extends Dialog {

    public HoursLogging() {

        // TODO: show Sprint selector
        // TODO: show Task selector (only show tasks for selected sprint)
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
            close();
        });


        final var form = new FormLayout();
        form.addFormItem(start_time, "Start time: ");
        form.addFormItem(end_time, "End time: ");
        final var cancel = new Button("Cancel", e -> close());
        form.add(create, cancel);
        add(form);
        open();
    }
}
