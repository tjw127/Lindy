package co.uglytruth.lindy.walmart.callback;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

/**
 * Created by tjw127 on 7/3/17.
 */

public class WalmartUpdateUICallback extends ResultReceiver implements WalmartCallback{
    /**
     * Create a new ResultReceive to receive results.  Your
     * {@link #onReceiveResult} method will be called from the thread running
     * <var>handler</var> if given, or from an arbitrary thread if null.
     *
     * @param handler
     */
    public WalmartUpdateUICallback(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {
        super.onReceiveResult(resultCode, resultData);


    }

    @Override
    public void onResult(int resultCode, Bundle resultData) {

    }

    @Override
    public void taskComplete(boolean sucess) {

    }
}
