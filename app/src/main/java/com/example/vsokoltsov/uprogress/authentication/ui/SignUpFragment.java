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
import com.example.vsokoltsov.uprogress.authentication.models.AuthenticationModel;
import com.example.vsokoltsov.uprogress.authentication.models.AuthenticationModelImpl;
import com.example.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenter;
import com.example.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.authentication.view_holders.SignUpViewHolder;
import com.example.vsokoltsov.uprogress.authentication.views.SignUpView;
import com.example.vsokoltsov.uprogress.authentication.views.AuthorizationScreen;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class SignUpFragment extends Fragment implements Button.OnClickListener {
    private View fragmentView;
    public ApplicationBaseActivity activity;
    private AuthenticationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        activity = (ApplicationBaseActivity) getActivity();
        fragmentView = inflater.inflate(R.layout.sign_up_fragment, container, false);
        Button signUpButton = (Button) fragmentView.findViewById(R.id.signUpButton);
        signUpButton.setOnClickListener(this);

        final SignUpViewHolder viewHolder = new SignUpViewHolder(
                (EditText) fragmentView.findViewById(R.id.emailField),
                (EditText) fragmentView.findViewById(R.id.passwordField),
                (EditText) fragmentView.findViewById(R.id.passwordConfirmationField),
                (EditText) fragmentView.findViewById(R.id.nickField),
                signUpButton
        );
        // Set fields in viewHolder
        viewHolder.setFields(getContext());

        final AuthenticationModel model = new AuthenticationModelImpl(viewHolder);
        final AuthorizationScreen view = new SignUpView(viewHolder, activity);
        presenter = new AuthenticationPresenterImpl(model, view, new PreferencesHelper(getContext()));
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        presenter.onSignUpSubmit();
    }
}
