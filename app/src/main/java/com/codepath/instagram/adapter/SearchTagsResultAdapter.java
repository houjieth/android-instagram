package com.codepath.instagram.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.instagram.R;
import com.codepath.instagram.models.InstagramSearchTag;
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
public class SearchTagsResultAdapter extends RecyclerView.Adapter<SearchTagsResultAdapter.ViewHolder> {

    private List<InstagramSearchTag> tags;
    private Context context;

    public SearchTagsResultAdapter(List<InstagramSearchTag> tags, Context context) {
        this.tags = tags;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View userView = inflater.inflate(R.layout.layout_item_tag, parent, false);
        ViewHolder viewHolder = new ViewHolder(userView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        InstagramSearchTag tag = tags.get(position);

        holder.tvTagName.setText(tag.tag);
        holder.tvTagCount.setText(tag.count);
    }

    @Override
    public int getItemCount() {
        return 3;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.tvTagName) TextView tvTagName;
        @Bind(R.id.tvTagCount) TextView tvTagCount;

        public ViewHolder(View view) {
            super(view);

            ButterKnife.bind(this, view);
        }
    }
}
