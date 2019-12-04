package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.Task;

import java.time.LocalDate;

public class EventCreation {
    public EventCreation(){

    }

    public static Task TaskCreation(){
        Dialog dialog = new Dialog();

        FormLayout newForm = new FormLayout();;
        TextField taskName = new TextField();
        DatePicker startTime = new DatePicker();
        startTime.setValue(LocalDate.now());
        DatePicker endTime = new DatePicker();
        endTime.setValue(LocalDate.now());
        TextArea description = new TextArea();
        description.setPlaceholder("Please provide task description here");
        Select<String> assignPeople = new Select<>("Bob", "John", "Rick", "Mahaa", "Tylo");
        assignPeople.setPlaceholder("Assigning to..");
        Select<String> assignSprint = new Select<>("Sprint 1", "Sprint 2", "Sprint 3");
        assignSprint.setPlaceholder("Assigning to sprint..");
        //selectView.addValueChangeListener(event ->);
        Button save = new Button("Create");
        Button reset = new Button("Reset");
        save.addClickListener(event -> {
            dialog.close();
        });
        //reset.addClickListener(event -> { });

        newForm.addFormItem(taskName, "Task Name");
        newForm.addFormItem(assignPeople, "Task assign");
        newForm.addFormItem(startTime,"Start Time");
        newForm.addFormItem(endTime,"End Time");
        newForm.addFormItem(assignSprint, "Sprint assign");
        newForm.addFormItem(description, "Task Description");
        newForm.add(save, reset);

        dialog.add(newForm);
        dialog.open();

        return new Task();
    }
}
