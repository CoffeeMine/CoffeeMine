package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H2;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;
import org.coffeemine.app.spring.data.Project;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class AddProjectDialog extends Dialog {

    public AddProjectDialog() {
        VerticalLayout layout = new VerticalLayout();
        this.add(layout);

        final var columnLayout = new VerticalLayout();
        columnLayout.getStyle().set("padding", "0px");

        final var addCleanButton = new Button("Create");
        addCleanButton.getStyle().set("margin", "16px 0px 0px auto");
        addCleanButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        addCleanButton.setEnabled(false);

        final var projectName = new TextField("Project name");
        projectName.setWidthFull();
        projectName.getStyle().set("margin", "0px");
        projectName.setRequired(true);
        projectName.setValueChangeMode(ValueChangeMode.EAGER);

        projectName.addValueChangeListener(text -> {
            if(text.getValue().length() > 0) {
                addCleanButton.setEnabled(true);
            } else {
                addCleanButton.setEnabled(false);
            }
        });

        final var projectDescription = new TextField("Description");
        projectDescription.setWidthFull();
        projectDescription.getStyle().set("margin", "0px");

        addCleanButton.addClickListener(c -> {
            try {
                final var newProjectName = projectName.getOptionalValue().orElseThrow();
                NitriteDBProvider.getInstance().addProject(new Project(newProjectName, 0));
                Notification.show("Project \"" + newProjectName+ "\" created", 2000, Notification.Position.BOTTOM_END);
                close();
            } catch (Exception e) {
            }
        });

        columnLayout.add(new Span("Create a fresh project:"), projectName, projectDescription, addCleanButton);

        final var addButton = new Button("Import");
        addButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        addButton.getStyle().set("margin", "0px 0px 0px auto");

        final var upload = new JSONUploadSection(addButton, () -> {
            close();
            UI.getCurrent().getPage().reload();
        });
        layout.add(new H2("New Project"), columnLayout, new Text("Import a project:"), upload, addButton);
    }
}
