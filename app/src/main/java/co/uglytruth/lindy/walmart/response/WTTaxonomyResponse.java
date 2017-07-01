package co.uglytruth.lindy.walmart.response;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import co.uglytruth.lindy.walmart.WTTaxonomy;

/**
 * Created by tjw127 on 6/22/17.
 */

public class WTTaxonomyResponse {

    public static WTTaxonomy.Categories[] getResult(String result) throws JSONException
    {
        Gson gson = new GsonBuilder().create();

        //JSONArray jsonArray = new J
        //JSONArray jsonArray = new JSONArray(result);

        JSONArray jsonObject = new JSONObject(result).getJSONArray("categories");



        WTTaxonomy.Categories[] categories = gson.fromJson(jsonObject.toString(), WTTaxonomy.Categories[].class);

        for (WTTaxonomy.Categories categories1 : categories)
        {
            Log.v("Categories ", " " + categories1.name + " \n" + " " + categories1.id);
        }


        return categories;
    }
}
