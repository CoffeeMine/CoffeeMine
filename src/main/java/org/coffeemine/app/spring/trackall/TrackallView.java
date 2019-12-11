package org.coffeemine.app.spring.trackall;

import ch.carnet.kasparscherrer.VerticalScrollLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.view.View;

@Route(value = "Tracker")
@NavbarRoutable
public class TrackallView extends View {
    private VerticalLayout outer = new VerticalLayout();
    private HorizontalLayout inner = new HorizontalLayout();
    private VerticalScrollLayout item_list = new VerticalScrollLayout();

    public TrackallView() {
        super();
        item_list.setWidth("100px");
        inner.add(item_list);
        outer.add(inner);
        add(item_list);
    }
}
