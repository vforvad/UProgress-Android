package com.example.vsokoltsov.uprogress.presenters;

import com.example.vsokoltsov.uprogress.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.models.authorization.AuthenticationModel;
import com.example.vsokoltsov.uprogress.models.authorization.CurrentUser;
import com.example.vsokoltsov.uprogress.models.authorization.Token;
import com.example.vsokoltsov.uprogress.views.authorization.AuthorizationScreen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import rx.Observable;
import rx.plugins.RxJavaSchedulersTestRule;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.atLeast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vsokoltsov on 31.12.16.
 */

public class AuthorizationPresenterTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersTestRule = new RxJavaSchedulersTestRule();

    AuthenticationPresenterImpl presenter;

    @Mock
    AuthenticationModel model;

    @Mock
    AuthorizationScreen screen;

    @Mock
    PreferencesHelper preferencesHelper;

    @Before
    public void setUp() throws Exception {
        presenter = new AuthenticationPresenterImpl(model, screen, preferencesHelper);
    }

    @Test
    public void onSignInSubmitGetsCurrentUser() throws Exception {
        //Given
        String testToken = "Test";
        when(model.signInRequest()).thenReturn(Observable.just(new Token(testToken)));

        //When
        presenter.onSignInSubmit();

        //Then
        verify(preferencesHelper, times(1)).writeToken(testToken);
        verify(model, times(1)).getCurrentUser();
    }

    @Test
    public void onTokenAndUserRequestSuccessCallsScreenSuccess() throws Exception {
        //Given
        String testToken = "Test";
        when(model.signInRequest()).thenReturn(Observable.just(new Token(testToken)));
        when(model.getCurrentUser()).thenReturn(Observable.just(mock(CurrentUser.class)));

        //When
        presenter.onSignInSubmit();

        //Then
        verify(screen, times(1)).successResponse(any(CurrentUser.class));
    }
}
