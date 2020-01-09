package org.coffeemine.app.spring.statistics;

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

        VerticalLayout layout = new VerticalLayout();
        H1 header = new H1("Statistics");
        H3 subtitle = new H3("Earned Value Analysis");

        this.add(header);
        this.add(subtitle);
        this.add(layout);

        layout.add(new Span("Actual Value: SEK " + Float.toString(statisticsCalculation.actualValueSprint(currentSprint))));
        layout.add(new Span("Planned Value: SEK " + Float.toString(statisticsCalculation.plannedValueSprint(currentSprint))));
        layout.add(new Span("Earned Value: SEK " + Float.toString(statisticsCalculation.earnedValue(currentSprint))));
        layout.add(new Span("Cost Variance: SEK " + Float.toString(statisticsCalculation.costVariance(currentSprint))));
        layout.add(new Span("Schedule Variance: SEK "+Float.toString(statisticsCalculation.scheduleVariance(currentSprint))));
        layout.add(new Span("Cost Performance Index: "+ Double.toString(statisticsCalculation.costPerformanceIndex(currentSprint)) +"%"));
        layout.add(new Span("Schedule Performance Index: "+ Double.toString(statisticsCalculation.schedulePerformanceIndex(currentSprint)) +"%"));

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

        this.add(new H3("Risk Matrix"));

        RiskMatrix riskMatrix = new RiskMatrix();
        this.add(riskMatrix);

    }

}
