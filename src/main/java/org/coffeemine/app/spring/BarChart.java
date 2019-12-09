package org.coffeemine.app.spring;

import com.github.appreciated.apexcharts.ApexCharts;
import com.github.appreciated.apexcharts.ApexChartsBuilder;
import com.github.appreciated.apexcharts.config.builder.*;
import com.github.appreciated.apexcharts.config.chart.Type;
import com.github.appreciated.apexcharts.config.plotoptions.builder.BarBuilder;
import com.github.appreciated.apexcharts.config.tooltip.builder.YBuilder;
import com.github.appreciated.apexcharts.config.yaxis.builder.TitleBuilder;
import com.github.appreciated.apexcharts.helper.Series;

public class BarChart extends View {

    public BarChart() {
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
                .withSeries(new Series<>("Planned Value", 44.0, 55.0, 57.0, 56.0, 61.0, 58.0, 63.0, 60.0, 66.0),
                        new Series<>("Actual Value", 76.0, 85.0, 101.0, 98.0, 87.0, 105.0, 91.0, 114.0, 94.0),
                        new Series<>("Earned Value", 35.0, 41.0, 36.0, 26.0, 45.0, 48.0, 52.0, 53.0, 41.0))
                .withYaxis(YAxisBuilder.get()
                        .withTitle(TitleBuilder.get()
                                .withText("$ (thousands)")
                                .build())
                        .build())
                .withXaxis(XAxisBuilder.get().withCategories("Sprint 1", "Sprint 2", "Sprint 3", "Sprint 4", "Sprint 5", "Sprint 6", "Sprint 7"
                        , "Sprint 8", "Sprint 9").build())
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



