package com.vsokoltsov.uprogress.statistics.ui.charts;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.statistics.model.StatisticsItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.ViewPortHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 20.01.17.
 */

public class HorizontalBarChartWrapper extends Fragment {
    private View fragmentView;
    private HorizontalBarChart barChart;
    List<StatisticsItem> items;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.horizontal_bar_chart, container, false);
        barChart = (HorizontalBarChart) fragmentView.findViewById(R.id.barChart);
        Bundle bundle = this.getArguments();
        items = bundle.getParcelableArrayList("directions_steps");
        configChart();
        setData();
        return fragmentView;
    }

    private void configChart() {

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);

        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(1000);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);
        XAxis xAxis = barChart.getXAxis();
        xAxis.setEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        xAxis.setYOffset(30.0f);
        xAxis.setDrawLabels(true);
        xAxis.setDrawAxisLine(false);
        xAxis.setDrawGridLines(false);
        xAxis.setDrawLimitLinesBehindData(false);
        xAxis.setTextSize(10.0f);
    }

    public void setData() {
        ArrayList<BarEntry> entries = new ArrayList<>();
        for(int i = 0; i < items.size(); i++) {
            StatisticsItem item = items.get(i);
            entries.add(new BarEntry(i, item.getValue().floatValue(), item.getLabel()));
        }
        barChart.animateY(1000);
        barChart.animateX(1000);
        BarDataSet dataset = new BarDataSet(entries, "# of Calls");
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);
        dataset.setValueFormatter(new IValueFormatter() {
            @Override
            public String getFormattedValue(float value, Entry entry, int dataSetIndex, ViewPortHandler viewPortHandler) {
                return entry.getData().toString() + " - " + entry.getY();
            }
        });

        BarData data = new BarData(dataset);
        barChart.setData(data);
        barChart.invalidate();
    }
}
