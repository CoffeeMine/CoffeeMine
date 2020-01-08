package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;

import org.coffeemine.app.spring.auth.LoginScreen;
import org.coffeemine.app.spring.components.HorizontalScroll;
import org.coffeemine.app.spring.components.TaskBlock;
import org.coffeemine.app.spring.components.EventsDialog.SprintCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskCreation;
import org.coffeemine.app.spring.components.EventsDialog.TaskDetail;
import org.coffeemine.app.spring.data.ISprint;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class TasksView extends HorizontalScroll {

    public TasksView(ISprint sprint) {
        if (sprint != null) {
            var db = NitriteDBProvider.getInstance();

            db.getTasks4Sprint(db.getSprint(sprint.getId())).forEach(task -> {
                var assigneeText = "";
    
                for (var user : task.getAssignees()) {
                    assigneeText += db.getUser(user).getName();
                }
    
                add(new TaskBlock(task.getName(), task.getDescription(), assigneeText, new Button("details", e -> {
                    final var details = new TaskDetail(task.getId(), t -> {
                        UI.getCurrent().navigate(LoginScreen.class);
                        UI.getCurrent().navigate(Overview.class);
                    });
                    details.open();
                })));
            });
            if (db.getTasks4Sprint(db.getSprint(sprint.getId())).count() == 0) {
                add(new TaskBlock("Create a new task.", "Try adding a new task!", "you", new Button("Create", e -> {
                    final var create = new TaskCreation(t -> {
                        UI.getCurrent().navigate(LoginScreen.class);
                        UI.getCurrent().navigate(Overview.class);
                    });
                    create.open();
                })));
            }
        } else {
            add(new TaskBlock("Create a new sprint.", "Try adding a new sprint!", "you", new Button("Create", e -> {
                final var create = new SprintCreation(t -> {
                    UI.getCurrent().navigate(LoginScreen.class);
                    UI.getCurrent().navigate(Overview.class);
                });
                create.open();
            })));
        }
    }
}
