package directions_list;

import com.example.vsokoltsov.uprogress.directions_list.models.Direction;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionModel;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionRequest;
import com.example.vsokoltsov.uprogress.directions_list.models.DirectionResponse;
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
    String userId = "1";
    int pageNumber = 1;
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
        when(user.getNick()).thenReturn(userId);
    }

    @Test
    public void getDirectionsList() {
        when(model.getDirectionsList(userId, pageNumber)).thenReturn(Observable.just(list));

        presenter.loadDirections();

        verify(view, times(1)).startLoader();
        verify(view, times(1)).successResponse(list);
        verify(view, times(1)).stopLoader();
    }

    @Test
    public void refreshDirectionsListTest() {
        when(model.getDirectionsList(userId, pageNumber)).thenReturn(Observable.just(list));

        presenter.refreshList();

        verify(view ,times(1)).startRefreshing();
        verify(view ,times(1)).refreshList(list);
        verify(view ,times(1)).stopRefreshing();
    }

    @Test
    public void loadMoreDirectionsTest() {
        pageNumber++;
        when(model.getDirectionsList(userId, pageNumber)).thenReturn(Observable.just(list));

        presenter.loadMoreDirections();

        verify(view ,times(1)).startFooterLoader();
        verify(view ,times(1)).successResponse(list);
        verify(view ,times(1)).stopFooterLoader();
    }

    @Mock DirectionResponse response;
    @Mock DirectionRequest request;

    @Test
    public void successCreateDirectionTest() {
        when(response.getDirection()).thenReturn(direction);
        when(model.createDirection(userId, request)).thenReturn(Observable.just(response));

        presenter.createDirection(userId, request);

        verify(view ,times(1)).startLoader();
        verify(view ,times(1)).successDirectionCreation(direction);
        verify(view ,times(1)).stopLoader();
    }

    @Test
    public void failedCreateDirectionTest() {
        Throwable t = new Throwable();
        when(model.createDirection(userId, request)).thenReturn(Observable.error(t));

        presenter.createDirection(userId, request);
        verify(view ,times(1)).startLoader();
        verify(view ,times(1)).failedDirectionCreation(t);
        verify(view ,times(1)).stopLoader();
    }
}
