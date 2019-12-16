package org.coffeemine.app.spring.components.eventform;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;

public abstract class EventForm {
    private Dialog dialog = new Dialog();
    private FormLayout newForm = new FormLayout();
    private Button save = new Button();

    public EventForm(){ }

    protected Dialog getDialog() {
        return dialog;
    }

    protected FormLayout getNewForm() {
        return newForm;
    }

    protected Button getSave() {
        return save;
    }
}