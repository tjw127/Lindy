package co.uglytruth.lindy.Webservice.requestproperty;

import java.util.HashMap;

import co.uglytruth.lindy.Webservice.contenttype.ContentType;

/**
 * Created by tjw127 on 6/21/17.
 */

public class RequestProperty {

    public HashMap<String, String> requestPropertyMap;

    public static class Builder{

        private HashMap<String, String> requestPropertyMap;

        public Builder ()
        {
            this.requestPropertyMap = new HashMap<String, String>();

        }

        public Builder contentType(ContentType contentType)
        {
            this.requestPropertyMap.put("Content-Type", contentType.toString());

            return this;
        }

        public Builder accept(String value)
        {
            this.requestPropertyMap.put("Accept", value);

            return this;
        }

        public Builder accept_charset(String charset)
        {
            this.requestPropertyMap.put("Accept-Charset", charset);

            return this;
        }

        public RequestProperty build()
        {
            return new RequestProperty(this);
        }

    }

    public RequestProperty(Builder builder)
    {
        this.requestPropertyMap = builder.requestPropertyMap;
    }
}
