package org.coffeemine.app.spring.trackall;

import ch.carnet.kasparscherrer.VerticalScrollLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.view.View;

import java.util.ArrayList;

@Route(value = "Tracker")
@NavbarRoutable
public class TrackallView extends View {
    private static class ListItem extends Div {
        ListItem() {
            super();
            getStyle().set("border-bottom", "1px solid yellow");
        }

        ListItem(Component... components) {
            this();
            super.add(components);
        }

    }

    private VerticalLayout outer = new VerticalLayout();
    private HorizontalLayout inner = new HorizontalLayout();
    private VerticalScrollLayout item_list = new VerticalScrollLayout();
    private ArrayList<ListItem> items = new ArrayList<>();
    private Div detailed_pane = new Div();

    public TrackallView() {
        super();
        detailed_pane.getStyle().set("border-left", "1px thick solid #ff0000");
        detailed_pane.getStyle().set("width", "100%");
        detailed_pane.getStyle().set("background-color", "#000080");
        detailed_pane.add(new Span("It all works, except not #2351"));
        item_list.setWidth("375px");
        item_list.setHeightFull();
        item_list.getStyle().set("background-color", "green");

        inner.add(item_list, detailed_pane);
        inner.getStyle().set("width", "100%");
        inner.setHeightFull();
        outer.add(inner);
        outer.setHeightFull();
        add(outer);

        final var el = new ListItem(new Span("Hello guys 'n' gals"));

        items.add(el);
        sync_list();
    }

    private void sync_list() {
        item_list.removeAll();
        item_list.add(items.toArray(new ListItem[]{}));
    }
}
