package directions_list;

import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionModel;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionsList;
import com.example.vsokoltsov.uprogress.directions_list.presenters.DirectionsListPresenter;
import com.example.vsokoltsov.uprogress.directions_list.presenters.DirectionsListPresenterImpl;
import com.example.vsokoltsov.uprogress.directions_list.views.DirectionsListView;
import com.example.vsokoltsov.uprogress.launch.LaunchPresenter;
import com.example.vsokoltsov.uprogress.user.current.User;

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
 * Created by vsokoltsov on 11.01.17.
 */

public class DirectionsListPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersTestRule = new RxJavaSchedulersTestRule();

    @Mock
    DirectionsListPresenterImpl presenter;

    @Mock
    DirectionsListView view;

    @Mock
    DirectionModel model;

    @Mock
    Direction direction;

    @Mock
    User user;

    @Mock
    DirectionsList list;

    @Before
    public void setUp() throws Exception {
        presenter = new DirectionsListPresenterImpl(view, model, user);
    }

    @Test
    public void getDirectionsList() {
        String userId = "1";
        int pageNumber = 1;

        when(user.getNick()).thenReturn(userId);
        when(model.getDirectionsList(userId, pageNumber)).thenReturn(Observable.just(list));

        presenter.loadDirections();

        verify(view, times(1)).successResponse(list);
    }
}
