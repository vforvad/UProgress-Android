package com.vsokoltsov.uprogress.reset_password;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.vsokoltsov.uprogress.R;
import com.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.vsokoltsov.uprogress.common.BaseApplication;
import com.vsokoltsov.uprogress.common.TabletActivity;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordBaseFragment extends Fragment implements Button.OnClickListener {
    private View fragmentView;
    private String token;
    private boolean isTablet;
    private ApplicationBaseActivity activity;
    private EditText passwordField;
    private EditText passwordConfirmationField;
    private Button resetPasswordButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.reset_password_fragment, container, false);
        setFields();
        activity = (ApplicationBaseActivity) getActivity();
        isTablet = getResources().getBoolean(R.bool.isTablet);
        if (isTablet) {
            try {
                ((TabletActivity) getActivity()).setToolbar();
                ((TabletActivity) getActivity()).getToolBar().setTitle(getResources().getString(R.string.reset_password));
            }
            catch(Exception e) {
                e.printStackTrace();
            }

        }
        else {
            activity.setTitle(getResources().getString(R.string.reset_password));
        }
        getActivity().invalidateOptionsMenu();
        setHasOptionsMenu(true);
        receiveToken();
        return fragmentView;
    }

    private void setButton() {
        resetPasswordButton = (Button) fragmentView.findViewById(R.id.resetPasswordButton);
        resetPasswordButton.setOnClickListener(this);
    }

    private void setFields() {
        passwordField = (EditText) fragmentView.findViewById(R.id.passwordField);
        passwordConfirmationField = (EditText) fragmentView.findViewById(R.id.passwordConfirmationField);

        Drawable passwordImg = ContextCompat.getDrawable(getContext(), R.drawable.password);

        passwordField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
        passwordConfirmationField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
    }

    private void receiveToken() {
        Bundle extras = getArguments();
        token = extras.getString("reset_token");
    }

    @Override
    public void onClick(View view) {

    }
}
