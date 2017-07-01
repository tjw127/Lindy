package co.uglytruth.lindy.Webservice.connection;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by tjw127 on 6/21/17.
 */

public class WebserviceConnection {

    public String result = null;

    public static class Builder
    {
        private URL url;

        private URLConnection urlConnection;

        private InputStream inputStream;

        private BufferedReader reader;

        private StringBuilder stringBuilder;

        private String result = null;

        public Builder ()
        {

        }

        public Builder url(String value){

            try {
                this.url = new URL(value);

            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder urlConnection(HashMap<String, String> requestPropertyMap)
        {
            try {
                this.urlConnection = this.url.openConnection();

                this.urlConnection.setDoInput(true);

                this.urlConnection.setUseCaches(false);



            } catch (IOException e) {

                e.printStackTrace();

            }

            try{

                if (requestPropertyMap.size() == 0)
                {

                    throw new NullPointerException();

                }else {


                    for (Map.Entry<String, String> requestSet : requestPropertyMap.entrySet())
                    {

                        this.urlConnection.setRequestProperty(requestSet.getKey(), requestSet.getValue());

                    }

                }

            }catch (NullPointerException e)
            {
                e.printStackTrace();
            }

            try {

                this.urlConnection.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

            return this;
        }

        public Builder inputStream()
        {
            try{

                this.inputStream = new BufferedInputStream(this.urlConnection.getInputStream());

            }catch (NullPointerException e)
            {

                e.printStackTrace();

            } catch (IOException e) {

                e.printStackTrace();

            }

            return this;
        }

        public Builder bufferReader(String charset)
        {
            try {

                this.reader = new BufferedReader(new InputStreamReader(this.inputStream, charset));

            } catch (UnsupportedEncodingException e) {

                e.printStackTrace();

            }catch (NullPointerException n)
            {
                n.printStackTrace();
            }

            return this;
        }

        public WebserviceConnection build()
        {

            this.stringBuilder = new StringBuilder();

            String line = null;

            try {
                while ((line = this.reader.readLine()) != null)
                {

                    stringBuilder.append(line + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }


            if (stringBuilder.toString().length() != 0)
            {
                this.result = this.stringBuilder.toString();
            }

            return new WebserviceConnection(this);

        }
    }

    public WebserviceConnection(Builder builder)
    {
        this.result = builder.result;
    }
}
