package co.uglytruth.lindy.walmart.params;

import java.util.HashMap;

import co.uglytruth.lindy.convert.Convert;

/**
 * Created by tjw127 on 6/21/17.
 */

public class WTSearchParams extends Convert{


    public String parameters;


    public static class Builder{

        private String parameter;

        private HashMap<String, String> params;

        public Builder()
        {
            params = new HashMap<String, String>();

            parameter = "";

            //http://api.walmartlabs.com/v1/search?apiKey={apiKey}&lsPublisherId={Your LinkShare Publisher Id}&query=ipod
        }

        public Builder apiKey(String value)
        {
            this.params.put("a", value);

            return this;
        }

        public Builder publisherId(String value)
        {
            this.params.put("p", value);

            return this;
        }


        public Builder sort(String value)
        {
            this.params.put("st", value);

            return this;
        }

        public Builder responseGroup(String value)
        {
            this.params.put("r", value);

            return this;
        }

        public Builder facet(String value)
        {

            this.params.put("ft", value);

            return this;
        }

        public Builder facet_filter(String value)
        {

            this.params.put("ftf", value);

            return this;
        }

        public Builder start(String value)
        {

            this.params.put("s", value);

            return this;
        }

        public Builder order(String value)
        {

            this.params.put("o", value);

            return this;
        }

        public Builder numItems(String value)
        {

            this.params.put("n", value);

            return this;
        }

        public Builder format(String value)
        {

            this.params.put("f", value);

            return this;
        }


        public Builder facet_range(String value)
        {

            this.params.put("fr", value);

            return this;
        }




        public Builder categoryId(String value)
        {
            this.params.put("c", value);

            return this;
        }


        public Builder query(String value)
        {
            this.params.put("q", value.replace(" ", "%20"));

            return this;
        }

        public WTSearchParams build()
        {
            this.parameter = concatKeyValuePairs(this.params);

            return new WTSearchParams(this);
        }




    }

    public WTSearchParams(Builder builder)
    {

        parameters = builder.parameter;
    }

}
