package co.uglytruth.lindy.Webservice;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutionException;

/**
 * Created by tjw127 on 6/21/17.
 */

public class Webservice {

    public String urlString;
    public String result = null;


    public static class Builder{

        private String url;

        private int executeTypeInt = 0;

        public Builder url(String value)
        {
            this.url = value;

            return this;
        }

        public Builder executeType(int value)
        {
            this.executeTypeInt = value;

            return this;
        }



        public Webservice build()
        {
            return new Webservice(this);
        }
    }

    public Webservice(Builder builder)
    {
        urlString = builder.url;

        try {
            switch (builder.executeTypeInt)
            {
                case 0:

                    result =  new URLBackgroundService().execute().get();

                    break;

                case 1:
                    new URLBackgroundService().execute().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);

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


    public class URLBackgroundService extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {



            try {

                String charset = "UTF-8";


                URL url = new URL(urlString);
                URLConnection httpURLConnection = url.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);


                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                httpURLConnection.setRequestProperty("Accept-Charset", charset);
                httpURLConnection.connect();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = null;

                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine())  != null)
                    stringBuilder.append(line + "\n");


                result = stringBuilder.toString();


            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String aResult) {

            Log.v("Web2.0", " " + aResult);

            super.onPostExecute(aResult);
        }
    }
}
