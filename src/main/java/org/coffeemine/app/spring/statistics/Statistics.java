package org.coffeemine.app.spring.statistics;

import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.components.BarChart;
import org.coffeemine.app.spring.components.RiskMatrix;
import org.coffeemine.app.spring.components.RadialBarChart;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.view.View;

@Route
@NavbarRoutable
public class Statistics extends View {

    private StatisticsCalculation statisticsCalculation;

    public Statistics() {

        statisticsCalculation = new StatisticsCalculation();

        final var currentProject = NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get());
        final var currentSprint = NitriteDBProvider.getInstance().getCurrentSprint(currentProject);

        Div mainLayout = new Div();
        this.add(mainLayout);
        mainLayout.getStyle().set("text-align", "center");

        Div statsLayout = new Div();
        statsLayout.getStyle().set("margin", "auto");
        FormLayout layout = new FormLayout();
        H1 header = new H1("Statistics");
        H3 subtitle = new H3("Earned Value Analysis");

        mainLayout.add(header);
        mainLayout.add(subtitle);
        statsLayout.add(layout);
        this.add(statsLayout);

        Span actualValue = new Span("SEK " + Float.toString(statisticsCalculation.actualValueSprint(currentSprint)));
        layout.addFormItem(actualValue, "Actual Value");

        Span plannedValue = new Span("SEK " + Float.toString(statisticsCalculation.plannedValueSprint(currentSprint)));
        layout.addFormItem(plannedValue, "Planned Value");

        Span earnedValue = new Span("SEK " + Float.toString(statisticsCalculation.earnedValue(currentSprint)));
        layout.addFormItem(earnedValue, "Earned Value");

        Span costVariance = new Span("SEK " + Float.toString(statisticsCalculation.costVariance(currentSprint)));
        layout.addFormItem(costVariance, "Cost Variance");

        Span scheduleVariance = new Span("SEK " + Float.toString(statisticsCalculation.scheduleVariance(currentSprint)));
        layout.addFormItem(scheduleVariance, "Schedule Variance");

        Span costPerformance = new Span("SEK " + Double.toString(statisticsCalculation.costPerformanceIndex(currentSprint)) +"%");
        layout.addFormItem(costPerformance, "Cost Performance Index");

        Span schedulePerformance = new Span("SEK " + Double.toString(statisticsCalculation.schedulePerformanceIndex(currentSprint)) +"%");
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

        Div matrixLayout = new Div();
        this.add(matrixLayout);
        matrixLayout.getStyle().set("text-align", "center");
        matrixLayout.add(new H3("Risk Matrix"));

        RiskMatrix riskMatrix = new RiskMatrix();
        this.add(riskMatrix);

    }

}
