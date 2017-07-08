package co.uglytruth.lindy.Webservice.test;

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
 * Created by tjw127 on 7/7/17.
 */

public class WebserviceTestTwo {

    public String urlString;
    public String result = null;


    public String getURL(String s) throws Exception
    {

        urlString = s;

        //new URLBackgroundService().executeOnExecutor(AsyncTask.SERIAL_EXECUTOR)
        // new URLBackgroundService().ex

        result =  new URLBackgroundService().get();


        Log.v("WTSearchAddService2", " Ty Wade" + result);
        return result;
    }





    public class URLBackgroundService extends AsyncTask<String, String, String>
    {

        @Override
        protected String doInBackground(String... params) {

            Log.v("URLView ", " " + urlString);
            URL url = null;
            try {

                String charset = "UTF-8";
                //String query = String.format("v=%s&session_id=%s&q=%s", URLEncoder.encode(v, charset),URLEncoder.encode("1234view",charset), URLEncoder.encode(textString, charset));


                URL urlV = new URL(urlString);
                URLConnection httpURLConnection = urlV.openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setUseCaches(false);


                //httpURLConnection.setRequestProperty("Authorization", "Bearer 3YGR5KDANX7YPPKK42QJWLEJWJSPIPQH");
                httpURLConnection.setRequestProperty("Content-Type", "application/json");
                httpURLConnection.setRequestProperty("Accept", "application/json");
                //httpURLConnection.setRequestProperty("q", "how%20many%20bae%20between%20Tuesday%20and%20Friday");
                httpURLConnection.setRequestProperty("Accept-Charset", charset);
                httpURLConnection.connect();
                InputStream inputStream = new BufferedInputStream(httpURLConnection.getInputStream());

                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                String line = null;

                StringBuilder stringBuilder = new StringBuilder();
                while ((line = reader.readLine())  != null)
                    stringBuilder.append(line + "\n");


                result = stringBuilder.toString();


                Log.v("URLView ", " " + result);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return result;
        }

        @Override
        protected void onPostExecute(String aVoid) {

            Log.v("Web2.0", " " + aVoid);

            super.onPostExecute(aVoid);
        }
    }
}
