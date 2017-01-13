package direction_detail;

import com.example.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.example.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModel;
import com.example.vsokoltsov.uprogress.direction_detail.model.DirectionDetailModelImpl;
import com.example.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenter;
import com.example.vsokoltsov.uprogress.direction_detail.presenter.DirectionDetailPresenterImpl;
import com.example.vsokoltsov.uprogress.direction_detail.view.DirectionDetailView;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import rx.plugins.RxJavaSchedulersTestRule;

/**
 * Created by vsokoltsov on 13.01.17.
 */

public class DirectionDetailPreseterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersTestRule = new RxJavaSchedulersTestRule();

    DirectionDetailPresenterImpl presenter;
    DirectionDetailModel model;

    @Mock DirectionDetailView view;

    @Before
    public void setUp() throws Exception {
        model = new DirectionDetailModelImpl();
        presenter = new DirectionDetailPresenterImpl(model, view);
    }

    @Test
    public void loadDirectionTest() {

    }

    @Test
    public void successUpdateStepTest() {

    }

    @Test
    public void failedUpdateStepTest() {

    }

    @Test
    public void reloadDirectionTest() {

    }

    @Test
    public void successDeleteStepTest() {

    }

    @Test
    public void failedDeleteStepTest() {

    }

    @Test
    public void successCreateStepTest() {

    }

    @Test
    public void failedCreateStepTest() {

    }
}
