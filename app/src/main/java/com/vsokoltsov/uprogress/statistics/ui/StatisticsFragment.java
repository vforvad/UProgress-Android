package com.vsokoltsov.uprogress.statistics.ui;

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

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.ErrorHandler;
import com.vsokoltsov.uprogress.statistics.model.StatisticsInfo;
import com.vsokoltsov.uprogress.statistics.model.StatisticsItem;
import com.vsokoltsov.uprogress.statistics.model.StatisticsModel;
import com.vsokoltsov.uprogress.statistics.model.StatisticsModelImpl;
import com.vsokoltsov.uprogress.statistics.presenter.StatisticsPresenter;
import com.vsokoltsov.uprogress.statistics.presenter.StatisticsPresenterImpl;
import com.vsokoltsov.uprogress.statistics.ui.charts.BarChartWrapper;
import com.vsokoltsov.uprogress.statistics.ui.charts.HorizontalBarChartWrapper;
import com.vsokoltsov.uprogress.statistics.ui.charts.PieChartWrapper;
import com.vsokoltsov.uprogress.statistics.views.StatisticsView;
import com.vsokoltsov.uprogress.user.current.User;
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
    private BaseApplication baseApplication;
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
    private ApplicationBaseActivity activity;
    private int orientation;
    private ErrorHandler errorHandler;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        baseApplication = ((BaseApplication) getActivity().getApplicationContext());
        fragmentView = inflater.inflate(R.layout.statistics_fragment, container, false);
        errorHandler = new ErrorHandler(getActivity());
        activity = (ApplicationBaseActivity) getActivity();
        activity.setTitle(getResources().getString(R.string.statistiscs_title));
        orientation = getResources().getConfiguration().orientation;
        User user = AuthorizationService.getInstance().getCurrentUser();
        StatisticsModel model = new StatisticsModelImpl(getActivity().getApplicationContext());
        presenter = new StatisticsPresenterImpl(model, this);
        presenter.getStatistics(user.getNick());
        return fragmentView;
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
        errorHandler.showMessage(t);
    }

    @Override
    public void startLoader() {
        activity.startProgressBar();
    }

    @Override
    public void stopLoader() {
        activity.stopProgressBar();
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
