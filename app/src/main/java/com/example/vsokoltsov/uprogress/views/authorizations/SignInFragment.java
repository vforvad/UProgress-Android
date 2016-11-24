package com.example.vsokoltsov.uprogress.views.authorizations;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.interfaces.UserApi;
import com.example.vsokoltsov.uprogress.models.authorization.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.services.ErrorResponse;
import com.example.vsokoltsov.uprogress.utils.ApiRequester;
import com.example.vsokoltsov.uprogress.utils.RetrofitException;
import com.example.vsokoltsov.uprogress.views.ApplicationBaseActivity;

import java.io.IOException;

import retrofit2.Retrofit;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by vsokoltsov on 22.11.16.
 */

public class SignInFragment extends Fragment implements Button.OnClickListener {
    private View fragmentView;
    private ApplicationBaseActivity activity;
    private EditText emailField;
    private EditText passwordField;

    static
    {
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        activity = (ApplicationBaseActivity) getActivity();

        fragmentView = inflater.inflate(R.layout.sign_in_fragment, container, false);
        setIconsForFields();
        Button signInButton = (Button) fragmentView.findViewById(R.id.signInButton);
        signInButton.setOnClickListener(this);
        return fragmentView;
    }

    private void setIconsForFields() {
        emailField = (EditText) fragmentView.findViewById(R.id.emailField);
        passwordField = (EditText) fragmentView.findViewById(R.id.passwordField);

        Drawable emailImg = getContext().getResources().getDrawable( R.drawable.email);
        Drawable passwordImg= getContext().getResources().getDrawable( R.drawable.password);

        emailField.setCompoundDrawablesWithIntrinsicBounds( emailImg, null, null, null);
        passwordField.setCompoundDrawablesWithIntrinsicBounds( passwordImg, null, null, null);

    }

    @Override
    public void onClick(View v) {
        SignInRequest user = new SignInRequest(emailField.getText().toString(),
                passwordField.getText().toString());
        Retrofit retrofit = ApiRequester.getInstance().getRestAdapter();
        UserApi service = retrofit.create(UserApi.class);
        service.signIn(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Token>() {
                    @Override
                    public void onCompleted() {
//                        activity.dismissProgress();
                        Log.d("tag", "request completed");
                    }

                    @Override
                    public void onError(Throwable e) {
                        try {
                            handleErrors(e);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }

                    @Override
                    public void onNext(Token token) {
                        successAuth(token);
                    }
                });
//        activity.showProgress(R.string.sign_in_action);

    }

    private void  successAuth(Token token) {
        String str = token.getToken();
        ApiRequester.getInstance().setToken(token.getToken());
        activity.currentUserRequest();
    }

    private void handleErrors(Throwable t) throws IOException {
        RetrofitException error = (RetrofitException) t;
        t.printStackTrace();
        ErrorResponse errors = error.getErrorBodyAs(ErrorResponse.class);
        emailField.setError(errors.getFullErrorMessage("email"));
        passwordField.setError(errors.getFullErrorMessage("password"));

//        activity.dismissProgress();
    }
}
