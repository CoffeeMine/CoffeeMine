package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

import java.time.LocalDate;

class CalendarComponent extends HorizontalLayout {

    CalendarComponent(CalendarForm calendarForm){
        Button today = new Button("Today", event -> calendarForm.calendarGoToday());
        Button previous = new Button("<< Previous", event -> calendarForm.calendarGoPrevious());
        Button next = new Button("next >>",event -> calendarForm.calendarGoNext());
        Select<String> selectView = new Select<>("Month", "Week", "Day");
        selectView.setPlaceholder("View as..");
        selectView.addValueChangeListener(event -> calendarForm.changeCalendarView(event.getValue()));
        Button newTask = new Button("New task", event -> calendarForm.createNewTask());
        DatePicker datePicker = new DatePicker();

        datePicker.setPlaceholder("Date within this month");
        LocalDate nowDate = LocalDate.now();

        datePicker.setClearButtonVisible(true);

        datePicker.addValueChangeListener( event -> {
            LocalDate newDate = event.getValue();
            calendarForm.calendarGoToDate(newDate);
        });

        add(previous);
        add(today);
        add(next);
        add(selectView);
        add(newTask);
        add(datePicker);
    }

    FormLayout newTaskForm() {
        FormLayout newForm = new FormLayout();
        newForm.addFormItem(new TextField(), "Task Name");
        return newForm;
    }
}
