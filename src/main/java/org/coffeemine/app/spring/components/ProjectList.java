package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

import org.coffeemine.app.spring.db.NitriteDBProvider;

public class ProjectList extends VerticalLayout {

    public ProjectList(int limit) {
        generate(limit);
    }

    public ProjectList(int limit, Component...components) {
        this.add(components);
        generate(limit);
    }

    private void generate(int limit) {
        this.setSizeFull();

        final var db = NitriteDBProvider.getInstance();
        final var projects = db.getProjects().limit(limit);

        projects.forEach(project -> {
            final var projectListItem = new HorizontalLayout();
            projectListItem.addClassName("projectitem");
            projectListItem.add(new Text(project.getName()));
            this.add(projectListItem);
        });
    }
}
