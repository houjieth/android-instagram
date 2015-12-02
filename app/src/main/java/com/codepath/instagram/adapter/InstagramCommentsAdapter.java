package com.codepath.instagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramComment;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by jie on 12/2/15.
 */
public class InstagramCommentsAdapter extends RecyclerView.Adapter<InstagramCommentsAdapter.ViewHolder> {

    private Context context;
    private List<InstagramComment> comments;

    public InstagramCommentsAdapter(List<InstagramComment> comments, Context context) {
        this.comments = comments;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View commentView = inflater.inflate(R.layout.layout_item_comment, parent, false);
        return new ViewHolder(commentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InstagramComment comment = comments.get(position);

        holder.ivCommentAvantar.setImageDrawable(null);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(36)
                .oval(false)
                .build();
        Picasso.with(context)
                .load(comment.user.profilePictureUrl)
                .transform(transformation)
                .into(holder.ivCommentAvantar);

        holder.tvCommentComment.setText(Utils.buildCommentSpanable(comment.user.userName, comment.text, context));

        holder.tvCommentTimestamp.setText(Utils.convertToInstagramStyleTimestamp(comment.createdTime));
    }

    @Override
    public int getItemCount() {
        return comments.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.ivCommentAvatar) ImageView ivCommentAvantar;
        @Bind(R.id.tvCommentComment) TextView tvCommentComment;
        @Bind(R.id.tvCommentTimestamp) TextView tvCommentTimestamp;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
