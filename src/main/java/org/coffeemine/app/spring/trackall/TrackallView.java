package org.coffeemine.app.spring.trackall;

import ch.carnet.kasparscherrer.VerticalScrollLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.data.TrackItem;
import org.coffeemine.app.spring.view.View;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Route(value = "Tracker")
@NavbarRoutable
public class TrackallView extends View {
    private static class ListItem extends Tab {

        private TrackItem item;

        ListItem(TrackItem item) {
            super();
            this.item = item;
            getStyle().set("border-bottom", "1px solid yellow");
            final var inner_container = new VerticalLayout(
                    new Span('#' + Integer.toString(item.getId())),
                    new Span(item.getName()));
            final var outer_container = new HorizontalLayout(
                    new Span(Character.toString(item.getType().name().charAt(0))),
                    inner_container);
            inner_container.getChildren().forEach(c -> (((HasStyle) c)).getStyle().set("all", "initial"));
            super.add(outer_container);
        }

    }

    private VerticalLayout outer = new VerticalLayout();
    private HorizontalLayout inner = new HorizontalLayout();
    private Tabs item_list = new Tabs();
    private ArrayList<ListItem> items = new ArrayList<>();
    private Div detailed_pane = new Div();

    public TrackallView() {
        super();
        detailed_pane.getStyle().set("border-left", "1px thick solid #ff0000");
        detailed_pane.getStyle().set("width", "100%");
        detailed_pane.getStyle().set("background-color", "#000080");
        detailed_pane.add(new Span("It all works, except not #2351"));
        item_list.setOrientation(Tabs.Orientation.VERTICAL);
        item_list.setHeightFull();
        item_list.getStyle().set("background-color", "green");

        final var scroller =  new VerticalScrollLayout(item_list);
        scroller.setWidth("375px");
        inner.add(scroller, detailed_pane);
        inner.getStyle().set("width", "100%");
        inner.setHeightFull();
        outer.add(inner);
        outer.setHeightFull();
        add(outer);

        items.add(new ListItem(new TrackItem(3, "Bob is broken", "Bob doesn't work since the update", 3,2, TrackItem.Type.BUG, true, TrackItem.Status.OPEN, TrackItem.Resolution.UNRESOLVED, LocalDateTime.now(), LocalDateTime.now())));
        items.add(new ListItem(new TrackItem(45, "Change color", "Green is better than yellow", 3,1, TrackItem.Type.FEATURE_REQUEST, true, TrackItem.Status.IN_PROGRESS, TrackItem.Resolution.FIXED, LocalDateTime.now(), LocalDateTime.now())));

        item_list.addSelectedChangeListener(e -> update_details(((ListItem) e.getSelectedTab()).item));

        sync_list();
        update_details(items.get(0).item);
    }

    private void sync_list() {
        item_list.removeAll();
        item_list.add(items.toArray(new ListItem[]{}));
    }

    private void update_details(TrackItem ti) {
        if(ti == null){
            // TODO: dflt
        }
    }
}
