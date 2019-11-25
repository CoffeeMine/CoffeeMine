package org.coffeemine.app.spring;

import com.vaadin.flow.component.applayout.AppLayout;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
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

        TextField ipb = new TextField();
        ipb.setEnabled(false);
        layout.addFormItem(ipb, "Initially Planned Budget");

        TextField av = new TextField();
        av.setEnabled(false);
        layout.addFormItem(av, "Actual Value");

        TextField pv = new TextField();
        pv.setEnabled(false);
        layout.addFormItem(pv, "Planned Value");

        TextField cv = new TextField();
        cv.setEnabled(false);
        layout.addFormItem(cv, "Cost Variance");

        TextField sv = new TextField();
        sv.setEnabled(false);
        layout.addFormItem(sv, "Schedule Variance");

        TextField cpi = new TextField();
        cpi.setEnabled(false);
        layout.addFormItem(cpi, "Cost Performance Index");

        TextField spi = new TextField();
        spi.setEnabled(false);
        layout.addFormItem(spi, "Schedule Performance Index");

        H3 rm = new H3("Risk Matrix");
        content_div.add(rm);

        FormLayout matrixLayout = new FormLayout();
        content_div.add(matrixLayout);

        TextField initiallyPlannedBudget = new TextField();
        initiallyPlannedBudget.setEnabled(false);
        matrixLayout.addFormItem(initiallyPlannedBudget, "Initially Planned Budget");

        TextField actualValue = new TextField();
        actualValue.setEnabled(false);
        matrixLayout.addFormItem(actualValue, "Actual Value");

        TextField plannedValue = new TextField();
        plannedValue.setEnabled(false);
        matrixLayout.addFormItem(plannedValue, "Planned Value");

        TextField costVariance = new TextField();
        costVariance.setEnabled(false);
        matrixLayout.addFormItem(costVariance, "Cost Variance");

        TextField scheduleVariance = new TextField();
        scheduleVariance.setEnabled(false);
        matrixLayout.addFormItem(scheduleVariance, "Schedule Variance");

        TextField costPerformance = new TextField();
        costPerformance.setEnabled(false);
        matrixLayout.addFormItem(costPerformance, "Cost Performance Index");

        TextField schedulePerformance = new TextField();
        schedulePerformance.setEnabled(false);
        matrixLayout.addFormItem(schedulePerformance, "Schedule Performance Index");

    }




}
