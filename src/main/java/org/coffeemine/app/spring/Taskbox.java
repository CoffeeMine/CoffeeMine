package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.*;

public class Taskbox extends View{

    public Taskbox(String taskTitle, String taskDescription){

        Div layout = new Div();
        layout.getStyle().set("border", "1px solid blue");
        layout.getStyle().set("width", "300px");
        layout.getStyle().set("height", "300px");
        layout.getStyle().set("text-overflow", "ellipsis");
        layout.getStyle().set("margin-bottom", "25px");
        setContent(layout);

        Div titleLayout = new Div();
        titleLayout.getStyle().set("width", "250px");
        titleLayout.getStyle().set("height", "70px");
        titleLayout.getStyle().set("text-overflow", "ellipsis");
        titleLayout.getStyle().set("margin-bottom", "25px");
        titleLayout.getStyle().set("margin-left", "20px");

        H2 title = new H2(taskTitle);
        titleLayout.add(title);
        layout.add(titleLayout);

        Div descriptionLayout = new Div();
        descriptionLayout.getStyle().set("width", "250px");
        descriptionLayout.getStyle().set("height", "90px");
        descriptionLayout.getStyle().set("overflow", "hidden");
        descriptionLayout.getStyle().set("text-overflow", "ellipsis");
        descriptionLayout.getStyle().set("margin-left", "20px");
        descriptionLayout.getStyle().set("margin-top", "20px");

        H3 description = new H3(taskDescription);
        descriptionLayout.add(description);
        layout.add(descriptionLayout);

        Div horizontalLayout = new Div();
        horizontalLayout.getStyle().set("margin-left", "20px");
        horizontalLayout.getStyle().set("margin-bottom", "25px");
        layout.add(horizontalLayout);
        H6 assignee = new H6("Assignee:      ");
        horizontalLayout.add(assignee);

        Button details = new Button("Details");
        details.getStyle().set("float", "right");
        details.getStyle().set("margin-right", "20px");
        horizontalLayout.add(details);

    }


}
