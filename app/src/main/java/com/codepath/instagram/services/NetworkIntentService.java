package com.codepath.instagram.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.SystemClock;

import com.codepath.instagram.core.MainApplication;
import com.codepath.instagram.helpers.Utils;
import com.codepath.instagram.models.InstagramPost;
import com.codepath.instagram.models.InstagramPosts;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NetworkIntentService extends IntentService {

    private AsyncHttpClient syncClient = new SyncHttpClient();


    public NetworkIntentService() {
        super("NetworkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // get the request content from intent (and also a receiver)
        final ResultReceiver receiver = intent.getParcelableExtra("receiver");

        // do some SYNCHRONOUS processing
        RequestParams params = new RequestParams();
        params.put("access_token", MainApplication.getRestClient().accessToken);

        final String HOME_FEED_URL = "https://api.instagram.com/v1/users/self/feed";

        syncClient.get(HOME_FEED_URL, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // put back the result into the receiver
                List<InstagramPost> posts = Utils.decodePostsFromJsonResponse(response);
                InstagramPosts postsToBundle = new InstagramPosts(posts);
                Bundle bundle = new Bundle();
                bundle.putSerializable("posts", postsToBundle);

                // tell the receiver "you can execute the callback"
                // the callback is usually implemented somewhere else (in Activity or
                // Fragment).
                // we cannot directly invoke the callback function since we cannot preempt
                // the Activity or Fragment. We have to put the callback into Activity or
                // Fragment's queue (waiting to be executed)
                receiver.send(Activity.RESULT_OK, bundle);
            }

            @Override
            public void onFailure(int statusCode, Header[] header, Throwable throwable, JSONObject json) {
                // RESULT_CANCELED is not the best choice. You should define your own error code
                receiver.send(Activity.RESULT_CANCELED, null);
            }
        });
    }
}
