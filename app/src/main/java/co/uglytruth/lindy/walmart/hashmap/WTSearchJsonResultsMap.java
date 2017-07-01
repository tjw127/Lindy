package co.uglytruth.lindy.walmart.hashmap;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

/**
 * Created by tjw127 on 6/27/17.
 */

public class WTSearchJsonResultsMap {

    public static String convertSearchResultsHashmapToString(HashMap<Integer, String> aMap)
    {
        Gson gson = new GsonBuilder().create();

        String json = gson.toJson(aMap);

        return json;
    }

    public static HashMap<Integer, String> convertJsonToHashmap(String json)
    {
        Gson gson = new GsonBuilder().create();

        HashMap<Integer, String> searchHashmap = gson.fromJson(json, HashMap.class);

        return searchHashmap;
    }
}
