package direction_detail;

import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetail;
import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModel;
import com.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModelImpl;
import com.vsokoltsov.uprogress.direction_detail.model.steps.Step;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepRequest;
import com.vsokoltsov.uprogress.direction_detail.model.steps.StepResponse;
import com.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenter;
import com.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenterImpl;
import com.vsokoltsov.uprogress.direction_detail.view.DirectionDetailView;
import com.vsokoltsov.uprogress.directions_list.models.Direction;
import com.vsokoltsov.uprogress.directions_list.models.DirectionRequest;
import com.vsokoltsov.uprogress.directions_list.models.DirectionResponse;

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

public class DirectionDetailPreseterTest {
    String userId = "1";
    String directionId = "1";
    String stepId = "1";

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersTestRule = new RxJavaSchedulersTestRule();

    DirectionDetailPresenterImpl presenter;

    @Mock
    Throwable t;

    @Mock
    DirectionDetailModel model;
    @Mock
    DirectionDetailView view;
    @Mock
    DirectionDetail detail;
    @Mock
    DirectionResponse response;
    @Mock
    DirectionRequest request;
    @Mock
    Direction direction;

    @Before
    public void setUp() throws Exception {
        presenter = new DirectionDetailPresenterImpl(model, view);
    }

    @Test
    public void successLoadDirectionTest() {
        when(detail.getDirection()).thenReturn(direction);
        when(model.loadDirection(userId, directionId)).thenReturn(Observable.just(detail));

        presenter.loadDirection(userId, directionId);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).successResponse(direction);
        verify(view, times(1)).stopLoader();
    }

    @Test
    public void failedLoadDirectionTest() {
        when(detail.getDirection()).thenReturn(direction);
        when(model.loadDirection(userId, directionId)).thenReturn(Observable.error(t));

        presenter.loadDirection(userId, directionId);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).failureResponse(t);
        verify(view, times(1)).stopLoader();
    }

    @Mock
    Step step;

    @Mock
    StepRequest stepRequest;

    @Mock
    StepResponse stepResponse;

    @Test
    public void successUpdateStepTest() {
        when(stepResponse.getStep()).thenReturn(step);
        when(model.updateStep(userId, directionId, stepId, stepRequest)).thenReturn(Observable.just(stepResponse));

        presenter.updateStep(userId, directionId, stepId, stepRequest);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).successStepUpdate(step);
        verify(view, times(1)).stopLoader();
    }

    @Test
    public void failedUpdateStepTest() {
        when(stepResponse.getStep()).thenReturn(step);
        when(model.updateStep(userId, directionId, stepId, stepRequest)).thenReturn(Observable.error(t));

        presenter.updateStep(userId, directionId, stepId, stepRequest);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).failureStepUpdate(t);
        verify(view, times(1)).stopLoader();
    }

    @Test
    public void successReloadDirectionTest() {
        when(detail.getDirection()).thenReturn(direction);
        when(model.loadDirection(userId, directionId)).thenReturn(Observable.just(detail));

        presenter.reloadDirection(userId, directionId);

        verify(view, times(1)).onStartRefresh();
        verify(view, times(1)).successResponse(direction);
        verify(view, times(1)).onStopRefresh();
    }

    @Test
    public void failedReloadDirectionTest() {
        when(detail.getDirection()).thenReturn(direction);
        when(model.loadDirection(userId, directionId)).thenReturn(Observable.error(t));

        presenter.reloadDirection(userId, directionId);

        verify(view, times(1)).onStartRefresh();
        verify(view, times(1)).failureResponse(t);
        verify(view, times(1)).onStopRefresh();
    }

    @Test
    public void successDeleteStepTest() {
        int[] positions =  new int[1];
        when(stepResponse.getStep()).thenReturn(step);
        when(model.deleteStep(userId, directionId, stepId)).thenReturn(Observable.just(stepResponse));

        presenter.deleteStep(userId, directionId, stepId, positions);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).successDelete(step, positions);
        verify(view, times(1)).stopLoader();
    }

    @Test
    public void failedDeleteStepTest() {
        int[] positions =  new int[1];
        when(stepResponse.getStep()).thenReturn(step);
        when(model.deleteStep(userId, directionId, stepId)).thenReturn(Observable.error(t));

        presenter.deleteStep(userId, directionId, stepId, positions);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).failedDelete(t);
        verify(view, times(1)).stopLoader();
    }

    @Test
    public void successCreateStepTest() {
        when(stepResponse.getStep()).thenReturn(step);
        when(model.createStep(userId, directionId, stepRequest)).thenReturn(Observable.just(stepResponse));

        presenter.createStep(userId, directionId, stepRequest);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).successStepCreate(step);
        verify(view, times(1)).stopLoader();
    }

    @Test
    public void failedCreateStepTest() {
        when(model.createStep(userId, directionId, stepRequest)).thenReturn(Observable.error(t));

        presenter.createStep(userId, directionId, stepRequest);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).failedStepCreate(t);
        verify(view, times(1)).stopLoader();
    }
}
