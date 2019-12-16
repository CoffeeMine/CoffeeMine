package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.notification.Notification;

import org.coffeemine.app.spring.components.HorizontalScroll;
import org.coffeemine.app.spring.components.TaskBlock;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class TasksView extends HorizontalScroll {

    public TasksView(ISprint sprint) {
        var db = NitriteDBProvider.getInstance();

        db.getTasks4Sprint(sprint).forEach(task -> {
            var assigneeText = "";

            for (var user : task.getAssignees()) {
                assigneeText += db.getUser(user).getAccountName();
            }

            add(new TaskBlock(task.getName(), task.getDescription(), assigneeText, new Button("details", e -> {
                Notification.show("Not Implemented Yet ;)");
            })));
        });
        if (db.getTasks4Sprint(sprint).count() == 0) {
            add(new TaskBlock("Create a new task.", "Try adding a new task!", "you",
                    new Button("Create", e -> Notification.show("Not Implemented Yet ;)"))));
        }
    }
}
