package com.example.vsokoltsov.uprogress.views.authorizations;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class SignUpFragment extends Fragment {
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private EditText emailField;
    private EditText passwordField;
    private EditText passwordConfirmationField;
    private EditText nickField;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.sign_up_fragment, container, false);
        setIconsForFields();
        return fragmentView;
    }

    private void setIconsForFields() {
        emailField = (EditText) fragmentView.findViewById(R.id.emailField);
        passwordField = (EditText) fragmentView.findViewById(R.id.passwordField);
        passwordConfirmationField = (EditText) fragmentView.findViewById(R.id.passwordConfirmationField);
        nickField = (EditText) fragmentView.findViewById(R.id.nickField);

        Drawable emailImg = getContext().getResources().getDrawable( R.drawable.email);
        Drawable passwordImg= getContext().getResources().getDrawable( R.drawable.password);
        Drawable nickImg = getContext().getResources().getDrawable( R.drawable.user_nick);

        emailField.setCompoundDrawablesWithIntrinsicBounds( emailImg, null, null, null);
        passwordField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
        passwordConfirmationField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);
        nickField.setCompoundDrawablesWithIntrinsicBounds( nickImg, null, null, null);
    }
}
