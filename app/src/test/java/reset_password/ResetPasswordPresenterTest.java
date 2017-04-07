package reset_password;

import com.vsokoltsov.uprogress.authentication.models.Token;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordModel;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordRequest;
import com.vsokoltsov.uprogress.reset_password.model.ResetPasswordResponse;
import com.vsokoltsov.uprogress.reset_password.presenter.ResetPasswordPresenter;
import com.vsokoltsov.uprogress.reset_password.presenter.ResetPasswordPresenterImpl;
import com.vsokoltsov.uprogress.reset_password.view.ResetPasswordView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import rx.Observable;
import rx.plugins.RxJavaSchedulersTestRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vsokoltsov on 07.04.17.
 */

public class ResetPasswordPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersTestRule = new RxJavaSchedulersTestRule();

    ResetPasswordPresenter presenter;

    @Mock
    ResetPasswordModel model;

    @Mock
    ResetPasswordView screen;

    @Mock
    ResetPasswordRequest request;

    @Before
    public void setUp() throws Exception {
        presenter = new ResetPasswordPresenterImpl(model, screen);
    }

    @Test
    public void callModelMethod() throws Exception {
        String message = "AAA";
        when(model.resetPassword(request)).thenReturn(Observable.just(new ResetPasswordResponse(message)));

        //When
        presenter.resetPassword(request);

        //Then
        verify(model, times(1)).resetPassword(request);
    }

    @Test
    public void onSuccessResetPassword() throws Exception {
        String message = "AAA";
        when(model.resetPassword(request)).thenReturn(Observable.just(new ResetPasswordResponse(message)));

        //When
        presenter.resetPassword(request);

        //Then
        verify(screen, times(1)).startLoader();
        verify(screen, times(1)).successReset(message);
        verify(screen, times(1)).stopLoader();
    }

    @Test
    public void onFailureResetPassword() throws Exception {
        Throwable t = new Throwable();
        when(model.resetPassword(request)).thenReturn(Observable.error(t));

        //When
        presenter.resetPassword(request);

        //Then
        verify(screen, times(1)).startLoader();
        verify(screen, times(1)).failedReset(t);
        verify(screen, times(1)).stopLoader();
    }
}
