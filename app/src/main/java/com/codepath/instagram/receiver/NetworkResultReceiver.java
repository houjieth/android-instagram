package com.codepath.instagram.receiver;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by jie on 12/6/15.
 */
@SuppressLint("ParcelCreator")
public class NetworkResultReceiver extends ResultReceiver {

    // This is a custom interface. You notice that if you extend from ResultReceiver,
    // the onReceiveResult function will have fixed parameters (an int and a Bundle)
    // What if your delegate provider (such as HomeActivity) wants to use a customized
    // callback interface? That's why we override onReceiveResult from ResultReceiver,
    // and replace it with a call to our own interface (implemented in this private
    // receiver)
    public interface Receiver {
        void onReceiveResult(int resultCode, Bundle resultData);
    }

    private Receiver receiver;

    public NetworkResultReceiver(Handler handler) {
        super(handler);
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        if (receiver != null) {
            receiver.onReceiveResult(resultCode, resultData);
        }
    }
}
