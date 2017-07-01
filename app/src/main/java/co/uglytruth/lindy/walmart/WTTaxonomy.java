package co.uglytruth.lindy.walmart;

import com.google.gson.annotations.SerializedName;

/**
 * Created by tjw127 on 6/22/17.
 */

public class WTTaxonomy {


    @SerializedName("Categories")
    public Categories [] categories;

    public static class Categories {

        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("children")
        public Children[] children;


        private static class Children{

            @SerializedName("id")
            public String id;

            @SerializedName("name")
            public String name;

        }
    }
}
