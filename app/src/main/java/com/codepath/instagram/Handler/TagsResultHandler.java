package com.codepath.instagram.Handler;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.instagram.R;
import com.codepath.instagram.adapter.SearchTagsResultAdapter;
import com.codepath.instagram.adapter.SearchUsersResultAdapter;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramSearchTag;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jie on 12/3/15.
 */
public class TagsResultHandler extends JsonHttpResponseHandler {

    private Activity activity;

    public TagsResultHandler(Activity activity) {
        this.activity = activity;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        List<InstagramSearchTag> tags = Utils.decodeSearchTagsFromJsonResponse(response);
        RecyclerView rvTags = (RecyclerView) activity.findViewById(R.id.rvTags);
        SearchTagsResultAdapter adapter = new SearchTagsResultAdapter(tags, activity);
        rvTags.setAdapter(adapter);
        rvTags.setLayoutManager(new LinearLayoutManager(activity));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Utils.makeToast("Please check your network", activity);
    }
}

