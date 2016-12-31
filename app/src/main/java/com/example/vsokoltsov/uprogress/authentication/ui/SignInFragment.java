package com.example.vsokoltsov.uprogress.authentication.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.authentication.models.AuthenticationModelImpl;
import com.example.vsokoltsov.uprogress.authentication.models.AuthenticationModel;
import com.example.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenter;
import com.example.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.authentication.view_holders.SignInViewHolder;
import com.example.vsokoltsov.uprogress.authentication.views.AuthorizationScreen;
import com.example.vsokoltsov.uprogress.authentication.views.SignInView;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class SignInFragment extends Fragment implements Button.OnClickListener {
    private View fragmentView;
    public ApplicationBaseActivity activity;
    private EditText emailField;
    private EditText passwordField;
    private AuthenticationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.sign_in_fragment, container, false);
        Button button = (Button) fragmentView.findViewById(R.id.signInButton);
        button.setOnClickListener(this);
        final SignInViewHolder viewHolder = new SignInViewHolder(
                (EditText) fragmentView.findViewById(R.id.emailField),
                (EditText) fragmentView.findViewById(R.id.passwordField),
                button
        );
        // Set fields in viewHolder
        viewHolder.setFields(getContext());

        final AuthenticationModel model = new AuthenticationModelImpl(viewHolder);
        final AuthorizationScreen view = new SignInView(viewHolder, activity);
        presenter = new AuthenticationPresenterImpl(model, view, new PreferencesHelper(getContext()));
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        presenter.onSignInSubmit();
    }
}
