package co.uglytruth.lindy.walmart;

import co.uglytruth.lindy.base.Base;

/**
 * Created by tjw127 on 6/21/17.
 */

public class WalmartUrl {

    public String url;

    public static class Builder{

        private String walmartUrl;

        public Builder ()
        {
            walmartUrl = Base.baseUrl;
        }

        public Builder productLookUp()
        {
            walmartUrl += "/productLookUp";

            return this;
        }

        public Builder itemSearch()
        {
            this.walmartUrl += "/itemsearch";

            return this;
        }

        public Builder wtsearch()
        {
            this.walmartUrl += "/wtsearch";

            return this;
        }

        public Builder taxonomy()
        {
            this.walmartUrl += "/wttaxonomy";

            return this;
        }

        public WalmartUrl build()
        {
            return new WalmartUrl(this);
        }


    }

    public WalmartUrl(Builder builder)
    {
        this.url = builder.walmartUrl;
    }

}
