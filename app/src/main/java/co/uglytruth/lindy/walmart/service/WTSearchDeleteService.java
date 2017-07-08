package co.uglytruth.lindy.walmart.service;

import android.app.IntentService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;

import java.util.HashSet;
import java.util.Set;

import co.uglytruth.lindy.walmart.key.WTKeys;

/**
 * Created by tjw127 on 7/3/17.
 */

public class WTSearchDeleteService extends IntentService {

    public WTSearchDeleteService()
    {
        super("WTSearchDeleteService");
    }
    @Override
    protected void onHandleIntent(@Nullable Intent intent) {


        int currentStartInt = intent.getIntExtra("currentStartInt", 0);

        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        Set<String> set = preferences.getStringSet(WTKeys.searchJsonResults, new HashSet<String>());

        if (set.size() > 0)
        {

           set.remove(currentStartInt);

           SharedPreferences.Editor editor = preferences.edit();

           editor.putStringSet(WTKeys.searchJsonResults, set);

           editor.commit();


        }

    }
}
