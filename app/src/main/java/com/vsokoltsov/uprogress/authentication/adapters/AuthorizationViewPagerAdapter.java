package com.vsokoltsov.uprogress.authentication.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.vsokoltsov.uprogress.authentication.ui.SignInFragment;
import com.vsokoltsov.uprogress.authentication.ui.SignUpFragment;

import java.util.List;

/**
 * Created by vsokoltsov on 02.07.16.
 */
public class AuthorizationViewPagerAdapter extends FragmentStatePagerAdapter {
    List<String> Titles; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public AuthorizationViewPagerAdapter(FragmentManager fm, List<String> mTitles, int mNumbOfTabsumb) {
        super(fm);

        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;

    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        if(position == 0) // if the position is 0 we are returning the First tab
        {
            SignInFragment tab1 = new SignInFragment();
            return tab1;
        }
        else             // As we are having 2 tabs if the position is now 0 it must be 1 so we are returning second tab
        {
            SignUpFragment tab2 = new SignUpFragment();
            return tab2;
        }

    }

    // This method return the titles for the Tabs in the Tab Strip

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles.get(position);
    }

    // This method return the Number of tabs for the tabs Strip

    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
