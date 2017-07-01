package co.uglytruth.lindy;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import com.walmartlabs.tofa.*;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.WTTaxonomy;
import co.uglytruth.lindy.walmart.adapter.WalmartAdapter;
import co.uglytruth.lindy.walmart.collections.WTSearchCollection;
import co.uglytruth.lindy.walmart.collections.WTTaxonomyCollection;
import co.uglytruth.lindy.walmart.hashmap.WTSearchJsonResultsMap;
import co.uglytruth.lindy.walmart.key.WTKeys;
import co.uglytruth.lindy.walmart.response.WTSearchResponse;
import co.uglytruth.lindy.walmart.response.WTTaxonomyResponse;
import co.uglytruth.lindy.walmart.start.WTStartTracking;

public class MainActivity extends AppCompatActivity {

    private RecyclerView walmartRecyclerView;

    private Context context;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;

    private boolean loading = false;

    private WTSearch wtSearch;

    private int currentStartInt = 0;

    private Integer currentKey;

    private HashMap<Integer, WTStartTracking> startTrackingHashMap;

    private HashMap<Integer, String> jsonTracking;
    private HashMap<Integer, Integer> itemsState; //keep track of when to trigger delete or insert new items

    private int visibleChildCount, pastVisibleChildCount, totalChildCount, lastVisibleChildPosition, completeVisibleChildFirstPosition, completeVisibleChildLastPosition;

    private BroadcastReceiver taxonomyBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            String taxonomy = intent.getStringExtra(WTKeys.taxonomyIntent);

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = preferences.edit();

            editor.putString(WTKeys.taxonomyIntent, taxonomy);

            editor.commit();

            if (taxonomy.length() > 0) {

                getSearchRecyclerView(taxonomy);

            }

        }
    };

    private BroadcastReceiver deleteSearchBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            Set<Integer> keySet = startTrackingHashMap.keySet();

            for (Integer key : keySet)
            {
                WTStartTracking startTracking = startTrackingHashMap.get(key);

                int startRange = startTracking.getStartRange().intValue();

                int endRange = startTracking.getEndRange().intValue();

                if ((startRange <= currentKey.intValue()) || (endRange >= currentKey.intValue()))
                {
                    int nextKey = key.intValue() + 1;

                    startTrackingHashMap.remove(new Integer(nextKey));
                }
            }

        }
    };

    private BroadcastReceiver addSearchBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            String c = preferences.getString(WTKeys.taxonomy, "");

            currentStartInt++;

            Integer start = new Integer(currentStartInt);

            if (c.length() > 0) {

                WTSearchCollection search = new WTSearchCollection.Builder()
                        .q("women")
                        .c(c)
                        .s(start.toString())
                        .n("25")
                        .context(context)
                        .executeType(WebserviceExecuteType.EXECUTE_PARAMS)
                        .walmartUrl()
                        .requestProperty()
                        .build();

            }

        }
    };
    private BroadcastReceiver searchBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {


            String search = intent.getStringExtra(WTKeys.search);

            wtSearch = WTSearchResponse.getResults(search);

            WalmartAdapter walmartAdapter = new WalmartAdapter();

            Integer startInt = new Integer(wtSearch.start);

            if (startInt.intValue() == 1) {

                Integer startRange = new Integer(1);

                Integer endRange = new Integer(wtSearch.numItems);

                WTStartTracking startTracking = new WTStartTracking();

                startTracking.setEndRange(endRange.intValue());

                startTracking.setStartRange(startRange.intValue());

                startTracking.setJson(search);

                startTrackingHashMap.put(startInt, startTracking);


            }else{

              Set<Integer> set = startTrackingHashMap.keySet();

              for (Object startObject : set.toArray())
              {
                  Integer startValueInteger = (Integer)startObject;

                  if ((startInt.intValue() - 1) == startValueInteger.intValue())
                  {
                      int startRangeInt = startTrackingHashMap.get(startValueInteger).getEndRange().intValue();

                      int endRangeInt = startRangeInt + new Integer(wtSearch.numItems).intValue();


                      WTStartTracking startTracking = new WTStartTracking();

                      startTracking.setStartRange(startRangeInt);

                      startTracking.setEndRange(endRangeInt);

                      startTracking.setJson(search);


                      startTrackingHashMap.put(startInt.intValue(), startTracking);

                  }
              }

            }


            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            String jsonConvertedString = sharedPreferences.getString("convert", "");


            Log.v("Convertv", " " + jsonConvertedString);

            if (jsonConvertedString.length() == 0) {

                //HashMap<Integer, String> jsonHaspMap = new HashMap<Integer, String>();

                jsonTracking.put(startInt, search);
                //jsonHaspMap.put(startInt, search);



                editor.commit();

            }else {



                try {


                    HashMap<Integer, String> hashMap = WTSearchJsonResultsMap.convertJsonToHashmap(jsonConvertedString);

                   if (!hashMap.containsKey(startInt)) {

                       hashMap.put(startInt, search);

                       String jsonConvert = WTSearchJsonResultsMap.convertSearchResultsHashmapToString(hashMap);


                       editor.putString("convert", jsonConvert);

                       editor.commit();
                   }

                }catch (Exception e)
                {
                    e.printStackTrace();
                }



            }



            WalmartAdapter.WTAdapter wtAdapter =  walmartAdapter.getAdapter(wtSearch.items, context);

            walmartRecyclerView.setAdapter(wtAdapter);

            progressDialog.cancel();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        WalmartSDK.initialize(this);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        walmartRecyclerView = (RecyclerView)findViewById(R.id.walmartRecyclerView);

        walmartRecyclerView.setLayoutManager(new GridLayoutManager(this,2));

        walmartRecyclerView.setHasFixedSize(false);

        context = this;

        progressDialog = new ProgressDialog(this);

        progressDialog.setMessage("Loading Items");

        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);


        itemsState = new HashMap<Integer, Integer>();

        startTrackingHashMap = new HashMap<Integer, WTStartTracking>();

        jsonTracking = new HashMap<Integer, String>();




        /*
        String json = WTSearchJsonResultsMap.convertSearchResultsHashmapToString(testMap);

        HashMap<Integer, String> testMapOne = WTSearchJsonResultsMap.convertJsonToHashmap(json);


        Log.v("Test", " " + testMapOne.get("1"));

        */
        /*
       for (Set<Integer> one : testMapOne.keySet())
       {
           String test = testMapOne.get(one);

           Log.v("Test", " " + test);
       }
       */

        walmartRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                //super.onScrolled(recyclerView, dx, dy);

               GridLayoutManager manager = (GridLayoutManager) recyclerView.getLayoutManager();

                visibleChildCount = manager.getChildCount();

                totalChildCount = manager.getItemCount();

                pastVisibleChildCount = manager.findFirstVisibleItemPosition();

                lastVisibleChildPosition = manager.findLastVisibleItemPosition();

                completeVisibleChildFirstPosition = manager.findFirstCompletelyVisibleItemPosition();

                completeVisibleChildLastPosition = manager.findLastCompletelyVisibleItemPosition();




                Log.v("visibleChildCount", " " + visibleChildCount);

                Log.v("totalChildCount", " " + totalChildCount);

                Log.v("pastVisibleChildCount", " " + pastVisibleChildCount);


                Integer lastChild = new Integer(pastVisibleChildCount);

                Integer start = new Integer(wtSearch.start);

                currentStartInt = start.intValue();

                int itemPosition = (pastVisibleChildCount + 1);

                //if ()
                if (dy > 0)
                {
                    //Scroll down
                    //Log.v("Scroll down ", " item " + pastVisibleChildCount);
                    if (pastVisibleChildCount > 0) {

                        if ((lastVisibleChildPosition % 6) == 0)
                        {



                            Log.v("Scroll down ", " load view " + pastVisibleChildCount);
                        }

                        if ((pastVisibleChildCount % 6) == 0) {

                            if (!itemsState.containsKey(lastChild)) {

                                int startInt = start.intValue();

                                int newStart = startInt++;

                                itemsState.put(lastChild, new Integer(newStart));

                                //Insert new (Search Collection) items

                                Intent intent = new Intent("Add_Search_Broadcast_Receiver");

                                context.sendBroadcast(intent);

                                //Log.v("Scroll down ", " itemsState " + startInt);
                            }else {

                                //Log.v("Scroll down ", " itemsState test " + pastVisibleChildCount);
                            }

                            Set<Integer> keys = itemsState.keySet();

                            for (int k = 0; k < keys.size(); k++)
                            {
                                Integer key = (Integer) keys.toArray()[k];

                                //Log.v("Scroll down", " key " + key.intValue());

                                if ((key.intValue() % 12) == 0)
                                {
                                   // Log.v("Scroll down", " key view " + key.intValue());

                                    //Delete the previous batch of data for example 1 through 25 if I have 26 through 50 items.

                                   Intent intent = new Intent("Delete_Search_Broadcast_Receiver");

                                   //context.sendBroadcast(intent);
                                }
                            }




                        }
                    }


                }else if (dy < 0)
                {
                    //Log.v("Scroll up", " item " + itemPosition);

                    if (pastVisibleChildCount > 0) {
                        //Log.v("Scroll up ", " Delete view " + lastVisibleChildPosition);
                        if ((lastVisibleChildPosition % 2) == 0)
                        {

                        }

                       // Log.v("Scroll up", " Delete " + pastVisibleChildCount);

                        if ((pastVisibleChildCount % 6) == 0)
                        {

                            if (!itemsState.containsKey(lastChild)) {

                                Log.v("Scroll up ", " load Fine " + pastVisibleChildCount);
                            }else {

                                //Delete anything not with range the for start 1 range is set to 25 items (out of range greater 25 items)

                                itemsState.remove(lastChild);
                                Log.v("Scroll up ", " load dataF " + pastVisibleChildCount);
                            }
                        }

                    }
                    //Scroll up
                    if (totalChildCount > 10)
                    {
                        if (!loading) {

                            if (itemPosition == 1) {
                                //Delete Any Items > 10
                            }



                        }
                    }
                }
            }

            @Override
            public int hashCode() {
                return super.hashCode();
            }

            @Override
            public boolean equals(Object obj) {
                return super.equals(obj);
            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }

            @Override
            public String toString() {
                return super.toString();
            }



            @Override
            protected void finalize() throws Throwable {
                super.finalize();
            }
        });

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

        String taxonomy = sharedPreferences.getString(WTKeys.taxonomyIntent, "");

        if (taxonomy.length() > 0)
        {
            progressDialog.show();
            getSearchRecyclerView(taxonomy);
        }else {

            try {
                progressDialog.show();
                WTTaxonomyCollection.getCategories(context);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
    }

    private void getSearchRecyclerView(String taxonomy)
    {


        if (taxonomy.length() > 0)
        {
            try {
                WTTaxonomy.Categories [] categories = WTTaxonomyResponse.getResult(taxonomy);

                HashMap<String, String> catHashMap = new HashMap<String, String>();

                for (WTTaxonomy.Categories category : categories)
                {
                    catHashMap.put(category.name, category.id);
                }

                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(WTKeys.taxonomy, catHashMap.get(WTKeys.clothing));

                editor.commit();

                WTSearchCollection search = new WTSearchCollection.Builder()
                        .q("women")
                        .c(catHashMap.get(WTKeys.clothing))
                        .n("25")
                        .context(context)
                        .executeType(WebserviceExecuteType.EXECUTE_PARAMS)
                        .walmartUrl()
                        .requestProperty()
                        .build();

            }catch (JSONException e)
            {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        LocalBroadcastManager.getInstance(context).registerReceiver(searchBroadcastReceiver, new IntentFilter("Search_BroadCast"));

        LocalBroadcastManager.getInstance(context).registerReceiver(taxonomyBroadcastReceiver, new IntentFilter("Taxonomy_BroadCast"));

        LocalBroadcastManager.getInstance(context).registerReceiver(addSearchBroadcastReceiver, new IntentFilter("Add_Search_Broadcast_Receiver"));

        LocalBroadcastManager.getInstance(context).registerReceiver(deleteSearchBroadcastReceiver, new IntentFilter("Delete_Search_Broadcast_Receiver"));

    }

    @Override
    protected void onStop() {
        super.onStop();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(searchBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(taxonomyBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(addSearchBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(deleteSearchBroadcastReceiver);


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        LocalBroadcastManager.getInstance(this).unregisterReceiver(searchBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(taxonomyBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(addSearchBroadcastReceiver);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(deleteSearchBroadcastReceiver);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
