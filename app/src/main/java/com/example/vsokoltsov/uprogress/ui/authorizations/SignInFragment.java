package com.example.vsokoltsov.uprogress.ui.authorizations;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModelImpl;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModel;
import com.example.vsokoltsov.uprogress.presenters.AuthenticationPresenter;
import com.example.vsokoltsov.uprogress.presenters.AuthenticationPresenterImpl;
import com.example.vsokoltsov.uprogress.ui.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.view_holders.SignInViewHolder;
import com.example.vsokoltsov.uprogress.views.authorization.AuthorizationView;
import com.example.vsokoltsov.uprogress.views.SignInView;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class SignInFragment extends Fragment implements Button.OnClickListener {
    private View fragmentView;
    private ApplicationBaseActivity activity;
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
        final AuthorizationView view = new SignInView(viewHolder);
        presenter = new AuthenticationPresenterImpl(model, view);
        presenter.onCreate(this);
        return fragmentView;
    }

    @Override
    public void onClick(View v) {
        presenter.onSignInSubmit();
    }
}
