package authorization;

import com.example.vsokoltsov.uprogress.authentication.models.SignIn.SignInRequest;
import com.example.vsokoltsov.uprogress.authentication.models.SignUp.SignUpRequest;
import com.example.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.example.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.authentication.models.AuthenticationModel;
import com.example.vsokoltsov.uprogress.user.current.CurrentUser;
import com.example.vsokoltsov.uprogress.authentication.models.Token;
import com.example.vsokoltsov.uprogress.authentication.views.AuthorizationScreen;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import rx.Observable;
import rx.plugins.RxJavaSchedulersTestRule;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
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
    SignInRequest request;

    @Mock
    SignUpRequest signUpRequest;

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
        when(model.signInRequest(request)).thenReturn(Observable.just(new Token(testToken)));

        //When
        presenter.onSignInSubmit(request);

        //Then
        verify(preferencesHelper, times(1)).writeToken(testToken);
        verify(model, times(1)).getCurrentUser();
    }

    @Test
    public void onTokenAndUserRequestSuccessCallsScreenSuccess() throws Exception {
        //Given
        String testToken = "Test";
        when(model.signInRequest(request)).thenReturn(Observable.just(new Token(testToken)));
        when(model.getCurrentUser()).thenReturn(Observable.just(mock(CurrentUser.class)));

        //When
        presenter.onSignInSubmit(request);

        //Then
        verify(screen, times(1)).successResponse(any(CurrentUser.class));
    }

    @Test
    public void onFailureSignIn() throws Exception {
        Throwable t = new Throwable();
        when(model.signInRequest(request)).thenReturn(Observable.error(t));

        presenter.onSignInSubmit(request);

        verify(screen, times(1)).failedResponse(t);
    }

    @Test
    public void onSignUpSubmitGetsCurrentUser() throws Exception {
        String testToken = "Test";
        when(model.signUpRequest(signUpRequest)).thenReturn(Observable.just(new Token(testToken)));

        //When
        presenter.onSignUpSubmit(signUpRequest);

        //Then
        verify(preferencesHelper, times(1)).writeToken(testToken);
        verify(model, times(1)).getCurrentUser();
    }

    @Test
    public void onSuccessSignUpCallCurrentUser() throws Exception {
        //Given
        String testToken = "Test";
        when(model.signUpRequest(signUpRequest)).thenReturn(Observable.just(new Token(testToken)));
        when(model.getCurrentUser()).thenReturn(Observable.just(mock(CurrentUser.class)));

        //When
        presenter.onSignUpSubmit(signUpRequest);

        //Then
        verify(screen, times(1)).successResponse(any(CurrentUser.class));
    }

    @Test
    public void onFailureSignUp() throws Exception {
        Throwable t = new Throwable();
        when(model.signUpRequest(signUpRequest)).thenReturn(Observable.error(t));

        presenter.onSignUpSubmit(signUpRequest);

        verify(screen, times(1)).failedResponse(t);
    }
}
