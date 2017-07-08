package co.uglytruth.lindy.walmart.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import co.uglytruth.lindy.Webservice.Webservice;
import co.uglytruth.lindy.Webservice.test.WebserviceTestTwo;
import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.walmart.WalmartUrl;
import co.uglytruth.lindy.walmart.collections.WTSearchCollection;
import co.uglytruth.lindy.walmart.key.WTKeys;
import co.uglytruth.lindy.walmart.params.WTSearchParams;

/**
 * Created by tjw127 on 7/2/17.
 */

public class WTSearchAddService extends IntentService{
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */

    private int currentStartInt;

    public WTSearchAddService(String name) {
        super(name);
    }

    public WTSearchAddService()
    {
        super("WTSearchAddService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        currentStartInt = intent.getIntExtra(WTKeys.currentStartInt, 0);








        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final String c = preferences.getString(WTKeys.taxonomy, "");






        if (currentStartInt > 0) {

            currentStartInt++;

             Integer start = new Integer(currentStartInt);

            Log.v("WTSearchAddService1", " " + start.toString());



            WTSearchParams params = new WTSearchParams.Builder().query("women").categoryId(c).start(start.toString())
                    .numItems("25").build();

            WalmartUrl walmartUrl = new WalmartUrl.Builder().wtsearch().build();

            WebserviceTestTwo webserviceTestTwo = new WebserviceTestTwo();

            try {
                Log.v("WTSearchAddService3", " " + walmartUrl.url + "?" + params.parameters);

                webserviceTestTwo.getURL(walmartUrl.url + "?" + params.parameters);
            } catch (Exception e) {
                e.printStackTrace();
            }
           /*

            WTSearchCollection search = new WTSearchCollection.Builder()
                    .q("women")
                    .c(c)
                    .s(start.toString())
                    .n("25")
                    .context(getApplicationContext())
                    .executeType(WebserviceExecuteType.EXECUTE_PARAMS)
                    .walmartUrl()
                    .requestProperty()
                    .build();


                    */

        }
    }
}
