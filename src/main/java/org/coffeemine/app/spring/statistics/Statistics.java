package org.coffeemine.app.spring.statistics;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.html.H3;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import org.coffeemine.app.spring.annonations.NavbarRoutable;
import org.coffeemine.app.spring.components.BarChart;
import org.coffeemine.app.spring.components.RiskMatrix;
import org.coffeemine.app.spring.components.RadialBarChart;
import org.coffeemine.app.spring.view.View;

@Route
@NavbarRoutable
public class Statistics extends View {

    public Statistics() {

        VerticalLayout layout = new VerticalLayout();
        H1 header = new H1("Statistics");
        H3 subtitle = new H3("Earned Value Analysis");

        this.add(header);
        this.add(subtitle);
        this.add(layout);

        layout.add(new Span("Actual Value: " ));
        layout.add(new Span("Planned Value: "));
        layout.add(new Span("Cost Variance: "));
        layout.add(new Span("Schedule Variance: "));
        layout.add(new Span("Cost Performance Index: "));
        layout.add(new Span("Schedule Performance Index: "));

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
