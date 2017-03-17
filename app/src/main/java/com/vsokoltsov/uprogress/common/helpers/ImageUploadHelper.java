package com.vsokoltsov.uprogress.common.helpers;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.vsokoltsov.uprogress.user.current.User;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

import static android.app.Activity.RESULT_OK;

/**
 * Created by vsokoltsov on 17.01.17.
 */

public class ImageUploadHelper {
    public final String APP_TAG = "UProgress";
    public String photoFileName = "photo.jpg";
    private final Activity activity;
    public final UploadHelper helper;
    Uri imageUri;
    private File selectImageFile = null;
    private final int CAMERA_REQUEST = 1888;
    private final int GALERY_REQUEST = 444;
    private Fragment fragment;

    public ImageUploadHelper(UploadHelper helper) {
        this.helper = helper;
        this.activity = ((Fragment) helper).getActivity();
        this.fragment = (Fragment) helper;
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data, User user) {
        if (resultCode == RESULT_OK) {
            if(requestCode == CAMERA_REQUEST){
                Uri takenPhotoUri = getPhotoFileUri(photoFileName);
                selectImageFile = new File(takenPhotoUri.getPath());
                Bitmap takenImage = BitmapFactory.decodeFile(takenPhotoUri.getPath());
            }
            else if (requestCode == GALERY_REQUEST) {
                Uri takenPhotoUri = data.getData();
                Bitmap bitmap = null;
                String path = getPath(takenPhotoUri);
                selectImageFile = new File(path);

            }
            if (selectImageFile != null) {
                RequestBody requestFile =
                        RequestBody.create(MediaType.parse("multipart/form-data"), selectImageFile);
                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body =
                        MultipartBody.Part.createFormData("file", selectImageFile.getName(), requestFile);
                RequestBody attachmentableId = RequestBody.create(MediaType.parse("text/plain"), Integer.toString(user.getId()));
                RequestBody attachmentableType = RequestBody.create(MediaType.parse("text/plain"), "User");
                helper.setUploadFileData(body, attachmentableId, attachmentableType);
            }
        }
    }

    public void cameraIntent() {
        Intent camerIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camerIntent.putExtra(MediaStore.EXTRA_OUTPUT, getPhotoFileUri(photoFileName));
        fragment.startActivityForResult(camerIntent, CAMERA_REQUEST);
    }

    public void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        fragment.startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), GALERY_REQUEST);
    }


    private String getPath(Uri uri) {
        // just some safety built in
        if( uri == null ) {
            // TODO perform some logging or show user feedback
            return null;
        }
        // try to retrieve the image from the media store first
        // this will only work for images selected from gallery
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
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

    private boolean isExternalStorageAvailable() {
        String state = Environment.getExternalStorageState();
        return state.equals(Environment.MEDIA_MOUNTED);
    }

    public Uri getPhotoFileUri(String fileName) {
        // Only continue if the SD Card is mounted
        if (isExternalStorageAvailable()) {
            // Get safe storage directory for photos
            // Use `getExternalFilesDir` on Context to access package-specific directories.
            // This way, we don't need to request external read/write runtime permissions.
            File mediaStorageDir = new File(activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES), APP_TAG);

            // Create the storage directory if it does not exist
            if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()){
                Log.d(APP_TAG, "failed to create directory");
            }

            // Return the file target for the photo based on filename
            return Uri.fromFile(new File(mediaStorageDir.getPath() + File.separator + fileName));
        }
        return null;
    }
}
