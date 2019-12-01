package org.coffeemine.app.spring;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

@Route
public class Statistics extends VerticalLayout {

    public Statistics(){
        final var content_div = new Div();

        FormLayout layout = new FormLayout();
        H1 header = new H1("Statistics");
        H3 eva = new H3("Earned Value Analysis");

        content_div.add(header);
        content_div.add(eva);
        content_div.add(layout);
        this.add(content_div);

        Span ipb = new Span("");
        layout.addFormItem(ipb, "Initially Planned Budget");

        Span av = new Span("");
        layout.addFormItem(av, "Actual Value");

        Span pv = new Span("");
        layout.addFormItem(pv, "Planned Value");

        Span cv = new Span("");
        layout.addFormItem(cv, "Cost Variance");

        Span sv = new Span("");
        layout.addFormItem(sv, "Schedule Variance");

        Span cpi = new Span("");
        layout.addFormItem(cpi, "Cost Performance Index");

        Span spi = new Span("");
        layout.addFormItem(spi, "Schedule Performance Index");

        H3 rm = new H3("Risk Matrix");
        content_div.add(rm);

        FormLayout matrixLayout = new FormLayout();
        content_div.add(matrixLayout);

        Span initiallyPlannedBudget = new Span("");
        matrixLayout.addFormItem(initiallyPlannedBudget, "Initially Planned Budget");

        Span actualValue = new Span("");
        matrixLayout.addFormItem(actualValue, "Actual Value");

        Span plannedValue = new Span("");
        matrixLayout.addFormItem(plannedValue, "Planned Value");

        Span costVariance = new Span("");
        matrixLayout.addFormItem(costVariance, "Cost Variance");

        Span scheduleVariance = new Span("");
        matrixLayout.addFormItem(scheduleVariance, "Schedule Variance");

        Span costPerformance = new Span("");
        matrixLayout.addFormItem(costPerformance, "Cost Performance Index");

        Span schedulePerformance = new Span("");
        matrixLayout.addFormItem(schedulePerformance, "Schedule Performance Index");

    }




}
