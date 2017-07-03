package co.uglytruth.lindy.walmart.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.walmart.collections.WTSearchCollection;
import co.uglytruth.lindy.walmart.key.WTKeys;

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


        currentStartInt = intent.getIntExtra("currentStartInt", 0);

        Log.v("WTSearchAddService", " execute " + currentStartInt);



        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        final String c = preferences.getString(WTKeys.taxonomy, "");

        currentStartInt++;

        final Integer start = new Integer(currentStartInt);

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
    }
}
