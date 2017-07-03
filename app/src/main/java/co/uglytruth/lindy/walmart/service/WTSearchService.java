package co.uglytruth.lindy.walmart.service;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.adapter.WalmartAdapter;
import co.uglytruth.lindy.walmart.collections.WTSearchCollection;
import co.uglytruth.lindy.walmart.items.WTItems;
import co.uglytruth.lindy.walmart.key.WTKeys;
import co.uglytruth.lindy.walmart.response.WTSearchResponse;
import co.uglytruth.lindy.walmart.start.WTStartTracking;

/**
 * Created by tjw127 on 7/2/17.
 */

public class WTSearchService extends IntentService {
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public WTSearchService(String name) {
        super(name);
    }

    public WTSearchService() {

        super("WTSearchService");

    }

    private Context context;

    private int currentStartInt;

    private WTSearch wtSearch;

    private Set<String> searchJsonResult;

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        String search = intent.getStringExtra(WTKeys.search);

        wtSearch = WTSearchResponse.getResults(search);

        WalmartAdapter walmartAdapter = new WalmartAdapter();

        Integer startInt = new Integer(wtSearch.start);

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (startInt.intValue() == 1) {

            Integer startRange = new Integer(1);

            Integer endRange = new Integer(wtSearch.numItems);





            searchJsonResult.add(search);

            editor.putStringSet(WTKeys.searchJsonResults, searchJsonResult);

            editor.commit();



        }else{

            Set<String> searchJsonResultsSaved = sharedPreferences.getStringSet(WTKeys.searchJsonResults, new HashSet<String>());

            if (searchJsonResultsSaved.size() != 0)
            {
                int startEqualFlag = 0;

                for (int i = 0; i < searchJsonResultsSaved.size(); i++)
                {

                    WTSearch searchResponse = WTSearchResponse.getResults(searchJsonResultsSaved.toArray()[i].toString());

                    Integer startSaved = new Integer(searchResponse.start);

                    if (startInt.intValue() == startSaved.intValue())
                    {
                        startEqualFlag = 1;
                    }
                }

                if (startEqualFlag != 1)
                {
                    searchJsonResultsSaved.add(search);

                    Log.v("SearchJsonResultsSaved", " " + searchJsonResultsSaved.size());

                    editor.putStringSet(WTKeys.searchJsonResults, searchJsonResultsSaved);

                    editor.commit();
                }
            }



        }



        Set<String> jsonSet = sharedPreferences.getStringSet(WTKeys.searchJsonResults, new HashSet<String>());

        Log.v("Scroll down", " jsonSet " + jsonSet.size());

        WTSearch.Items[] itemsArray;
        if (jsonSet.size() > 1) {

            itemsArray = WTItems.mergeItems(jsonSet);

        }else {

            itemsArray = wtSearch.items;
        }


    }
}
