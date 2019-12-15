package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class TaskBlock extends VerticalLayout {

    public TaskBlock(String title, String description, String assignees, Button button) {
        addClassName("taskBlock");
        addClassName("mono_font");

        var taskTitle = new H2(title);
        taskTitle.getStyle().set("padding-top", "20px");

        var taskDescription = new Text(description);

        var footer = new HorizontalLayout();
        footer.setWidthFull();
        footer.setHeight("44px");
        footer.getStyle().set("margin-top", "auto");

        var assigneesTotal = new Span("Assignee(s): " + assignees);
        assigneesTotal.getStyle().set("max-width", "250px");
        assigneesTotal.getStyle().set("margin-bottom", "auto");

        var detailButton = button;
        detailButton.getStyle().set("margin-left", "auto");
        detailButton.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        footer.add(assigneesTotal, detailButton);

        add(taskTitle, taskDescription, footer);
    }
}
