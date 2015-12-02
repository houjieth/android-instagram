package com.codepath.instagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.format.DateUtils;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.helpers.DeviceDimensionsHelper;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramPost;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.util.List;

/**
 * Created by jie on 12/1/15.
 */
public class InstagramPostsAdapter extends RecyclerView.Adapter<InstagramPostsAdapter.PostItemViewHolder> {

    private List<InstagramPost> posts;
    private Context context;

    public InstagramPostsAdapter(List<InstagramPost> posts, Context context) {
        this.posts = posts;
        this.context = context;
    }

    @Override
    public PostItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View postItemView = inflater.inflate(R.layout.layout_item_post, parent, false);
        PostItemViewHolder viewHolder = new PostItemViewHolder(postItemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(PostItemViewHolder holder, int position) {
        InstagramPost post = posts.get(position);

        holder.ivUserImage.setImageDrawable(null);
        Transformation transformation = new RoundedTransformationBuilder()
                .cornerRadiusDp(30)
                .oval(false)
                .build();
        Picasso.with(context)
                .load(post.user.profilePictureUrl)
                .transform(transformation)
                .into(holder.ivUserImage);

        holder.tvUsername.setText(post.user.userName);

        holder.tvTimestamp.setText(
                DateUtils.getRelativeTimeSpanString(post.createdTime * 1000,
                        System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        holder.ivPhoto.setImageDrawable(null);
        int displayWidth = DeviceDimensionsHelper.getDisplayWidth(context);
        Picasso.with(context)
                .load(post.image.imageUrl)
                .resize(displayWidth, 0)
                .placeholder(R.drawable.gray_rectangle)
                .into(holder.ivPhoto);

        holder.tvLikes.setText(Utils.formatNumberForDisplay(post.likesCount) + " likes");

        if (post.caption == null || post.caption.length() == 0) {
            holder.tvCaption.setVisibility(View.GONE);
        } else {
            ForegroundColorSpan blueForegroundColorSpan = new ForegroundColorSpan(
                    context.getResources().getColor(R.color.blue_text));
            SpannableStringBuilder builder = new SpannableStringBuilder(post.user.userName);
            builder.setSpan(
                    blueForegroundColorSpan,
                    0,
                    builder.length(),
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            );
            builder.append(" ");
            builder.append(post.caption);
            holder.tvCaption.setText(builder);
        }
    }

    @Override
    public int getItemCount() {
        return posts.size();
    }

    public static class PostItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivUserImage;
        public TextView tvUsername;
        public TextView tvTimestamp;
        public ImageView ivPhoto;
        public TextView tvLikes;
        public TextView tvCaption;

        public PostItemViewHolder(View view) {
            super(view);

            ivUserImage = (ImageView) view.findViewById(R.id.ivUserImage);
            tvUsername = (TextView) view.findViewById(R.id.tvUsername);
            tvTimestamp = (TextView) view.findViewById(R.id.tvTimestamp);
            ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
            tvLikes = (TextView) view.findViewById(R.id.tvLikes);
            tvCaption = (TextView) view.findViewById(R.id.tvCaption);
        }
    }
}
