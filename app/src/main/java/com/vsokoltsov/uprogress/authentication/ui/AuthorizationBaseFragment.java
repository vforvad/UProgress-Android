package com.vsokoltsov.uprogress.authentication.ui;

import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.adapters.AuthorizationViewPagerAdapter;
import com.vsokoltsov.uprogress.common.utils.SlidingTabLayout;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class AuthorizationBaseFragment extends Fragment {
    private View fragmentView;
    private ApplicationBaseActivity activity;

    private android.support.v4.app.FragmentManager fragmentManager;
    private String action;

    private FragmentTabHost mTabHost;

    //Sliding tab elements
    private ViewPager pager;
    private AuthorizationViewPagerAdapter adapter;
    private SlidingTabLayout tabs;
    private CharSequence Titles[];
    private List<String> titles = new ArrayList<String>();
    private int Numboftabs =3;
    private Resources resources;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.authorization_base_fragment, container, false);
        resources = getResources();
        setTitles();
        defineCurrentTab();
        setSlidingTabs();
        return fragmentView;
    }

    private void setTitles() {
        String signIn = resources.getString(R.string.sign_in); //resources.getString(R.string.nav_sign_in);
        String signUp = resources.getString(R.string.sign_up); //resources.getString(R.string.nav_sign_up);
        String restorePassword = resources.getString(R.string.restore_password);
        titles.add(signIn);
        titles.add(signUp);
        titles.add(restorePassword);
    }

    private void defineCurrentTab() {
        Bundle extras = getArguments();
        action = extras.getString("action");
    }

    private void setSlidingTabs() {
        Resources resources = getResources();
        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        adapter =  new AuthorizationViewPagerAdapter(getFragmentManager(), titles, Numboftabs);

        // Assigning ViewPager View and setting the adapter
        pager = (ViewPager) fragmentView.findViewById(R.id.pager);
        pager.setAdapter(adapter);
        Resources res = getResources();

        if (action.equals("sign_in")) {
            pager.setCurrentItem(0);
            activity.setTitle(titles.get(0));
        }
        else if (action.equals("sign_up")) {
            pager.setCurrentItem(1);
            activity.setTitle(titles.get(1));
        }

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) fragmentView.findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width
        tabs.setTabsFontSize(12);
        tabs.setBackground(new ColorDrawable(getResources().getColor(R.color.colorPrimary)));
        tabs.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener(){
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                String title = titles.get(position);
                activity.getSupportActionBar().setTitle(title);
            }
        });

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(pager);
    }
}
