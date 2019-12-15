package org.coffeemine.app.spring.trackall;

import ch.carnet.kasparscherrer.VerticalScrollLayout;
import com.vaadin.flow.component.HasStyle;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Paragraph;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.Route;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.components.Collapsible;
import org.coffeemine.app.spring.data.TrackItem;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.View;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

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
        scroller.setWidth("30%");
        inner.add(scroller, detailed_pane);
        inner.getStyle().set("width", "100%");
        inner.setHeightFull();
        outer.add(new Button("New issue", e -> Notification.show("unimplemented")));
        outer.add(inner);
        outer.setHeightFull();
        add(outer);

        items.addAll(NitriteDBProvider.getInstance().getTrackItems().map(ListItem::new).collect(Collectors.toList()));

        item_list.addSelectedChangeListener(e -> update_details(((ListItem) e.getSelectedTab()).item));

        sync_list();
        update_details(items.get(0).item);
    }

    private void sync_list() {
        item_list.removeAll();
        item_list.add(items.toArray(new ListItem[]{}));
    }

    private void update_details(TrackItem ti) {
        detailed_pane.removeAll();
        if(ti == null){
            // TODO: dflt

            return;
        }
        final var details_collapse = new Collapsible(false,
                new VerticalLayout(
                        new Span("Type: " + ti.getType().name()),
                        new Span("Status: " + ti.getStatus().name()),
                        new Span("Confirmation status: " + (ti.isConfirmed() ? "Confirmed" : "Unconfirmed")),
                        new Span("Resolution: " + ti.getResolution().name())
                ));
        final var details_toggle = new Button("Details", e -> details_collapse.toggle());
        final var description_collapse = new Collapsible(false,
                new Paragraph("Hello darkness my old friend..."));
        final var description_toggle = new Button("Description", e -> description_collapse.toggle());
        final var left_col = new VerticalLayout(details_toggle, details_collapse, description_toggle, description_collapse);
        left_col.setWidth("150%");

        final var people_collapse = new Collapsible(false,
                new VerticalLayout(
                    new Span("Assignee: " + ti.getAssignee()),
                    new Span("Reporter: " + ti.getReporter()))
        );
        final var people_toggle = new Button("People", e -> people_collapse.toggle());
        final var dates_collapes = new Collapsible(false,
                new VerticalLayout(
                        new Span("Created: " + ti.getOpened().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)), //TODO: custom date formatter YYYY-MM-DD hh:mm
                        new Span("Resolved: " + (ti.getResolved() != null ? ti.getResolved().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) : "N/A"))
                ));
        final var dates_toggle = new Button("Dates", e -> dates_collapes.toggle());
        final var right_col = new VerticalLayout(people_toggle, people_collapse, dates_toggle, dates_collapes);
        final var columns_holder = new HorizontalLayout(left_col, right_col);
        columns_holder.setWidth("100%");
        detailed_pane.add(new VerticalLayout(new H3(ti.getName()), columns_holder));
    }
}
