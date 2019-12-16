package org.coffeemine.app.spring.components.eventform;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.notification.Notification;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.data.Sprint;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.LocalDate;
import java.util.function.Consumer;

public class SprintCreation extends EventForm {
    private ISprint sprint = new Sprint();

    public SprintCreation(Consumer<ISprint> callback) {
        super();
        getSave().addClickListener(e -> callback.accept(sprint));
        render();
    }

    private void render() {
        final var title = new Text("Sprint" + " x");
        final var start_time = new DatePicker(LocalDate.now());
        final var end_time = new DatePicker(LocalDate.now());
        getSave().setText("Create");
        getSave().addClickListener(event -> {
            sprint.setStart(start_time.getValue());
            sprint.setEnd((end_time.getValue()));
            final var id = NitriteDBProvider.getInstance().addSprint(sprint);
            Notification.show(
                    "Sprint " + id + " is now added",
                    1100,
                    Notification.Position.BOTTOM_CENTER);
            getDialog().close();
        });
        final var form = getNewForm();
        form.add(title);
        form.addFormItem(start_time, "Start Time");
        form.addFormItem(end_time, "End Time");
        final var reset = new Button("Reset");
        form.add(getSave(), reset);

        final var dialog = super.getDialog();
        dialog.add(form);
        dialog.open();
    }
}
