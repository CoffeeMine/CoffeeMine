package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.coffeemine.app.spring.data.ChangeTracker;
import org.coffeemine.app.spring.data.Project;
import org.coffeemine.app.spring.db.NitriteDBProvider;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.stream.Stream;

public class RecentChangesView extends VerticalLayout {

    Stream<ChangeTracker> total = Stream.empty();

    public RecentChangesView(Project project) {
        addClassName("recent-changes");
        add(new H2("Recent Changes:"));

        final var db = NitriteDBProvider.getInstance();

        total = Stream.concat(total, db.getSprints4Project(project));
        total = Stream.concat(total, db.getSprints4Project(project).flatMap(db::getTasks4Sprint));
        total = Stream.concat(total, db.getSprints4Project(project).flatMap(s -> db.getTasks4Sprint(s).flatMap(db::getFragments4Task)));

        total.sorted((a, b) -> -Long.compare(a.getLastModifiedTime(), b.getLastModifiedTime()))
                .forEach(change -> {
                    final var date = LocalDateTime.ofInstant(Instant.ofEpochMilli(change.getLastModifiedTime()), ZoneId.systemDefault());

                    final var changeText = new Span();
                    changeText.getElement().setProperty("innerHTML", change.getType() + " \"" + change.getMessage() + "\":<br/>"
                            + ((change.getRevision() == 1) ? "Added" : "Modified") + " at: " + date.format(DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss")));
                    add(changeText);
                });
    }
}
