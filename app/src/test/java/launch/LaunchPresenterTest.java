package launch;

import com.example.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.example.vsokoltsov.uprogress.common.helpers.PreferencesHelper;
import com.example.vsokoltsov.uprogress.launch.LaunchPresenter;
import com.example.vsokoltsov.uprogress.user.current.CurrentUser;
import com.example.vsokoltsov.uprogress.user.current.CurrentUserModel;
import com.example.vsokoltsov.uprogress.user.current.CurrentUserView;

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
 * Created by vsokoltsov on 02.01.17.
 */

public class LaunchPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersTestRule = new RxJavaSchedulersTestRule();

    @Mock
    PreferencesHelper preferencesHelper;

    @Mock
    LaunchPresenter presenter;

    @Mock
    CurrentUserView screen;

    @Mock
    CurrentUserModel model;

    @Mock
    CurrentUser currentUser;

    @Before
    public void setUp() throws Exception {
        presenter = new LaunchPresenter(screen, model);
    }

    @Test
    public void returnCurrentUser() {
        when(model.getCurrentUser()).thenReturn(Observable.just(mock(CurrentUser.class)));

        presenter.getCurrentUser();

        verify(screen, times(1)).currentUserReceived(any(CurrentUser.class));
    }

    @Test
    public void failedToReceiveCurrentUser() throws Exception {
        Throwable t = new Throwable();

        when(model.getCurrentUser()).thenReturn(Observable.error(t));

        presenter.getCurrentUser();

        verify(screen, times(1)).currentUserFailedToReceive(t);
    }

    @Test
    public void callTransitionToList() {
        when(model.getCurrentUser()).thenReturn(Observable.just(currentUser));

        presenter.getCurrentUser();

        verify(screen, times(1)).currentUserReceived(currentUser);
    }
}
