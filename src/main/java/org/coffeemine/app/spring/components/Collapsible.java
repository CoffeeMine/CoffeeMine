package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Tag;
import com.vaadin.flow.component.dependency.JsModule;
import com.vaadin.flow.component.dependency.NpmPackage;
import com.vaadin.flow.component.polymertemplate.PolymerTemplate;
import com.vaadin.flow.dom.Element;
import com.vaadin.flow.templatemodel.TemplateModel;

@Tag("iron-collapse")
@JsModule("./src/Collapsible.js")
@NpmPackage(value = "@polymer/iron-collapse", version = "3.0.1")
public class Collapsible extends PolymerTemplate<Collapsible.IronCollapseModel> {

    public Collapsible() {
        super();
    }

    public Collapsible(boolean collapsed) {
        super();
        setOpened(!collapsed);
    }

    public Collapsible(boolean collapsed, Component... components) {
        super();
        setOpened(!collapsed);

        final var els = new Element[components.length];
        for (int i = 0; i < components.length; ++i)
            els[i] = components[i].getElement();

        getElement().appendChild(els);
    }

    public boolean getHorizontal() { return getModel().getHorizontal(); }
    public void setHorizontal(boolean horizontal) { getModel().setHorizontal(horizontal); }

    public boolean getOpened() { return getModel().getOpened(); }
    public void setOpened(boolean opened) { getModel().setOpened(opened); }

    public boolean getNoAnimation() { return getModel().getNoAnimation(); }
    public void setNoAnimation(boolean no_animation) { getModel().setNoAnimation(no_animation); }

    public void toggle() {
        setOpened(!getOpened());
    }

    public interface IronCollapseModel extends TemplateModel {

        boolean getHorizontal();
        void setHorizontal(boolean horizontal);

        boolean getOpened();
        void setOpened(boolean opened);

        boolean getNoAnimation();
        void setNoAnimation(boolean no_animation);
    };
}
