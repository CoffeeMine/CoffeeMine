package org.coffeemine.app.spring.view;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.H4;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.data.value.ValueChangeMode;

import org.coffeemine.app.spring.components.LetterIcon;
import org.coffeemine.app.spring.components.SpaceableTextField;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.userprofile.UserProfile;

class SearchPopUp extends VerticalLayout {

    private SpaceableTextField searchbar;

    public SearchPopUp() {
        addClassNames("accountinfo", "mono_font");
        setVisible(false);
        setAlignItems(Alignment.CENTER);
        setWidth("350px");

        final var results = new VerticalLayout();
        results.addClassNames("mono_font", "projectlist");

        searchbar = new SpaceableTextField();

        searchbar.setWidthFull();
        searchbar.setValueChangeMode(ValueChangeMode.EAGER);

        final var nothing = new H4(".. type to start search ..");

        searchbar.addValueChangeListener(event -> {
            results.removeAll();
            if (!event.getValue().equals("")) {
                nothing.setVisible(false);
                NitriteDBProvider.getInstance().getUsers().filter(user -> {
                    final var nameMatch = user.getName().toLowerCase().contains(event.getValue().toLowerCase());
                    var idMatch = false;
                    try {
                        idMatch = user.getId() == Integer.parseInt(event.getValue());
                    } catch (Exception e) {
                    }
                    return nameMatch || idMatch;
                }).forEach(user -> {
                    final var projectListItem = new HorizontalLayout();
                    projectListItem.addClassName("projectitem");
                    projectListItem.addClickListener(
                            e -> UI.getCurrent().navigate(UserProfile.class, Integer.toString(user.getId())));

                    final var letter = new LetterIcon(user.getName().substring(0, 1));
                    letter.getStyle().set("font-size", "1.5rem");
                    projectListItem.addClassName("projectitem-large");
                    projectListItem.add(letter);
                    projectListItem.add(new H4(user.getName()));

                    results.add(projectListItem);
                });
            } else {
                nothing.setVisible(true);
            }
        });
        add(searchbar, nothing, results);
    }

    public void focus() {
        searchbar.focus();
    }
}
