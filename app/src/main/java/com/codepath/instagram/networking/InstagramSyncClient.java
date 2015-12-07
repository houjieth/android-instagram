package com.codepath.instagram.networking;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

/**
 * Created by jie on 12/6/15.
 */
public class InstagramSyncClient extends SyncHttpClient {

    private final String accessToken;

    public InstagramSyncClient(String accessToken) {
        this.accessToken = accessToken;
    }

    public void getFeed(JsonHttpResponseHandler responseHandler) {
        String url = "https://api.instagram.com/v1/users/self/feed";
        // A hack! For some reason the library doesn't append the access_token to the request
        // we have to do it manually
        RequestParams params = new RequestParams();
        params.put("access_token", accessToken);
        get(url, params, responseHandler);
    }
}
