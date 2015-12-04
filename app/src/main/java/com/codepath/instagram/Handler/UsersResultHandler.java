package com.codepath.instagram.Handler;

import android.app.Activity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.codepath.instagram.R;
import com.codepath.instagram.adapter.SearchUsersResultAdapter;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramUser;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jie on 12/3/15.
 */
public class UsersResultHandler extends JsonHttpResponseHandler {

    private View view;

    public UsersResultHandler(View view) {
        this.view = view;
    }

    @Override
    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
        List<InstagramUser> users = Utils.decodeUsersFromJsonResponse(response);
        RecyclerView rvUsers = (RecyclerView) view.findViewById(R.id.rvUsers);
        SearchUsersResultAdapter adapter = new SearchUsersResultAdapter(users, view.getContext());
        rvUsers.setAdapter(adapter);
        rvUsers.setLayoutManager(new LinearLayoutManager(view.getContext()));
    }

    @Override
    public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
        Utils.makeToast("Please check your network", view.getContext());
    }
}
