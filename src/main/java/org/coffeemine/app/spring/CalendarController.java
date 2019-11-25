package org.coffeemine.app.spring;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.component.textfield.TextField;

class CalendarController extends HorizontalLayout {
    CalendarController(){

        Button today = new Button("Today", event -> CalendarComponent.calendarGoToday());
        Button previous = new Button("<< Previous", event -> CalendarComponent.calendarGoPrevious());
        Button next = new Button("next >>",event -> CalendarComponent.calendarGoNext());
        Select<String> selectView = new Select<>("Month", "Week", "Day");
        selectView.setPlaceholder("View as..");
        selectView.addValueChangeListener(event -> CalendarComponent.changeCalendarView(event.getValue()));
        Button newTask = new Button("New task", event -> CalendarComponent.createNewTask());

        add(previous);
        add(today);
        add(next);
        add(selectView);
        add(newTask);
    }

    FormLayout newTaskForm() {
        FormLayout newForm = new FormLayout();
        newForm.addFormItem(new TextField(), "Task Name");
        return newForm;
    }
}
