package co.uglytruth.lindy;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.Switch;


//import com.google.firebase.analytics.FirebaseAnalytics;
import com.mopub.mobileads.MoPubErrorCode;
import com.mopub.mobileads.MoPubInterstitial;
import com.mopub.mobileads.MoPubView;
//import com.walmartlabs.tofa.WalmartSDK;

import org.json.JSONException;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.ebates.EbatesActivity;
import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.WTTaxonomy;
import co.uglytruth.lindy.walmart.collections.WTSearchCollection;
import co.uglytruth.lindy.walmart.collections.WTTaxonomyCollection;
import co.uglytruth.lindy.walmart.items.WTItems;
import co.uglytruth.lindy.walmart.key.WTKeys;
import co.uglytruth.lindy.walmart.response.WTTaxonomyResponse;
import co.uglytruth.lindy.walmart.service.WTSearchAddService;
import co.uglytruth.lindy.walmart.service.WTSearchService;
import co.uglytruth.lindy.walmart.start.WTStartTracking;
import co.uglytruth.lindy.walmart.update.WTSearchUpdateUI;
import co.uglytruth.lindy.walmart.update.WTSearchUpdateUIInterface;

public class LindyHomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, MoPubInterstitial.InterstitialAdListener, MoPubView.BannerAdListener, WTSearchUpdateUIInterface {

//    private FirebaseAnalytics mFirebaseAnalytics;

    private RecyclerView walmartRecyclerView;

    private Context context;

    private ProgressDialog progressDialog;

    private SharedPreferences sharedPreferences;

    private boolean loading = false;

    private WTSearch wtSearch;

    private MoPubInterstitial interstitial;
    Switch location;
     Switch storage;

    private int currentStartInt = 0;

    MoPubView moPubView;

    private Integer currentKey;

    private HashMap<Integer, WTStartTracking> startTrackingHashMap;

    private HashMap<Integer, String> jsonTracking;

    private Set<String> searchJsonResult;

    private WTSearchService wtSearchService;

    private HashMap<Integer, Integer> itemsState; //keep track of when to trigger delete or insert new items

    private Intent wtSearchServiceIntent;

    private Intent wtSearchAddServiceIntent;

    private Intent wtDeleteServiceIntent;

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
        public void onReceive(Context aContext, Intent intent) {



            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);

            final String c = preferences.getString(WTKeys.taxonomy, "");

            currentStartInt++;

            final Integer start = new Integer(currentStartInt);





            wtSearchServiceIntent.putExtra(WTKeys.currentStartInt, currentStartInt);

            context.startService(wtSearchServiceIntent);

            if (c.length() > 0) {

                Log.v("Scroll down", " " + currentStartInt);



            }

        }
    };
    private BroadcastReceiver searchBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            /*

            String search = intent.getStringExtra(WTKeys.search);

            wtSearch = WTSearchResponse.getResults(search);

            WalmartAdapter walmartAdapter = new WalmartAdapter();

            Integer startInt = new Integer(wtSearch.start);

            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);

            SharedPreferences.Editor editor = sharedPreferences.edit();

            if (startInt.intValue() == 1) {

                Integer startRange = new Integer(1);

                Integer endRange = new Integer(wtSearch.numItems);

                WTStartTracking startTracking = new WTStartTracking();

                startTracking.setEndRange(endRange.intValue());

                startTracking.setStartRange(startRange.intValue());

                startTracking.setJson(search);

                startTrackingHashMap.put(startInt, startTracking);

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



            Set<String> jsonSet = sharedPreferences.getStringSet(WTKeys.searchJsonResults, new HashSet<String>());

            Log.v("Scroll down", " jsonSet " + jsonSet.size());

            WTSearch.Items[] itemsArray;
            if (jsonSet.size() > 1) {

                itemsArray = WTItems.mergeItems(jsonSet);

            }else {

                itemsArray = wtSearch.items;
            }
            WalmartAdapter.WTAdapter wtAdapter = walmartAdapter.getAdapter(itemsArray, context);

            walmartRecyclerView.setAdapter(wtAdapter);
            progressDialog.cancel();

            */
            // Intent intent1 = new Intent("co.uglytruth.lindy.MainActivity");

            //intent1.setPackage("co.uglytruth.lindy.MainActivity");

            updateUI(walmartRecyclerView, sharedPreferences.getStringSet(WTKeys.searchJsonResults, new HashSet<String>()), getApplicationContext());

            progressDialog.cancel();
            //MainActivity.this.setResult(WTActivityResultTags.WTSearch_Result_Status_OK.getRequestCode());

            //startActivityForResult(intent1, WTActivityResultTags.WTSearch_Result_Status_OK.getRequestCode());
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        WalmartSDK.initialize(this);
        setContentView(R.layout.activity_lindy_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);
//        mFirebaseAnalytics.setCurrentScreen(this, "LindyHomeActivity", null);
//        Bundle bundle = new Bundle();
//        bundle.putString(FirebaseAnalytics.Param.ITEM_ID, "1234");
//        bundle.putString(FirebaseAnalytics.Param.ITEM_NAME, "LindyHomeActivity");
//        bundle.putString(FirebaseAnalytics.Param.CONTENT_TYPE, "lindy");
//        mFirebaseAnalytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, bundle);
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)
        {
            turnOnLcation();
            //location.setChecked(false);
        }
        else
        {
            //location.setChecked(true);
        }
/*
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            storage.setChecked(false);
        }else {
            storage.setChecked(true);
        }
        */

        /*
        location.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    turnOnLcation();
                }
            }
        });

        storage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    turnOnStorage();
                }
            }
        });

*/
        moPubView = (MoPubView)findViewById(R.id.adview);

        moPubView.setAdUnitId("bec958748ca54d2787cf01ca8c27bc90");
        moPubView.setBannerAdListener(this);// Enter your Ad Unit ID from www.mopub.com
        moPubView.loadAd();

        interstitial = new MoPubInterstitial(this, "a4edab6298c14b728c74387499f049cb");
        interstitial.setInterstitialAdListener(this);

        interstitial.load();
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

        searchJsonResult = new HashSet<String>();

        wtSearchService = new WTSearchService();




        wtSearchServiceIntent = new Intent(this, WTSearchService.class);

        wtSearchAddServiceIntent = new Intent(this, WTSearchAddService.class);





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

                //Integer start = new Integer(wtSearch.start);

                // currentStartInt = start.intValue();

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

                                // int startInt = start.intValue();

                                //int newStart = startInt++;

                                // itemsState.put(lastChild, new Integer(newStart));

                                //Insert new (Search Collection) items

                                //Intent intent = new Intent("Add_Search_Broadcast_Receiver");

                                Set<String> stringSet =  sharedPreferences.getStringSet(WTKeys.searchJsonResults, new HashSet<String>());

                                String currentKey = sharedPreferences.getString(WTKeys.currentStartKey, "");


                                if (stringSet.size() < 2) {

                                    Intent intent = new Intent(context, WTSearchAddService.class);

                                    Log.v("WTSearchAddService", " " + currentKey);

                                    if (currentKey.length() > 0) {

                                        intent.putExtra("currentStartInt", new Integer(currentKey).intValue());

                                    }
                                    //context.sendBroadcast(intent);

                                    context.startService(intent);

                                }

                                //LocalBroadcastManager.getInstance(context).sendBroadcast(intent);



                                Log.v("Scroll down ", " Add_Search_Broadcast_Receiver " + stringSet.size());
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


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
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
    public void updateUI(RecyclerView recyclerView, Set<String> resultSet, Context context) {

        WTSearch.Items[] items =  WTItems.mergeItems(resultSet);

        // WalmartAdapter walmartAdapter = new WalmartAdapter();

        // WalmartAdapter.WTAdapter wtAdapter = walmartAdapter.getAdapter(items, context);

        WTSearchUpdateUI wtSearchUpdateUI = new WTSearchUpdateUI.Builder().items(items).context(context).recyclerView(recyclerView).build();


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);


        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        switch (resultCode)
        {
            case 205:

                updateUI(this.walmartRecyclerView, sharedPreferences.getStringSet(WTKeys.searchJsonResults, new HashSet<String>()), getApplicationContext());

                break;

            default:

                break;
        }
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
                        .q("women lingerie".replace(" ", "%20"))
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

    public void turnOnLcation()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED)

        {
            ActivityCompat.requestPermissions(LindyHomeActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 2);
            //location.setChecked(true);
        }else {

            //location.setChecked(true);
        }
    }

    public void turnOnStorage()
    {
        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(LindyHomeActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
           // storage.setChecked(true);





            sharedPreferences.edit().putBoolean("firstrun", false).commit();



        }else
        {
            storage.setChecked(true);

        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.lindy_home, menu);
        return true;
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

            Intent intentEbates = new Intent(this, EbatesActivity.class);
            intentEbates.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentEbates);

        } else if (id == R.id.nav_gallery) {

            Intent intentKindle = new Intent(this, KindleActivity.class);
            intentKindle.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentKindle);

        } else if (id == R.id.nav_slideshow) {

            Intent intentBCBG = new Intent(this, BCBGActivity.class);
            intentBCBG.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentBCBG);

        }  else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onInterstitialLoaded(MoPubInterstitial interstitial) {

        if (interstitial.isReady())
        {
            interstitial.show();
        }
    }

    @Override
    public void onInterstitialFailed(MoPubInterstitial interstitial, MoPubErrorCode errorCode) {

    }

    @Override
    public void onInterstitialShown(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialClicked(MoPubInterstitial interstitial) {

    }

    @Override
    public void onInterstitialDismissed(MoPubInterstitial interstitial) {

    }

    @Override
    public void onBannerLoaded(MoPubView banner) {

    }

    @Override
    public void onBannerFailed(MoPubView banner, MoPubErrorCode errorCode) {

    }

    @Override
    public void onBannerClicked(MoPubView banner) {

    }

    @Override
    public void onBannerExpanded(MoPubView banner) {

    }

    @Override
    public void onBannerCollapsed(MoPubView banner) {

    }
}
