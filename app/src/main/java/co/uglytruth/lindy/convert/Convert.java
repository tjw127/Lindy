package co.uglytruth.lindy.convert;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by tjw127 on 6/21/17.
 */

public class Convert {

    public static String concatKeyValuePairs(HashMap<String, String> params)
    {
        Iterator it = params.entrySet().iterator();

        String parameters = "";

        ArrayList<String> list = new ArrayList<String>();

        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry)it.next();

            String key = pair.getKey().toString();

            String value = pair.getValue().toString();


            list.add(key + "=" + value);
            it.remove();
        }

        for (int i = 0; i < list.size(); i++)
        {

            if (i == (list.size() - 1))
            {
                parameters += list.get(i);
            }else {

                parameters += list.get(i) + "&";
            }

        }

        return parameters;

    }
}
