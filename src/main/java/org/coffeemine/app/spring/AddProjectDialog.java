package org.coffeemine.app.spring;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.components.JSONUploadSection.JSONUploadSection;

class AddProjectDialog extends Dialog {

    public AddProjectDialog() {
        VerticalLayout layout = new VerticalLayout();
        this.add(layout);

        FormLayout columnLayout = new FormLayout();
        columnLayout.getStyle().set("padding", "15px");
        TextField projectName = new TextField();
        projectName.setPlaceholder("Project name");

        TextField projectDescription = new TextField();
        projectDescription.setPlaceholder("Description");
        columnLayout.add(projectName, projectDescription);

        final var addButton = new Button("Add");
        addButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        addButton.getStyle().set("margin-left", "auto");

        final var upload = new JSONUploadSection(addButton);
        layout.add(new H1("New Project"), columnLayout, new Text("Project data:"), upload, addButton);
    }
}
