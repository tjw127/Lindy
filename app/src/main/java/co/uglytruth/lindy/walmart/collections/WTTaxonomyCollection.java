package co.uglytruth.lindy.walmart.collections;

import android.content.Context;

import org.json.JSONException;

import co.uglytruth.lindy.Webservice.WebserviceAsyncTask;
import co.uglytruth.lindy.Webservice.contenttype.ContentType;
import co.uglytruth.lindy.Webservice.requestproperty.RequestProperty;
import co.uglytruth.lindy.Webservice.test.WebserviceTest;
import co.uglytruth.lindy.Webservice.type.WebserviceCollectionType;
import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.Webservice.type.WebserviceType;
import co.uglytruth.lindy.walmart.WTTaxonomy;
import co.uglytruth.lindy.walmart.WalmartUrl;
import co.uglytruth.lindy.walmart.response.WTTaxonomyResponse;

/**
 * Created by tjw127 on 6/22/17.
 */

public class WTTaxonomyCollection {

    public static void getCategories(Context context) throws JSONException
    {
        WalmartUrl walmartUrl = new WalmartUrl.Builder().taxonomy().build();

        RequestProperty requestProperty = new RequestProperty.Builder().contentType(ContentType.JSON).accept(ContentType.JSON.toString()).accept_charset("UTF-8").build();

        WebserviceAsyncTask webserviceAsyncTask = new WebserviceAsyncTask.Builder().url(walmartUrl.url).requestProperty(requestProperty.requestPropertyMap).context(context).executeType(WebserviceExecuteType.EXECUTE_PARAMS).webserviceType(WebserviceCollectionType.WTTAXONOMYCOLLLECTION).build();


    }
}
