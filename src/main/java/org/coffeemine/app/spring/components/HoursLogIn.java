package org.coffeemine.app.spring.components;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import org.coffeemine.app.spring.data.Task;
import java.time.LocalDate;

public class HoursLogIn {
    private Task newTask;
    private Dialog dialog;
    private FormLayout newForm;
    private Button hoursLogIn;
    private Button create;
    private Button cancel;

    public HoursLogIn(){
        newTask = new Task();
        dialog = new Dialog();
        newForm = new FormLayout();
    }

    public void taskCreating(){
        TextField taskName = new TextField();
        DatePicker startTime = new DatePicker();
        startTime.setValue(LocalDate.now());
        DatePicker endTime = new DatePicker();
        endTime.setValue(LocalDate.now());
        TextArea description = new TextArea();
        description.setPlaceholder("Please provide project description here");

        hoursLogIn = new Button("Hours log in");
        hoursLogIn.addThemeVariants(ButtonVariant.MATERIAL_OUTLINED);
        hoursLogIn.addClickListener(event -> dialog.open());
        create = new Button("Create");
        cancel = new Button("Cancel");
        cancel.addClickListener(event -> dialog.close());
        create.addClickListener(event -> {
            newTask.setName(taskName.getValue());
            newTask.setDescription(description.getValue());
            Notification.show("Project " + newTask.getName() + " is now saved");
            dialog.close();
    });

        newForm.addFormItem(taskName, "Project name：");
        newForm.addFormItem(description, "Project description: ");
        newForm.addFormItem(startTime, "Start time: ");
        newForm.addFormItem(endTime, "End time: ");
        newForm.add(create,cancel);
        dialog.add(newForm);
        dialog.open();

    }
}
