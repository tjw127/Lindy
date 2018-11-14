package co.uglytruth.lindy;

import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.AsyncQueryHandler;
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

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.WTTaxonomy;
import co.uglytruth.lindy.walmart.Walmart;
import co.uglytruth.lindy.walmart.adapter.WalmartAdapter;
import co.uglytruth.lindy.walmart.collections.WTSearchCollection;
import co.uglytruth.lindy.walmart.collections.WTTaxonomyCollection;
import co.uglytruth.lindy.walmart.hashmap.WTSearchJsonResultsMap;
import co.uglytruth.lindy.walmart.items.WTItems;
import co.uglytruth.lindy.walmart.key.WTKeys;
import co.uglytruth.lindy.walmart.response.WTSearchResponse;
import co.uglytruth.lindy.walmart.response.WTTaxonomyResponse;
import co.uglytruth.lindy.walmart.rest_api.WalmartRestAPI;
import co.uglytruth.lindy.walmart.service.WTSearchAddService;
import co.uglytruth.lindy.walmart.service.WTSearchService;
import co.uglytruth.lindy.walmart.start.WTStartTracking;
import co.uglytruth.lindy.walmart.tag.WTActivityResultTags;
import co.uglytruth.lindy.walmart.update.WTSearchUpdateUI;
import co.uglytruth.lindy.walmart.update.WTSearchUpdateUIInterface;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);







        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
    }

    public void walmart_search(String q){

        Walmart.RequestBuilder requestBuilder = new Walmart.RequestBuilder();

        Walmart.EndpointBuilder endpointBuilder = new Walmart.EndpointBuilder();

        Walmart.ArgumentsBuilder argumentsBuilder = new Walmart.ArgumentsBuilder();

        argumentsBuilder.query(q);

        WalmartRestAPI.Search search = new WalmartRestAPI.Search(endpointBuilder, argumentsBuilder, requestBuilder);

        String result = (String)search.getResults();



        Log.d("WalmartSearch 1 ", result);

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
        walmart_search("women");


    }

    @Override
    protected void onStop() {
        super.onStop();




    }

    @Override
    protected void onDestroy() {
        super.onDestroy();



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
