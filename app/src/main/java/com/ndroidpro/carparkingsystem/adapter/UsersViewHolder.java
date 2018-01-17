package com.ndroidpro.carparkingsystem.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ndroidpro.carparkingsystem.R;
import com.ndroidpro.carparkingsystem.model.UserProfile;

public class UsersViewHolder extends RecyclerView.ViewHolder {

    private TextView tvName;
    private ImageView imvEdit;

    public UsersViewHolder(View itemView ) {
        super(itemView);
        tvName = (TextView) itemView.findViewById(R.id.tv_item_name);
        imvEdit = (ImageView) itemView.findViewById(R.id.btn_edit);
        imvEdit.setVisibility(View.GONE);
    }


    public void bind(UserProfile userProfile) {
        tvName.setText(userProfile.getUserName());
    }
}
