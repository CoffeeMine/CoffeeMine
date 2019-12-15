package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.polymertemplate.Id;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.templatemodel.TemplateModel;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;

@Tag("horizontal-scroll")
@JsModule("./src/HorizontalScroll.js")
public class HorizontalScroll extends PolymerTemplate<TemplateModel> {

    @Id("container")
    private HorizontalLayout container;

    public HorizontalScroll() {
    }

    public void add(Component component) {
        getElement().appendChild(component.getElement());
    }

    public void add(Component... components) {
        for (Component component : components) {
            add(component);
        }
    }
}

