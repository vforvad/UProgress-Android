package user;

import com.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenterImpl;
import com.vsokoltsov.uprogress.user.current.User;
import com.vsokoltsov.uprogress.user.current.UserRequest;
import com.vsokoltsov.uprogress.user.current.UserResponse;
import com.vsokoltsov.uprogress.user.models.UserModel;
import com.vsokoltsov.uprogress.user.presenters.UserProfilePresenterImpl;
import com.vsokoltsov.uprogress.user.views.UserProfileView;

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
 * Created by vsokoltsov on 13.01.17.
 */

public class UserPresenterTest {
    String userId = "1";
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersTestRule = new RxJavaSchedulersTestRule();

    UserProfilePresenterImpl presenter;

    @Mock Throwable t;
    @Mock UserModel model;
    @Mock UserProfileView view;
    @Mock UserResponse userResponse;
    @Mock UserRequest userRequest;
    @Mock User user;

    @Before
    public void setUp() throws Exception {
        presenter = new UserProfilePresenterImpl(view, model);
    }

    @Test
    public void successUserProfileUpdate() {
        when(userResponse.getUser()).thenReturn(user);
        when(model.updateUser(userId, userRequest)).thenReturn(Observable.just(userResponse));

        presenter.updateUser(userId, userRequest);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).successUpdate(user);
        verify(view, times(1)).stopLoader();
    }

    @Test
    public void failedUserProfileUpdate() {

    }
}
