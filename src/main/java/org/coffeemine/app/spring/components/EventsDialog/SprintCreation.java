package org.coffeemine.app.spring.components.EventsDialog;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.data.Sprint;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.LocalDate;
import java.util.function.Consumer;

public class SprintCreation extends Dialog {
    private ISprint sprint = new Sprint();

    public SprintCreation(Consumer<ISprint> callback) {
        super();

        final var title = new Text(
                "Sprint " + ((int) NitriteDBProvider.getInstance().getSprints4Project(
                                NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get())).count()+1));
        final var start_time = new DatePicker(LocalDate.now());
        final var end_time = new DatePicker(LocalDate.now());
        final var create = new Button("Create", event -> {
            sprint.setStart(start_time.getValue());
            sprint.setEnd((end_time.getValue()));
            final var id = NitriteDBProvider.getInstance().addSprint(sprint);
            NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get()).addSprint(id);
            callback.accept(sprint);
            Notification.show(
                    "Sprint " + id + " is now added",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            close();
        });
        final var form = new FormLayout(title);
        form.add(title);
        form.addFormItem(start_time, "Start Time");
        form.addFormItem(end_time, "End Time");
        final var reset = new Button("Reset");
        form.add(create, reset);

        add(form);
        open();
    }
}
