package org.coffeemine.app.spring.components;

import java.time.Instant;
import java.time.ZoneId;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.coffeemine.app.spring.data.ChangeTracker;
import org.coffeemine.app.spring.data.Project;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class RecentChangesView extends VerticalLayout {

    Stream<ChangeTracker> total = Stream.empty();

    public RecentChangesView(Project project) {
        addClassName("recent-changes");
        add(new H2("Recent Changes:"));

        final var db = NitriteDBProvider.getInstance();

        total = Stream.concat(total, db.getSprints4Project(project).map(sprint -> (ChangeTracker) sprint));

        db.getSprints4Project(project).forEach(sprint -> {
            total = Stream.concat(total, db.getTasks4Sprint(sprint).map(task -> (ChangeTracker) task));
        });

        total.sorted((a, b) -> -Long.compare(a.getLastModifiedTime(), b.getLastModifiedTime()))
        .forEach(change -> {
            final var date = LocalDateTime.ofInstant(Instant.ofEpochMilli(change.getLastModifiedTime()), ZoneId.systemDefault());

            final var changeText = new Span();
            changeText.getElement().setProperty("innerHTML", change.getType() + " \"" + change.getMessage() + "\":<br/>"
                    + ((change.getRevision() == 1) ? "Added" : "Modified") + " at: " + date.format(DateTimeFormatter.ofPattern("dd MMM. yyyy hh:mm:ss")));
            add(changeText);
        });
    }
}
