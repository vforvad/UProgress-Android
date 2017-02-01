package attachment;

import android.content.Context;

import com.vsokoltsov.uprogress.attachment.model.AttachmentModel;
import com.vsokoltsov.uprogress.attachment.model.AttachmentResponse;
import com.vsokoltsov.uprogress.attachment.presenter.AttachmentPresenter;
import com.vsokoltsov.uprogress.attachment.presenter.AttachmentPresenterImpl;
import com.vsokoltsov.uprogress.attachment.view.AttachmentView;
import com.vsokoltsov.uprogress.authentication.models.Attachment;
import com.vsokoltsov.uprogress.authentication.presenters.AuthenticationPresenterImpl;
import com.vsokoltsov.uprogress.user.current.User;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import rx.Observable;
import rx.plugins.RxJavaSchedulersTestRule;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by vsokoltsov on 18.01.17.
 */

public class AttachmentPresenterTest {
    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    @Rule
    public RxJavaSchedulersTestRule rxJavaSchedulersTestRule = new RxJavaSchedulersTestRule();

    AttachmentPresenterImpl presenter;

    @Mock
    AttachmentModel model;

    @Mock
    AttachmentView view;

    @Mock
    User user;

    @Mock
    File file;

    MultipartBody.Part body;

    @Mock
    RequestBody attachableType;

    @Mock
    RequestBody attachableId;

    @Mock
    AttachmentResponse response;

    @Mock
    Attachment attachment;

    @Before
    public void setUp() throws Exception {
        presenter = new AttachmentPresenterImpl(model, view);
        RequestBody requestFile =
                RequestBody.create(MediaType.parse("multipart/form-data"), file);
        body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);;
    }

    @Test
    public void uploadImageTest() {
        when(response.getAttachment()).thenReturn(attachment);
        when(model.uploadImage(body, attachableType, attachableId)).thenReturn(Observable.just(response));

        presenter.uploadImage(body, attachableType, attachableId);

        verify(view, times(1)).startLoader();
        verify(view, times(1)).successUpload(attachment);
        verify(view, times(1)).stopLoader();
    }
}
