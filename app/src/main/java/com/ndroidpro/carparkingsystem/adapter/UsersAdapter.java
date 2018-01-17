package com.ndroidpro.carparkingsystem.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.model.UserProfile;

import java.util.ArrayList;
import java.util.List;

public class UsersAdapter extends RecyclerView.Adapter<UsersViewHolder> {

    private OnItemClickListener clickListener;
    private List<UserProfile> mUserProfiles;
    private Context context;

    public UsersAdapter(Context context, ArrayList<UserProfile> userProfileArrayList) {
        this.context = context;
        mUserProfiles = userProfileArrayList;
    }

    @Override
    public UsersViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_car_parking_location, parent, false);
        return new UsersViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final UsersViewHolder viewHolder, final int position) {

        final UserProfile userProfile = mUserProfiles.get(position);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClick(position, userProfile);
            }
        });

        viewHolder.bind(userProfile);
    }

    @Override
    public int getItemCount() {
        return mUserProfiles.size();
    }

    public void setOnItemClickListener(OnItemClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void notifyDataChange(ArrayList<UserProfile> userProfileArrayList) {
        mUserProfiles = userProfileArrayList;
        notifyDataSetChanged();
    }

    public interface OnItemClickListener {
        void onItemClick(int position, UserProfile userProfile);
    }
}
