package org.coffeemine.app.spring;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.html.Span;

class LetterIcon extends Span {

    public LetterIcon() {
        super();
        this.setClassName("lettericon");
    }

    public LetterIcon(Component... components) {
        super(components);
        this.setClassName("lettericon");
    }

    public LetterIcon(String text) {
        super();
        setText(text);
        this.setClassName("lettericon");
    }
}
