package com.codepath.instagram.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.codepath.instagram.R;
import com.codepath.instagram.adapter.InstagramCommentsAdapter;
import com.codepath.instagram.helpers.SimpleVerticalSpacerItemDecoration;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramComment;
import com.codepath.instagram.networking.InstagramClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CommentsActivity extends AppCompatActivity {

    private List<InstagramComment> comments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);

        fetchComments(getIntent().getStringExtra("mediaId"));
    }

    private void fetchComments(String mediaId) {
        InstagramClient.getComments(mediaId, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                comments = Utils.decodeCommentsFromJsonResponse(response);
                RecyclerView rvComments = (RecyclerView) findViewById(R.id.rvComments);
                InstagramCommentsAdapter adapter = new InstagramCommentsAdapter(comments, CommentsActivity.this);
                rvComments.setAdapter(adapter);
                rvComments.setLayoutManager(new LinearLayoutManager(CommentsActivity.this));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                Utils.makeToast("Fail to make http request (" + statusCode + ")", CommentsActivity.this);
            }
        });
    }
}
