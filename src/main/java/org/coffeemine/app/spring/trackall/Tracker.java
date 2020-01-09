package org.coffeemine.app.spring.trackall;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.Tabs;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.components.Collapsible;
import org.coffeemine.app.spring.components.CreateTrackItem;
import org.coffeemine.app.spring.data.TrackItem;
import org.coffeemine.app.spring.data.User;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.View;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.stream.Collectors;

@Route(value = "Tracker")
@PageTitle("CoffeeMine - Tracker")
@NavbarRoutable
public class Tracker extends View {
    private static class ListItem extends Tab {

        private TrackItem item;

        ListItem(TrackItem item) {
            super();
            this.item = item;
            final var inner_container = new VerticalLayout(new Span('#' + Integer.toString(item.getId())),
                    new Span(item.getName()));
            final var outer_container = new HorizontalLayout(
                    new Span(Character.toString(item.getType().name().charAt(0))), inner_container);

            outer_container.setPadding(false);
            inner_container.setPadding(false);
            this.getStyle().set("align-items", "flex-start");
            super.add(outer_container);
        }

    }

    private SplitLayout inner = new SplitLayout();
    private Tabs item_list = new Tabs();
    private ArrayList<ListItem> items = new ArrayList<>();
    private VerticalLayout detailed_pane = new VerticalLayout();
    private VerticalLayout wrapper = new VerticalLayout();
    private HorizontalLayout topbar = new HorizontalLayout();

    public Tracker() {
        super();
        add(wrapper);
        wrapper.setSizeFull();

        topbar.addClassName("tracking-topbar");
        // left alligned
        final var controls = new Div();
        final var newIssueButton = new Button("New Issue", e -> new CreateTrackItem(ti -> NitriteDBProvider.getInstance().addTrackItem(ti)));
        newIssueButton.addThemeVariants(ButtonVariant.MATERIAL_CONTAINED);
        controls.add(newIssueButton);
        // centered
        final var text = new Div(new H3("Tracker"));
        text.getStyle().set("text-align", "center");
        // right alligned
        final var rest = new Div();
        rest.getStyle().set("text-align", "right");

        topbar.add(controls, text, rest);
        wrapper.add(topbar);

        inner.setSizeFull();
        inner.setSplitterPosition(0);

        detailed_pane.addClassName("tracking-detailed-pane");

        item_list.setOrientation(Tabs.Orientation.VERTICAL);
        item_list.setWidthFull();

        final var scroller = new VerticalLayout(item_list);
        scroller.addClassName("trackall-scroller");

        inner.addToPrimary(scroller);
        inner.addToSecondary(detailed_pane);
        wrapper.add(inner);

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
        if (ti == null) {
            // TODO: dflt

            return;
        }

        final var confirm_status = new Div();
        final var status = new Div();
        final var resolution = new Div();
        final var assignee = new Div();
        if (CurrentUser.get() != null && CurrentUser.get().getStatus().equals(User.Status.ADMIN)) {
            {
                final var sel = new Select<>("Confirmed", "Unconfirmed");
                sel.setValue((ti.isConfirmed() ? "Confirmed" : "Unconfirmed"));
                sel.addValueChangeListener(e -> {
                    ti.setConfirmed(e.getValue().equals("Confirmed"));
                    NitriteDBProvider.getInstance().updateTrackItem(ti);
                    update_details(ti);
                });
                sel.setLabel("Confirmation status:");
                confirm_status.add(sel);
            }
            {
                final var sel = new Select<>(TrackItem.Status.values());
                sel.setValue(ti.getStatus());
                sel.addValueChangeListener(e -> {
                    ti.setStatus(e.getValue());
                    NitriteDBProvider.getInstance().updateTrackItem(ti);
                    update_details(ti);
                });
                sel.setLabel("Status:");
                status.add(sel);
            }
            {
                final var sel = new Select<>(TrackItem.Resolution.values());
                sel.setValue(ti.getResolution());
                sel.addValueChangeListener(e -> {
                    ti.setResolution(e.getValue());
                    if (e.getValue() == TrackItem.Resolution.FIXED || e.getValue() == TrackItem.Resolution.IMPLEMENTED) {
                        ti.setResolved(LocalDateTime.now());
                    } else
                        ti.setResolved(null);
                    NitriteDBProvider.getInstance().updateTrackItem(ti);
                    update_details(ti);
                });
                sel.setLabel("Status:");
                resolution.add(sel);
            }
            {
                final var sel = new Select<>(NitriteDBProvider.getInstance().getUsers().collect(Collectors.toList()).toArray(new User[]{}));
                sel.setItemLabelGenerator(u -> u != null ? u.getName() : "N/A");
                sel.setValue(NitriteDBProvider.getInstance().getUser(ti.getAssignee()));
                if (NitriteDBProvider.getInstance().getUser(ti.getAssignee()) != null)
                    Notification.show("Assigned to" + NitriteDBProvider.getInstance().getUser(ti.getAssignee()).getName());

                sel.addValueChangeListener(e -> {
                    ti.setAssignee(e.getValue().getId());
                    NitriteDBProvider.getInstance().updateTrackItem(ti);
                    update_details(ti);
                });

                sel.setLabel("Assignee:");
                assignee.add(sel);
            }
        } else {
            confirm_status.add(new Span("Confirmation status: " + (ti.isConfirmed() ? "Confirmed" : "Unconfirmed")));
            status.add(new Span("Status: " + ti.getStatus().getVal()));
            resolution.add(new Span("Resolution: " + ti.getResolution().name()));
            assignee.add(new Span("Assignee: " + (ti.getAssignee() < 0 ? "N/A" : NitriteDBProvider.getInstance().getUser(ti.getAssignee()).getName())));
        }

        final var details_collapse = new Collapsible(false,
                new VerticalLayout(
                        new Span("Type: " + ti.getType().getVal()),
                        status,
                        confirm_status,
                        resolution
                ));
        final var details_toggle = new Button("Details", e -> details_collapse.toggle());
        final var description_collapse = new Collapsible(false,
                new Paragraph("Hello darkness my old friend..."));
        final var description_toggle = new Button("Description", e -> description_collapse.toggle());
        final var left_col = new VerticalLayout(details_toggle, details_collapse, description_toggle, description_collapse);

        final var people_collapse = new Collapsible(false,
                new VerticalLayout(
                        assignee,
                        new Span("Reporter: " + NitriteDBProvider.getInstance().getUser(ti.getReporter()).getName()))
        );
        final var people_toggle = new Button("People", e -> people_collapse.toggle());
        final var dates_collapes = new Collapsible(false,
                new VerticalLayout(
                        new Span("Created: " + ti.getOpened().format(DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss"))),
                        new Span("Resolved: " + (ti.getResolved() != null ? ti.getResolved().format(DateTimeFormatter.ofPattern("uuuu MMM dd HH:mm:ss")) : "N/A"))
                ));
        final var dates_toggle = new Button("Dates", e -> dates_collapes.toggle());
        final var right_col = new VerticalLayout(people_toggle, people_collapse, dates_toggle, dates_collapes);
        final var columns_holder = new HorizontalLayout(left_col, right_col);

        columns_holder.setWidthFull();
        left_col.setWidthFull();
        right_col.setWidthFull();

        detailed_pane.add(new H2(ti.getName()), columns_holder);
    }
}
