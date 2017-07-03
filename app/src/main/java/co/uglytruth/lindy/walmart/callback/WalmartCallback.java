package co.uglytruth.lindy.walmart.callback;

import android.os.Bundle;

/**
 * Created by tjw127 on 6/25/17.
 */

public interface WalmartCallback {

    public void taskComplete(boolean sucess);

    public void onResult(int resultCode, Bundle resultData);
}
