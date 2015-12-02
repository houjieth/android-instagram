package com.codepath.instagram.networking;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

/**
 * Created by jie on 12/2/15.
 */
public class InstagramClient {

    public static void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        String url = "https://api.instagram.com/v1/media/popular";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", "e05c462ebd86446ea48a5af73769b602");
        client.get(url, params, responseHandler);
    }

    public static void getComments(String mediaId, JsonHttpResponseHandler responseHandler) {
        String url = "https://api.instagram.com/v1/media/" + mediaId + "/comments";
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("client_id", "e05c462ebd86446ea48a5af73769b602");
        client.get(url, params, responseHandler);
    }
}
