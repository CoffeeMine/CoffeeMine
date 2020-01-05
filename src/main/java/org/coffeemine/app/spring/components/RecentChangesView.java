package org.coffeemine.app.spring.components;

import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.coffeemine.app.spring.auth.LoginScreen;
import org.coffeemine.app.spring.components.EventsDialog.TaskDetail;
import org.coffeemine.app.spring.data.Project;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.Overview;

public class RecentChangesView extends VerticalLayout {

    public RecentChangesView(Project project) {
        addClassName("recent-changes");
        add(new H2("Recent Changes:"));

        final var db = NitriteDBProvider.getInstance();

        db.getSprints4Project(project)
                .forEach(sprint -> db.getTasks4Sprint(sprint).sorted(
                        (a, b) -> -Long.compare(db.getTaskModifiedTime(a.getId()), (db.getTaskModifiedTime(b.getId()))))
                        .forEach(task -> {
                            final var date = LocalDateTime
                                    .ofInstant(Instant.ofEpochMilli(db.getTaskModifiedTime(task.getId())),
                                            ZoneId.systemDefault())
                                    .format(DateTimeFormatter.ofPattern("dd MMM. yyyy hh:mm:ss"));
                            final var revision = db.getTaskRevision(task.getId());

                            final var changeText = new Span();
                            changeText.addClassName("link");
                            changeText.getElement().setProperty("innerHTML", "Task \"" + task.getName() + "\":<br/>"
                                    + ((revision == 1) ? "Added" : "Modified") + " at: " + date);

                            changeText.addClickListener(e -> {
                                final var details = new TaskDetail(task.getId(), t -> {
                                    UI.getCurrent().navigate(LoginScreen.class);
                                    UI.getCurrent().navigate(Overview.class);
                                });
                                details.open();
                            });

                            add(changeText);
                        }));

    }
}
