package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.data.value.ValueChangeMode;

public class SpaceableTextArea extends TextArea {

    public SpaceableTextArea() {
        setValueChangeMode(ValueChangeMode.EAGER);
        addKeyDownListener(Key.SPACE, e -> setValue(getOptionalValue().orElse("") + " "));
    }

    public SpaceableTextArea(String label) {
        this();
        setLabel(label);
    }

    public SpaceableTextArea(String label, String placeholder) {
        this(label);
        setPlaceholder(placeholder);
    }
}
