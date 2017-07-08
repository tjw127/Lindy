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

    public static WTSearch.Items[] mergeItems(Set<String> aJsonString)
    {


        Set<String> itemsSet =  aJsonString;

        int totalItemInt = 0;

        int itemCounter = 0;

        ArrayList<WTSearch.Items> itemsList = new ArrayList<WTSearch.Items>();


        for (String itemJson : itemsSet)
        {
            WTSearch search =  WTSearchResponse.getResults(itemJson);

            WTSearch.Items[] itemArray = search.items;

            Integer itemNums = new Integer(search.numItems);

            totalItemInt += itemNums.intValue();

            for (int i = 0; i < itemArray.length; i++)
            {

                itemsList.add(itemArray[i]);

            }


        }

        WTSearch.Items[] items = new WTSearch.Items[itemsList.size()];


        for (int j = 0; j < itemsList.size(); j++)
        {
            items[j] = itemsList.get(j);
        }
        return items;
    }
}
