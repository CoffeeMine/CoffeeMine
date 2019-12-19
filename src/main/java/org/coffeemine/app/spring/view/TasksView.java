package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

import org.coffeemine.app.spring.components.HorizontalScroll;
import org.coffeemine.app.spring.components.TaskBlock;
import org.coffeemine.app.spring.components.EventsDialog.TaskCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskDetail;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class TasksView extends HorizontalScroll {

    private int localSprint;

    public TasksView(ISprint sprint) {
        localSprint = sprint.getId();
        generate();
    }

    public void generate() {
        removeAll();
        var db = NitriteDBProvider.getInstance();

        db.getTasks4Sprint(db.getSprint(localSprint)).forEach(task -> {
            var assigneeText = "";

            for (var user : task.getAssignees()) {
                assigneeText += db.getUser(user).getAccountName();
            }

            add(new TaskBlock(task.getName(), task.getDescription(), assigneeText, new Button("details", e -> {
                final var details = new TaskDetail(task.getId(), t -> generate());
                details.open();
            })));
        });
        if (db.getTasks4Sprint(db.getSprint(localSprint)).count() == 0) {
            add(new TaskBlock("Create a new task.", "Try adding a new task!", "you", new Button("Create", e -> {
                final var create = new TaskCreation(t -> generate());
                create.open();
            })));
        }
    }
}
