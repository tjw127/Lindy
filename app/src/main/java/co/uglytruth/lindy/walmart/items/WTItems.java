package co.uglytruth.lindy.walmart.items;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.response.WTSearchResponse;

/**
 * Created by tjw127 on 6/30/17.
 */

public class WTItems {

    public static WTSearch.Items[] mergeItems(HashMap<Integer, String> aItemsHashmap)
    {


        Set<Integer> itemInteger =  aItemsHashmap.keySet();

        int totalItemInt = 0;

        int itemCounter = 0;

        ArrayList<WTSearch.Items> itemsList = new ArrayList<WTSearch.Items>();


        for (Integer itemInt : itemInteger)
        {
            WTSearch search =  WTSearchResponse.getResults(aItemsHashmap.get(itemInt));

            WTSearch.Items[] itemArray = search.items;

            Integer itemNums = new Integer(search.numItems);

            totalItemInt += itemNums.intValue();

            for (int i = 0; i < itemArray.length; i++)
            {

                itemsList.add(itemArray[i]);

            }

        }

        return (WTSearch.Items[]) itemsList.toArray();
    }
}
