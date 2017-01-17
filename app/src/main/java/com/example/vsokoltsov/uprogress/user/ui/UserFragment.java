package com.example.vsokoltsov.uprogress.user.ui;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Config;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.attachment.model.AttachmentModelImpl;
import com.example.vsokoltsov.uprogress.attachment.presenter.AttachmentPresenterImpl;
import com.example.vsokoltsov.uprogress.attachment.view.AttachmentView;
import com.example.vsokoltsov.uprogress.authentication.models.Attachment;
import com.example.vsokoltsov.uprogress.authentication.models.AuthorizationService;
import com.example.vsokoltsov.uprogress.common.ApplicationBaseActivity;
import com.example.vsokoltsov.uprogress.common.AttachmentConfig;
import com.example.vsokoltsov.uprogress.common.services.ErrorResponse;
import com.example.vsokoltsov.uprogress.common.utils.RetrofitException;
import com.example.vsokoltsov.uprogress.direction_detail.popup.PopupInterface;
import com.example.vsokoltsov.uprogress.user.adapters.UserInfoListAdapter;
import com.example.vsokoltsov.uprogress.common.helpers.ImageHelper;
import com.example.vsokoltsov.uprogress.navigation.NavigationDrawer;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.example.vsokoltsov.uprogress.user.current.UserItem;
import com.example.vsokoltsov.uprogress.user.current.UserRequest;
import com.example.vsokoltsov.uprogress.user.models.UserModel;
import com.example.vsokoltsov.uprogress.user.models.UserModelImpl;
import com.example.vsokoltsov.uprogress.user.popup.UserFormPopup;
import com.example.vsokoltsov.uprogress.user.presenters.UserProfilePresenter;
import com.example.vsokoltsov.uprogress.user.presenters.UserProfilePresenterImpl;
import com.example.vsokoltsov.uprogress.user.views.UserProfileView;
import com.squareup.picasso.Picasso;

import org.solovyev.android.views.llm.LinearLayoutManager;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vsokoltsov on 06.01.17.
 */

public class UserFragment extends Fragment implements PopupInterface, UserProfileView, AttachmentView {
    private View fragmentView;
    private User user;
    private ApplicationBaseActivity activity;
    private ImageView userAvatar;
    private List<UserItem> list = new ArrayList<UserItem>();
    private NavigationDrawer navigationDrawer;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private UserFormPopup popup;
    private ProgressBar progressBar;
    UserProfilePresenter presenter;
    AttachmentPresenterImpl attachmentPresenter;
    AttachmentModelImpl attachmentModel;
    private UserInfoListAdapter adapter;
    CollapsingToolbarLayout layout;
    Uri imageUri;
    private static final int CAMERA_REQUEST = 1888;
    private static final int GALERY_REQUEST = 444;
    public String photoFileName = "photo.jpg";
    public final String APP_TAG = "UProgress";

    public static final int MEDIA_TYPE_IMAGE = 1;
    private File selectImageFile = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        fragmentView = inflater.inflate(R.layout.user_fragment, container, false);
        activity = (ApplicationBaseActivity) getActivity();
        UserModel model = new UserModelImpl();
        attachmentModel = new AttachmentModelImpl();
        presenter = new UserProfilePresenterImpl(this, model);
        attachmentPresenter = new AttachmentPresenterImpl(attachmentModel, this);
        loadList();
        loadUserImage();
        setElements();
        setToolbar();
        setNavigationDrawer();
        setProgressBar();
        return fragmentView;
    }


    public void setUser(User user) {
        this.user = user;
    }

    private void setElements() {
        layout = (CollapsingToolbarLayout) fragmentView.findViewById(R.id.collapsing_toolbar);
//        layout.setTitle(user.getCorrectName());
        FloatingActionButton floatingActionButton = (FloatingActionButton) fragmentView.findViewById(R.id.addDirection);
        floatingActionButton.setImageDrawable(getResources().getDrawable(R.drawable.edit_icon));
        floatingActionButton.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.price_green)));
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popup = new UserFormPopup();
                popup.setPopupInterface(UserFragment.this);
                popup.setUser(user);
                popup.show(getActivity().getFragmentManager(), "form");
            }
        });

    }

    private void loadUserImage() {
        userAvatar = (ImageView) fragmentView.findViewById(R.id.userAvatar);
        user = AuthorizationService.getInstance().getCurrentUser();
        ImageHelper.getInstance(getContext()).setUserImage(user, userAvatar, R.drawable.empty_user);
    }

    private void loadList() {
        RecyclerView rv = (RecyclerView) fragmentView.findViewById(R.id.recyclerView);
        LinearLayoutManager llm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(llm);

        adapter = new UserInfoListAdapter(list);
        adapter.setList(user);
        rv.setAdapter(adapter);
    }

    private void setToolbar() {
        toolbar = (Toolbar) fragmentView.findViewById(R.id.toolbar_actionbar);
        ((ApplicationBaseActivity) getActivity()).setToolbar(toolbar);
    }

    private void setNavigationDrawer() {
        activity.setLeftNavigationBar();
    }

    private void setProgressBar() {
        progressBar =  (ProgressBar) fragmentView.findViewById(R.id.progressBar);
    }

    @Override
    public void successPopupOperation(Object obj) {
        UserRequest request = (UserRequest) obj;
        presenter.updateUser(user.getNick(), request);
    }

    @Override
    public void failedPopupOperation(Throwable t) {

    }

    @Override
    public void successUpdate(User user) {
        this.user = user;
        AuthorizationService.getInstance().setCurrentUser(user);
        popup.dismiss();
        layout.setTitle(user.getCorrectName());
        adapter.setList(user);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void failedUpdate(Throwable t) {
        RetrofitException error = (RetrofitException) t;
        ErrorResponse errors = null;
        try {
            errors = error.getErrorBodyAs(ErrorResponse.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        popup.firstNameField.setError(errors.getFullErrorMessage("first_name"));
        popup.secondNameField.setError(errors.getFullErrorMessage("last_name"));
        popup.emailField.setError(errors.getFullErrorMessage("email"));
    }

    @Override
    public void startLoader() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void stopLoader() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.user_profile, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.photo:
                Intent camerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                camerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
                getActivity().startActivityForResult(camerIntent, CAMERA_REQUEST);
                break;
            case R.id.collection:
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,
                        "Select Picture"), GALERY_REQUEST);
                break;
            default: break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if(requestCode == CAMERA_REQUEST){
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                selectImageFile = new File(takenPhotoUri.getPath());
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
//                userAvatar.setImageBitmap(takenImage);
            }
            else if (requestCode == GALERY_REQUEST) {
                Uri takenPhotoUri = data.getData();
                Bitmap bitmap = null;
                String path = getPath(takenPhotoUri);
                selectImageFile = new File(path);

//                if (file.exists()) {
//                    try {
//                        bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), takenPhotoUri);
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
////                    userAvatar.setImageBitmap(bitmap);
//                }
            }
            if (selectImageFile != null) {
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), selectImageFile);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", selectImageFile.getName(), requestFile);
                RequestBody attachmentableId = RequestBody.create(MediaType.parse("text/plain"), Integer.toString(user.getId()));
                RequestBody attachmentableType = RequestBody.create(MediaType.parse("text/plain"), "User");
                attachmentPresenter.uploadImage(body, attachmentableType, attachmentableId);
            }
        }

    }

    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable("file_uri", imageUri);
    }

    public void onRestoreInstanceState(Bundle savedInstanceState) {
        imageUri = savedInstanceState.getParcelable("file_uri");
    }

    // Returns the Uri for a photo stored on disk given the fileName
    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }

    // Returns true if external storage for photos is available
    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    @Override
    public void successUpload(Attachment attachment) {
        ImageHelper.getInstance(getContext()).load(selectImageFile, userAvatar, R.drawable.empty_user);
//        AuthorizationService.getInstance().getCurrentUser().setImage(attachment);
    }

    @Override
    public void failedUpload(Throwable t) {

    }

    public String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        if( cursor != null ){
            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(column_index);
            cursor.close();
            return path;
        }
        // this is our fallback here
        return uri.getPath();
    }
}
