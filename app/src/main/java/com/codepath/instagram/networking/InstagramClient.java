package com.codepath.instagram.networking;

import android.app.DownloadManager;
import android.content.Context;
import android.util.Log;

import com.codepath.instagram.helpers.Constants;
import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;

/**
 * Created by jie on 12/2/15.
 */
public class InstagramClient extends OAuthBaseClient {

    public static final Class<? extends Api> REST_API_CLASS = InstagramApi.class;
    public static final String REST_URL = "https://api.instagram.com/v1/";
    public static final String REST_CONSUMER_KEY = "e05c462ebd86446ea48a5af73769b602";
    public static final String REST_CONSUMER_SECRET = "7f18a14de6c241c2a9ccc9f4a3df4b35";
    public static final String REDIRECT_URI = Constants.REDIRECT_URI;
    public static final String SCOPE = Constants.SCOPE;

    private String accessToken;

    public InstagramClient(Context context) {
        super(context, REST_API_CLASS, REST_URL,
                REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REDIRECT_URI, SCOPE);
        accessToken = client.getAccessToken().getToken();
    }

    public void getPopularFeed(JsonHttpResponseHandler responseHandler) {
        String url = "https://api.instagram.com/v1/media/popular";
        RequestParams params = new RequestParams();
        params.put("client_id", "e05c462ebd86446ea48a5af73769b602");
        client.get(url, params, responseHandler);
    }

    public void getComments(String mediaId, JsonHttpResponseHandler responseHandler) {
        String url = "https://api.instagram.com/v1/media/" + mediaId + "/comments";
        RequestParams params = new RequestParams();
        params.put("client_id", "e05c462ebd86446ea48a5af73769b602");
        client.get(url, params, responseHandler);
    }

    public void getFeed(JsonHttpResponseHandler responseHandler) {
        String url = "https://api.instagram.com/v1/users/self/feed";
        // A hack! For some reason the library doesn't append the access_token to the request
        // we have to do it manually
        RequestParams params = new RequestParams();
        params.put("access_token", accessToken);
        Log.d("Client-token", accessToken);
        client.get(url, params, responseHandler);
    }

    public void queryUser(String searchTerm, JsonHttpResponseHandler responseHandler) {
        String url = "https://api.instagram.com/v1/users/search";
        RequestParams params = new RequestParams();
        addAccessToken(params);
        params.put("q", searchTerm);
        client.get(url, params, responseHandler);
    }

    public void queryTag(String searchTerm, JsonHttpResponseHandler responseHandler) {
        String url = "https://api.instagram.com/v1/tags/search";
        RequestParams params = new RequestParams();
        addAccessToken(params);
        params.put("q", searchTerm);
        client.get(url, params, responseHandler);
    }

    private void addAccessToken(RequestParams params) {
        params.put("access_token", accessToken);
    }
}
