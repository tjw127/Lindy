package co.uglytruth.lindy.walmart.response;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import co.uglytruth.lindy.walmart.WTSearch;

/**
 * Created by tjw127 on 6/22/17.
 */

public class WTSearchResponse {

    public static WTSearch getResults(String result)
    {
        Gson gson = new GsonBuilder().create();

        WTSearch searchResponse = gson.fromJson(result, WTSearch.class);

        return searchResponse;
    }
}
