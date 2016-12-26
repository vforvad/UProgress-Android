package com.example.vsokoltsov.uprogress.ui.authorizations;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.api.UserApi;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModel;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModelImpl;
import com.example.vsokoltsov.uprogress.models.authorization.SignUp.SignUpRequest;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.presenters.AuthenticationPresenter;
import com.example.vsokoltsov.uprogress.presenters.AuthenticationPresenterImpl;
import com.example.vsokoltsov.uprogress.services.ErrorResponse;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.utils.RetrofitException;
import com.example.vsokoltsov.uprogress.ui.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.view_holders.SignInViewHolder;
import com.example.vsokoltsov.uprogress.view_holders.SignUpViewHolder;
import com.example.vsokoltsov.uprogress.views.SignInView;
import com.example.vsokoltsov.uprogress.views.SignUpView;
import com.example.vsokoltsov.uprogress.views.authorization.AuthorizationView;

import java.io.IOException;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class SignUpFragment extends Fragment implements Button.OnClickListener {
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private EditText emailField;
    private EditText passwordField;
    private EditText passwordConfirmationField;
    private EditText nickField;
    private AuthenticationPresenter presenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        final AuthorizationView view = new SignUpView(viewHolder);
        presenter = new AuthenticationPresenterImpl(model, view);
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

    @Override
    public void onClick(View v) {
        presenter.onSignUpSubmit();
    }

    private void handleErrors(Throwable t) throws IOException {
        RetrofitException error = (RetrofitException) t;
        ErrorResponse errors = error.getErrorBodyAs(ErrorResponse.class);
        emailField.setError(errors.getFullErrorMessage("email"));
        passwordField.setError(errors.getFullErrorMessage("password"));
        passwordConfirmationField.setError(errors.getFullErrorMessage("password_confirmation"));
        nickField.setError(errors.getFullErrorMessage("nick"));
    }

    private void  successAuth(Token token) {
        String str = token.getToken();
        Token.writeToken(token.getToken());
        activity.currentUserRequest();
    }
}
