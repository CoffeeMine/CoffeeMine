package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.db.NitriteDBProvider;

public class ProjectList extends VerticalLayout {

    public static enum Modes {
        SMALL,
        LARGE
    }

    public ProjectList(Modes mode) {
        generate(mode, (int) NitriteDBProvider.getInstance().getProjects().count());
    }

    public ProjectList(Modes mode, Component... components) {
        this.add(components);
        generate(mode, (int) NitriteDBProvider.getInstance().getProjects().count());
    }

    public ProjectList(Modes mode, int limit) {
        generate(mode, limit);
    }

    public ProjectList(Modes mode, int limit, Component... components) {
        this.add(components);
        generate(mode, limit);
    }

    private void generate(Modes mode, int limit) {
        this.setSizeFull();
        this.addClassNames("mono_font", "projectlist");

        final var projects = NitriteDBProvider.getInstance().getProjects().limit(limit);

        projects.forEach(project -> {
            final var projectListItem = new HorizontalLayout();
            projectListItem.addClassName("projectitem");

            projectListItem.addClickListener(e -> {
                if (CurrentUser.get() != null && CurrentUser.get().getCurrentProject() != project.getId()) {
                    final var redirectDialog = new Dialog();
                    final var buttons = new HorizontalLayout();
                    buttons.getStyle().set("padding-top", "1em");
                    final var confirm = new Button("Switch", s -> {
                        CurrentUser.get().setCurrentProject(project.getId());
                        // small trick to also refresh overview
                        UI.getCurrent().navigate("login");
                        UI.getCurrent().navigate("");
                        redirectDialog.close();
                    });
                    confirm.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
                    final var cancel = new Button("Cancel", c -> redirectDialog.close());
                    cancel.getStyle().set("margin-left", "auto");
                    buttons.add(cancel, confirm);
                    redirectDialog.add(new H3("Switch the current project to: \"" + project.getName() + "\"?"), buttons);

                    redirectDialog.open();

                }
            });

            if (mode == Modes.LARGE) {
                final var letter = new LetterIcon(project.getName().substring(0, 1));
                letter.getStyle().set("font-size", "2.5rem");
                projectListItem.addClassName("projectitem-large");
                projectListItem.add(letter);
                projectListItem.add(new H4(project.getName()));
            } else if (mode == Modes.SMALL) {
                projectListItem.addClassName("projectitem-small");
                projectListItem.add(new Span(project.getName()));
            }
            this.add(projectListItem);
        });
    }
}
