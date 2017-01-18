package com.example.vsokoltsov.uprogress.statistics;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.SeekBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsInfo;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsItem;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsModel;
import com.example.vsokoltsov.uprogress.statistics.model.StatisticsModelImpl;
import com.example.vsokoltsov.uprogress.statistics.presenter.StatisticsPresenter;
import com.example.vsokoltsov.uprogress.statistics.presenter.StatisticsPresenterImpl;
import com.example.vsokoltsov.uprogress.statistics.views.StatisticsView;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class StatisticsFragment extends Fragment implements StatisticsView {
    private View fragmentView;
    private PieChart pieChart;
    private StatisticsPresenter presenter;
    private boolean iconSwitcher = true;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.statistics_fragment, container, false);
        pieChart = (PieChart) fragmentView.findViewById(R.id.pieChart);
        User user = AuthorizationService.getInstance().getCurrentUser();
        StatisticsModel model = new StatisticsModelImpl();
        presenter = new StatisticsPresenterImpl(model, this);
        presenter.getStatistics(user.getNick());
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
        return fragmentView;
    }

    @Override
    public void successLoadStatistics(StatisticsInfo statisticsInfo) {
        List<StatisticsItem> items = statisticsInfo.getDirectionsSteps();
        ArrayList<PieEntry> entries = new ArrayList<PieEntry>();
        ArrayList<Integer> colors = new ArrayList<Integer>();
        for (int i = 0; i < items.size(); i ++) {
            StatisticsItem item = items.get(i);
            entries.add(new PieEntry(item.getValue().floatValue(), item.getLabel()));
            int color = ColorTemplate.rgb(item.getColor());
            colors.add(color);
        }

        PieDataSet dataSet = new PieDataSet(entries, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
        PieData data = new PieData(dataSet);
        pieChart.setData(data);
        // undo all highlights
        pieChart.highlightValues(null);

        pieChart.invalidate();
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
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
