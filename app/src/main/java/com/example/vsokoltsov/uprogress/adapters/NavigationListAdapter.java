package com.example.vsokoltsov.uprogress.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.models.NavigationItem;
import com.example.vsokoltsov.uprogress.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by vsokoltsov on 13.03.16.
 */
public class NavigationListAdapter extends BaseAdapter {
    private Activity activity;
    private LayoutInflater inflater;
    private List<NavigationItem> navigationList;
    private NavigationItem navigation;

    public NavigationListAdapter(Activity activity, List<NavigationItem> navigationList) {
        this.activity = activity;
        this.navigationList = navigationList;
    }

    @Override
    public int getCount() {
        return navigationList.size();
    }

    @Override
    public Object getItem(int location) {
        return navigationList.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        navigation = navigationList.get(position);

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (convertView == null) {
            // TODO: Make after authorization
            if (navigation.isUserCell()) {
                convertView = inflater.inflate(R.layout.navigation_header, null);
                configViewForUserItem(convertView);
            }
            else {
                convertView = inflater.inflate(R.layout.navigation_drawer_item, null);
                configViewForSimilarItem(convertView);
            }
        }
        return convertView;
    }

    private void configViewForUserItem(View contentView) {
        User user = navigation.getUser();
        final CircleImageView avatarView = (CircleImageView) contentView.findViewById(R.id.circleView);
        setUserNameInHeader(user, contentView);
        Drawable emptyUserImg = activity.getResources().getDrawable(R.drawable.empty_user);
        avatarView.setImageDrawable(emptyUserImg);
//        Drawable background = (Drawable) activity.getResources().getDrawable(R.drawable.backgroundploy);
//        int width = background.getIntrinsicWidth();
//        Bitmap bitmap = ((BitmapDrawable) background).getBitmap();
//        int backgroundHeight = (int) activity.getResources().getDimension(R.dimen.user_header_navigation_menu_height);
//        Drawable d = new BitmapDrawable(activity.getResources(), Bitmap.createScaledBitmap(bitmap, width, backgroundHeight, true));
//        contentView.findViewById(R.id.backgroundView).setBackground(d);
        if (navigation.getUser().getImage() != null) {
            Drawable emptyUser = activity.getResources().getDrawable(R.drawable.empty_user);
            String fullUrl = navigation.getUser().getImage().getUrl();
            Picasso.with(this.activity.getApplicationContext())
                    .load(fullUrl)
                    .placeholder(emptyUser)
                    .into(avatarView);
        }
    }

    private void configViewForSimilarItem(View convertView) {
        final TextView text = (TextView) convertView.findViewById(R.id.navigationText);
        final ImageView image = (ImageView) convertView.findViewById(R.id.navigationImage);

        text.setText(navigation.getTitle());
        image.setImageResource(navigation.getImage());
    }

    private void setUserNameInHeader(User user, View contentView) {
        TextView userName = (TextView) contentView.findViewById(R.id.name);
        TextView userEmail = (TextView) contentView.findViewById(R.id.email);

        if (user.isFullNamePresent()) {
            userName.setText(user.getCorrectName());
            userEmail.setText(user.getNick());
        }
        else {
            userName.setText(user.getNick());
        }
    }
}
