package com.codepath.instagram.services;

import android.app.Activity;
import android.app.IntentService;
import android.content.Intent;
import android.content.Context;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.os.SystemClock;

/**
 * An {@link IntentService} subclass for handling asynchronous task requests in
 * a service on a separate handler thread.
 * <p>
 * TODO: Customize class - update intent actions, extra parameters and static
 * helper methods.
 */
public class NetworkIntentService extends IntentService {

    public NetworkIntentService() {
        super("NetworkIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        // get the request content from intent (and also a receiver)
        ResultReceiver receiver = intent.getParcelableExtra("receiver");

        // do some SYNCHRONOUS processing
        SystemClock.sleep(3000);

        // put back the result into the receiver
        Bundle bundle = new Bundle();
        bundle.putString("resultValue", "FUCK YA");

        // tell the receiver "you can execute the callback"
        // the callback is usually implemented somewhere else (in Activity or
        // Fragment).
        // we cannot directly invoke the callback function since we cannot preempt
        // the Activity or Fragment. We have to put the callback into Activity or
        // Fragment's queue (waiting to be executed)
        receiver.send(Activity.RESULT_OK, bundle);
    }
}
