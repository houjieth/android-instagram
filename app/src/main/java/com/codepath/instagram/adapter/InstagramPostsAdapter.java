package com.codepath.instagram.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.activities.CommentsActivity;
import com.codepath.instagram.helpers.DeviceDimensionsHelper;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramComment;
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
        final InstagramPost post = posts.get(position);

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

        holder.tvTimestamp.setText(Utils.convertToInstagramStyleTimestamp(post.createdTime));

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
            holder.tvCaption.setText(Utils.buildCommentSpanable(post.user.userName, post.caption, context));
        }

        if (post.commentsCount == 0) {
            holder.llComments.setVisibility(View.GONE);
            holder.tvViewAllComments.setVisibility(View.GONE);
        } else if (post.commentsCount <= 2) {
            holder.tvViewAllComments.setVisibility(View.GONE);
            inflateComments(post, post.commentsCount, holder.llComments);
        } else {
            holder.tvViewAllComments.setText("View all " + post.commentsCount + " comments");
            inflateComments(post, 2, holder.llComments);
        }

        holder.tvViewAllComments.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("mediaId", post.mediaId);
                context.startActivity(intent);
            }
        });

        final ImageView ivMoreDots = holder.ivMoreDots;
        final ImageView ivPhoto = holder.ivPhoto;
        ivMoreDots.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPopup(ivMoreDots, ivPhoto);
            }
        });
    }

    private void showPopup(View view, final ImageView imageView) {
        PopupMenu popup = new PopupMenu(context, view);
        popup.getMenuInflater().inflate(R.menu.menu_popup, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.mnPopupShare:
                        Drawable drawable = imageView.getDrawable();
                        Bitmap mBitmap = ((BitmapDrawable) drawable).getBitmap();
                        String path = MediaStore.Images.Media.insertImage(
                                context.getContentResolver(),
                                mBitmap,
                                "Instagram Photo",
                                null
                        );
                        Uri uri = Uri.parse(path);
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
                        shareIntent.setType("image/*");
                        context.startActivity(Intent.createChooser(shareIntent, "Share Image"));
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    private void inflateComments(InstagramPost post, int count, ViewGroup root) {
        root.removeAllViews();
        LayoutInflater inflater = LayoutInflater.from(context);
        for (int i = 0; i < count; i++) {
            InstagramComment comment = post.comments.get(i);
            TextView commentView = (TextView) inflater.inflate(R.layout.layout_item_text_comment, root, false);
            commentView.setText(Utils.buildCommentSpanable(comment.user.userName, comment.text, context));
            root.addView(commentView);
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
        public TextView tvViewAllComments;
        public ImageView ivMoreDots;
        public LinearLayout llComments;

        public PostItemViewHolder(View view) {
            super(view);

            ivUserImage = (ImageView) view.findViewById(R.id.ivUserImage);
            tvUsername = (TextView) view.findViewById(R.id.tvUsername);
            tvTimestamp = (TextView) view.findViewById(R.id.tvTimestamp);
            ivPhoto = (ImageView) view.findViewById(R.id.ivPhoto);
            tvLikes = (TextView) view.findViewById(R.id.tvLikes);
            tvCaption = (TextView) view.findViewById(R.id.tvCaption);
            tvViewAllComments = (TextView) view.findViewById(R.id.tvViewAllComments);
            ivMoreDots = (ImageView) view.findViewById(R.id.ivMoreDots);
            llComments = (LinearLayout) view.findViewById(R.id.llComments);
        }
    }
}
