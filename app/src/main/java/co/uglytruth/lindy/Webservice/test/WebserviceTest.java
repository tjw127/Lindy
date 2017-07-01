package co.uglytruth.lindy.Webservice.test;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;

import co.uglytruth.lindy.Webservice.connection.WebserviceConnection;
import co.uglytruth.lindy.Webservice.result.WebserviceResult;
import co.uglytruth.lindy.Webservice.type.WebserviceCollectionType;
import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.Webservice.type.WebserviceType;
import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.adapter.WalmartAdapter;
import co.uglytruth.lindy.walmart.response.WTSearchResponse;

/**
 * Created by tjw127 on 6/21/17.
 */

public class WebserviceTest {

    public String urlString;

    public WebserviceResult result = null;

    public HashMap<String, String> requestPropertyMap;

    private Context finalContext;

    private WebserviceCollectionType webserviceType;

    public static class Builder{

        private String url;

        private HashMap<String, String> requestPropertyMap;

        private WebserviceExecuteType executeTypeInt;

        private Context context;

        private WebserviceCollectionType type;

        public Builder url(String value)
        {
            this.url = value;

            return this;
        }

        public Builder executeType(WebserviceExecuteType value)
        {
            this.executeTypeInt = value;

            return this;
        }


        public Builder context(Context context)
        {
            this.context = context;

            return this;
        }

        public Builder webserviceType(WebserviceCollectionType type)
        {
            this.type = type;

            return this;
        }


        public Builder requestProperty(HashMap<String, String> valueMap)
        {
            this.requestPropertyMap = valueMap;

            return this;
        }



        public WebserviceTest build()
        {
            return new WebserviceTest(this);
        }
    }

    public WebserviceTest(Builder builder)
    {
        urlString = builder.url;

        if (builder.context != null) {

            finalContext = builder.context;
        }

        this.webserviceType = builder.type;


        requestPropertyMap = builder.requestPropertyMap;
        try {
            switch (builder.executeTypeInt.getValue())
            {
                case 1:

                    new URLBackgroundService().execute().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR);

                    break;

                case 2:
                    new URLBackgroundService().execute().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

                    break;

                case 3:
                    result =  new URLBackgroundService().execute().get();
                    break;

                case 4:

                    new URLBackgroundService().execute();
                    break;

                case 5:

                    break;

                default:
                    result =  new URLBackgroundService().execute().get();
                    break;
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

    }


    public class URLBackgroundService extends AsyncTask<String, String, WebserviceResult>
    {

        @Override
        protected WebserviceResult doInBackground(String... params) {

            result = new WebserviceResult();

            WebserviceResult webserviceResult = new WebserviceResult();

            String jsonResult = null;

            try {


                WebserviceConnection webserviceConnection = new WebserviceConnection.Builder().url(urlString).urlConnection(requestPropertyMap).inputStream().bufferReader("iso-8859-1").build();

                jsonResult = webserviceConnection.result;

                webserviceResult.setResult(jsonResult);

                webserviceResult.setType(webserviceType.getValue());


            } catch (Exception e) {
                e.printStackTrace();
            }


            return webserviceResult;
        }

        @Override
        protected void onPostExecute(WebserviceResult aResult) {


            if (aResult != null)
            {
                switch (aResult.getType())
                {
                    case 1:

                        if (finalContext != null) {

                            Intent intent = new Intent("Search_BroadCast");

                            intent.putExtra("search", aResult.getResult());

                            LocalBroadcastManager.getInstance(finalContext).sendBroadcast(intent);

                        }

                        break;

                    case 2:

                        if (finalContext != null)
                        {
                            Intent intent = new Intent("Taxonomy_BroadCast");

                            intent.putExtra("taxonomy", aResult.getResult());

                            LocalBroadcastManager.getInstance(finalContext).sendBroadcast(intent);

                        }

                        break;
                }

            }


            super.onPostExecute(aResult);
        }
    }
}
