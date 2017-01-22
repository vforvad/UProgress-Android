package com.example.vsokoltsov.uprogress.user.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.vsokoltsov.uprogress.R;
import com.example.vsokoltsov.uprogress.common.utils.ContextManager;
import com.example.vsokoltsov.uprogress.direction_detail.model.steps.Step;
import com.example.vsokoltsov.uprogress.direction_detail.ui.StepsItem;
import com.example.vsokoltsov.uprogress.user.current.User;
import com.example.vsokoltsov.uprogress.user.current.UserItem;
import com.example.vsokoltsov.uprogress.user.ui.UserInfoItem;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by vsokoltsov on 07.01.17.
 */

public class UserInfoListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<UserItem> items;
    private final int ITEM_VIEW_TYPE_BASIC = 0;
    Context context = ContextManager.getInstance().getContext();


    public UserInfoListAdapter(List<UserItem> items){
        this.items = items;

    }

    @Override
    public int getItemViewType(int position) {
        return ITEM_VIEW_TYPE_BASIC;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item_list, parent, false);
            UserInfoViewHolder uvh = new UserInfoViewHolder(v);
            return uvh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        UserItem item = items.get(position);
        UserInfoViewHolder viewHolder = ((UserInfoViewHolder) holder);
        viewHolder.bind(item);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }


    public class UserInfoViewHolder extends RecyclerView.ViewHolder {
        private UserInfoItem userItem;

        public UserInfoViewHolder (View itemView) {
            super(itemView);
            userItem = (UserInfoItem) itemView;
        }

        public void bind(UserItem info) {
            userItem.bind(info);
        }

    }

    public void setList(User user) {
        Resources resources = context.getResources();
        List<UserItem> list = new ArrayList<UserItem>();
        list.add(new UserItem(resources.getString(R.string.user_profile_email), user.getEmail()));
        list.add(new UserItem(resources.getString(R.string.user_profile_nick), user.getNick()));
        list.add(new UserItem(resources.getString(R.string.user_profile_location), user.getLocation()));
        list.add(new UserItem(resources.getString(R.string.user_profile_description), user.getDescription()));
        items = list;
    }
}
