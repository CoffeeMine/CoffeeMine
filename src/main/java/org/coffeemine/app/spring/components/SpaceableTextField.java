package org.coffeemine.app.spring.components;

import com.vaadin.flow.component.Key;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.value.ValueChangeMode;

public class SpaceableTextField extends TextField {

    public SpaceableTextField() {
        setValueChangeMode(ValueChangeMode.EAGER);
        addKeyDownListener(Key.SPACE, e -> setValue(getOptionalValue().orElse("") + " "));
    }

    public SpaceableTextField(String label) {
        this();
        setLabel(label);
    }

    public SpaceableTextField(String label, String placeholder) {
        this(label);
        setPlaceholder(placeholder);
    }
}
