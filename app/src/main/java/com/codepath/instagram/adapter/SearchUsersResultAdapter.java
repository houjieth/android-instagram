package com.codepath.instagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramUser;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jie on 12/3/15.
 */
public class SearchUsersResultAdapter extends RecyclerView.Adapter<SearchUsersResultAdapter.ViewHolder> {

    private List<InstagramUser> users;
    private Context context;

    public SearchUsersResultAdapter(List<InstagramUser> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.layout_item_user, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InstagramUser user = users.get(position);

        holder.ivUserAventar.setImageDrawable(null);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(40)
                .oval(false)
                .build();
        Picasso.with(context)
                .load(user.profilePictureUrl)
                .transform(transformation)
                .into(holder.ivUserAventar);

        holder.tvUserUsername.setText(user.userName);
        holder.tvUserFullname.setText(user.fullName);
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivUserAvantar) ImageView ivUserAventar;
        @Bind(R.id.tvUserUsername) TextView tvUserUsername;
        @Bind(R.id.tvUserFullname) TextView tvUserFullname;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
