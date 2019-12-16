package org.coffeemine.app.spring.components.eventform;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;

public abstract class EventForm {
    private Dialog dialog;
    private FormLayout newForm;
    private Button save;

    public EventForm(){
        dialog = new Dialog();
        newForm = new FormLayout();
        save = new Button();
    }

    public Dialog getDialog() {
        return dialog;
    }

    public FormLayout getNewForm() {
        return newForm;
    }

    public Button getSave() {
        return save;
    }
}