package org.coffeemine.app.spring.components;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.ChartBuilder;
import com.github.appreciated.apexcharts.config.builder.TitleSubtitleBuilder;
import com.github.appreciated.apexcharts.config.chart.Type;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.statistics.StatisticsCalculation;
import org.coffeemine.app.spring.view.View;

public class RadialBarChart extends View {

    private StatisticsCalculation statisticsCalculation;

    public RadialBarChart() {

        statisticsCalculation = new StatisticsCalculation();

        final var currentProject = NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get());
        final var currentSprint = NitriteDBProvider.getInstance().getCurrentSprint(currentProject);

        ApexCharts multiRadialBarChart = ApexChartsBuilder.get()
                .withTitle(TitleSubtitleBuilder.get()
                        .withText("Cost & Schedule Performance Index")
                        .build())
                .withChart(ChartBuilder.get()
                        .withType(Type.radialBar)
                        .build())
                .withSeries(statisticsCalculation.costPerformanceIndex(currentSprint), statisticsCalculation.schedulePerformanceIndex(currentSprint))
                .withLabels("Cost Performance", "Schedule Performance")
                .build();
        this.add(multiRadialBarChart);
    }
}




