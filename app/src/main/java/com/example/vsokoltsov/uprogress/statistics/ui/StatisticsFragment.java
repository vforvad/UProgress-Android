package com.example.vsokoltsov.uprogress.statistics.ui;

import android.content.res.Configuration;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsInfo;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsItem;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsModel;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsModelImpl;
import com.example.vsokoltsov.uprogress.statistics.presenter.StatisticsPresenter;
import com.example.vsokoltsov.uprogress.statistics.presenter.StatisticsPresenterImpl;
import com.example.vsokoltsov.uprogress.statistics.ui.charts.BarChartWrapper;
import com.example.vsokoltsov.uprogress.statistics.ui.charts.HorizontalBarChartWrapper;
import com.example.vsokoltsov.uprogress.statistics.ui.charts.PieChartWrapper;
import com.example.vsokoltsov.uprogress.statistics.views.StatisticsView;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.HorizontalBarChart;
import com.github.mikephil.charting.charts.PieChart;
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
 * Created by vsokoltsov on 06.01.17.
 */

public class StatisticsFragment extends Fragment implements StatisticsView {
    private View fragmentView;
    private PieChart pieChart;
    private BarChart barChart;
    private StatisticsPresenter presenter;
    private boolean iconSwitcher = true;
    private int defaultChart = 1;
    private SeekBar mSeekBarX, mSeekBarY;
    private android.support.v4.app.FragmentManager fragmentManager;
    private BarChartWrapper barFragment;
    private PieChartWrapper pieFragment;
    private HorizontalBarChartWrapper horizontalBarChart;
    private static StatisticsInfo localStatistics;
    private int orientation;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.statistics_fragment, container, false);
        orientation = getResources().getConfiguration().orientation;
        User user = AuthorizationService.getInstance().getCurrentUser();
        StatisticsModel model = new StatisticsModelImpl();
        presenter = new StatisticsPresenterImpl(model, this);
        presenter.getStatistics(user.getNick());
        return fragmentView;
    }
    
    private void setBarChart() {
//        barChart.setOnChartValueSelectedListener(this);

        barChart.setDrawBarShadow(false);
        barChart.setDrawValueAboveBar(true);

        barChart.getDescription().setEnabled(false);

        // if more than 60 entries are displayed in the chart, no values will be
        // drawn
        barChart.setMaxVisibleValueCount(1000);

        // scaling can now only be done on x- and y-axis separately
        barChart.setPinchZoom(false);

        barChart.setDrawGridBackground(false);
        // barChart.setDrawYLabels(false);

//        IAxisValueFormatter xAxisFormatter = new DayAxisValueFormatter(barChart);



//        mSeekBarY.setProgress(50);
//        mSeekBarX.setProgress(12);

        // setting data
//        mSeekBarY.setProgress(50);
//        mSeekBarX.setProgress(12);
    }

    private void setPieChart() {
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.setExtraOffsets(5, 10, 5, 5);

        pieChart.setDragDecelerationFrictionCoef(0.95f);

//        pieChart.setCenterTextTypeface(mTfLight);
//        pieChart.setCenterText(generateCenterSpannableText());

        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.WHITE);
        pieChart.setTransparentCircleAlpha(110);

        pieChart.setHoleRadius(50f);
        pieChart.setTransparentCircleRadius(53f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the chart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);

        // pieChart.setUnit(" €");
        // pieChart.setDrawUnitsInChart(true);

        // add a selection listener
//        pieChart.setOnChartValueSelectedListener(getActivity());

//        setData(4, 100);

        pieChart.animateY(1400, Easing.EasingOption.EaseInOutQuad);
        // pieChart.spin(2000, 0, 360);

//        mSeekBarX.setOnSeekBarChangeListener(this);
//        mSeekBarY.setOnSeekBarChangeListener(this);
    }

    private void setData(StatisticsInfo statisticsInfo) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<StatisticsItem> items = statisticsInfo.getDirectionsSteps();
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

    @Override
    public void successLoadStatistics(StatisticsInfo statisticsInfo) {
        localStatistics = statisticsInfo;
        showChart();
    }

    private void showChart() {
        fragmentManager = getChildFragmentManager();
        Bundle bundle = new Bundle();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (iconSwitcher) {
            bundle.putParcelableArrayList("directions_steps", getStatisticsList());
            pieFragment = new PieChartWrapper();
            pieFragment.setArguments(bundle);
            fragmentTransaction.replace(R.id.chartsPlaceholder, pieFragment);
        }
        else {
            bundle.putParcelableArrayList("directions_steps", getStatisticsList());
            if (orientation == Configuration.ORIENTATION_PORTRAIT) {
                horizontalBarChart = new HorizontalBarChartWrapper();
                horizontalBarChart.setArguments(bundle);
                fragmentTransaction.replace(R.id.chartsPlaceholder, horizontalBarChart);
            }
            else {
                barFragment = new BarChartWrapper();
                barFragment.setArguments(bundle);
                fragmentTransaction.replace(R.id.chartsPlaceholder, barFragment);
            }

        }

        fragmentTransaction.commit();
    }

    @Override
    public void failedLoadStatistics(Throwable t) {

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.statistics_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.chartType:
                if (iconSwitcher) {
                    item.setIcon(R.drawable.bar_chart_icon);
                }
                else {
                    item.setIcon(R.drawable.pie_chart_icon);
                }
                iconSwitcher = !iconSwitcher;
                showChart();
                break;
            case R.id.directions:
                defaultChart = 1;
                showChart();
                break;
            case R.id.steps:
                defaultChart = 2;
                showChart();
                break;
            case R.id.direction_steps:
                defaultChart = 3;
                showChart();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private ArrayList<? extends Parcelable> getStatisticsList() {
        List<StatisticsItem> list = null;
        switch(defaultChart) {
            case 1:
                list = localStatistics.getDirections();
                break;
            case 2:
                list = localStatistics.getSteps();
                break;
            case 3:
                list = localStatistics.getDirectionsSteps();
                break;
        }
        return (ArrayList<? extends Parcelable>) list;
    }
}
