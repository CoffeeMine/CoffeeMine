package org.coffeemine.app.spring;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.router.Route;

import org.coffeemine.app.spring.annonations.NavbarRoutable;

@Route
@NavbarRoutable
public class Statistics extends View {

    public Statistics() {
        FormLayout layout = new FormLayout();
        H1 header = new H1("Statistics");
        H3 subtitle = new H3("Earned Value Analysis");

        this.add(header);
        this.add(subtitle);
        this.add(layout);

        Span initiallyPlannedBudget = new Span("");
        layout.addFormItem(initiallyPlannedBudget, "Initially Planned Budget");

        Span actualValue = new Span("");
        layout.addFormItem(actualValue, "Actual Value");

        Span plannedValue = new Span("");
        layout.addFormItem(plannedValue, "Planned Value");

        Span costVariance = new Span("");
        layout.addFormItem(costVariance, "Cost Variance");

        Span scheduleVariance = new Span("");
        layout.addFormItem(scheduleVariance, "Schedule Variance");

        Span costPerformance = new Span("");
        layout.addFormItem(costPerformance, "Cost Performance Index");

        Span schedulePerformance = new Span("");
        layout.addFormItem(schedulePerformance, "Schedule Performance Index");

        Div barChartLayout = new Div();
        barChartLayout.getStyle().set("margin", "auto");
        barChartLayout.setMaxWidth("800px");
        this.add(barChartLayout);
        BarChart barChart = new BarChart();
        barChartLayout.add(barChart);

        Div radialChartLayout = new Div();
        radialChartLayout.getStyle().set("margin", "auto");
        radialChartLayout.setMaxWidth("400px");
        this.add(radialChartLayout);
        RadialBarChart radialBarChart = new RadialBarChart();
        radialChartLayout.add(radialBarChart);

    }

}
