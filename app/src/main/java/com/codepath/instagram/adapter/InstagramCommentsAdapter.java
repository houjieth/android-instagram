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
        public ImageView ivCommentAvantar;
        public TextView tvCommentComment;
        public TextView tvCommentTimestamp;

        public ViewHolder(View view) {
            super(view);

            ivCommentAvantar = (ImageView) view.findViewById(R.id.ivCommentAvatar);
            tvCommentComment = (TextView) view.findViewById(R.id.tvCommentComment);
            tvCommentTimestamp = (TextView) view.findViewById(R.id.tvCommentTimestamp);
        }
    }
}
