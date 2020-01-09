package org.coffeemine.app.spring.components;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.tooltip.builder.YBuilder;
import com.github.appreciated.apexcharts.config.yaxis.builder.TitleBuilder;
import com.github.appreciated.apexcharts.helper.Series;
import org.coffeemine.app.spring.auth.CurrentUser;
import org.coffeemine.app.spring.db.NitriteDBProvider;
import org.coffeemine.app.spring.statistics.StatisticsCalculation;
import org.coffeemine.app.spring.view.View;

public class BarChart extends View {

    private StatisticsCalculation statisticsCalculation;

    public BarChart() {

        statisticsCalculation = new StatisticsCalculation();

        final var currentProject = NitriteDBProvider.getInstance().getCurrentProject(CurrentUser.get());
        final var currentSprint = NitriteDBProvider.getInstance().getCurrentSprint(currentProject);

        ApexCharts barChart = ApexChartsBuilder.get()
                .withChart(ChartBuilder.get()
                        .withType(Type.bar)
                        .build())
                .withPlotOptions(PlotOptionsBuilder.get()
                        .withBar(BarBuilder.get()
                                .withHorizontal(false)
                                .withColumnWidth("55%")
                                .build())
                        .build())
                .withDataLabels(DataLabelsBuilder.get()
                        .withEnabled(false).build())
                .withStroke(StrokeBuilder.get()
                        .withShow(true)
                        .withWidth(2.0)
                        .withColors("transparent")
                        .build())
                .withSeries(new Series<>("Planned Value", statisticsCalculation.plannedValueSprint(currentSprint)),
                        new Series<>("Actual Value", statisticsCalculation.actualValueSprint(currentSprint)),
                        new Series<>("Earned Value", statisticsCalculation.earnedValue(currentSprint)))
                .withYaxis(YAxisBuilder.get()
                        .withTitle(TitleBuilder.get()
                                .withText("SEK")
                                .build())
                        .build())
                .withXaxis(XAxisBuilder.get().withCategories("Current Sprint").build())
                .withFill(FillBuilder.get()
                        .withOpacity(1.0).build())
                .withTooltip(TooltipBuilder.get()
                        .withY(YBuilder.get()
                                .withFormatter("function (val) {\n" + // Formatter currently not yet working
                                        "return \"$ \" + val + \" thousands\"\n" +
                                        "}").build())
                        .build())
                .build();
        this.add(barChart);
    }



}



