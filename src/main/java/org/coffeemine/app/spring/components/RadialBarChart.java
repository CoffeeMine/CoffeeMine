package org.coffeemine.app.spring.components;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import org.coffeemine.app.spring.view.View;

public class RadialBarChart extends View {

    public RadialBarChart() {

        ApexCharts multiRadialBarChart = ApexChartsBuilder.get()
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("Cost & Schedule Performance Index")
                        .build())
                .withChart(ChartBuilder.get()
                        .withType(Type.radialBar)
                        .build())
                .withSeries(44.0, 55.0)
                .withLabels("Cost Performance", "Schedule Performance")
                .build();
        this.add(multiRadialBarChart);
    }
}




