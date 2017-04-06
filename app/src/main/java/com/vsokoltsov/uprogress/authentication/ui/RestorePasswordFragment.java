package com.vsokoltsov.uprogress.authentication.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModel;
import com.vsokoltsov.uprogress.authentication.models.AuthenticationModelImpl;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.ErrorHandler;
import com.vsokoltsov.uprogress.common.helpers.PreferencesHelper;

/**
 * Created by vsokoltsov on 06.04.17.
 */

public class RestorePasswordFragment extends Fragment {
    private View fragmentView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.restore_password, container, false);
        return fragmentView;
    }

}
