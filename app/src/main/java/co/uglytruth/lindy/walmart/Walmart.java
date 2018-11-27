package co.uglytruth.lindy.walmart;

import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import co.uglytruth.lindy.base.Base;
import co.uglytruth.lindy.walmart.builder.Builder;
import okhttp3.MediaType;

public class Walmart {

    public TreeMap<Integer, String> url = new TreeMap<Integer, String>();

    public static class RequestBuilder implements Builder{

        private MediaType JSON
                = MediaType.parse("application/json; charset=utf-8");

        private MediaType XML
                = MediaType.parse("application/xml; charset=utf-8");
        private String url;

//        private OkHttpClient client;
//
//        private OkHttpAsyncTaskService okHttpAsyncTask;
//
//        private OkHttpAsyncTaskResponse response;


        public RequestBuilder(){
            Log.v("RequestBuilder", "Builder class");
//            client = new OkHttpClient();
        }

        public RequestBuilder url(String url, String args){

            this.url = url;

            this.url += args;

            return this;
        }

//        public RequestBuilder response(OkHttpAsyncTaskResponse okHttpAsyncTaskResponse){
//
//            this.response = okHttpAsyncTaskResponse;
//
//            return this;
//        }

        public RequestBuilder url(String url){

            this.url = url;

            return this;
        }
        @Override
        public Object build() {

            /*
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(url)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            Log.d("Walmart Retro", " " + retrofit.toString());

            */


//         okHttpAsyncTask = new OkHttpAsyncTaskService(this.url);
//         okHttpAsyncTask.okHttpAsyncTaskResponse = this.response;
//         okHttpAsyncTask.execute();

            return null;
        }
    }
    public static class ArgumentsBuilder implements Builder{

        String arguments;

        HashMap<String, String> args;

        public ArgumentsBuilder(){

            args = new HashMap<String, String>();
        }
        public ArgumentsBuilder apiKey(String key){

            this.args.put("apiKey", key);

            return this;
        }

        public ArgumentsBuilder lsPublisherId(String id){

            this.args.put("lsPublisherId", id);

            return this;
        }

        public ArgumentsBuilder format(String format){

            this.args.put("format", format);

            return this;
        }

        public ArgumentsBuilder query(String q){

            this.args.put("query", q);

            return this;
        }

        public ArgumentsBuilder sort(String sort){

            this.args.put("sort", sort);

            return this;
        }

        public ArgumentsBuilder order(String order){

            this.args.put("order", order);

            return this;
        }

        public ArgumentsBuilder facet(String facet){

            this.args.put("facet", facet);

            return this;
        }

        public ArgumentsBuilder facet_filter(String filter){

            this.args.put("facet.filter", filter);

            return this;
        }

        public ArgumentsBuilder start(String start){

            this.args.put("start", start);

            return this;
        }

        public ArgumentsBuilder category(String category){

            this.args.put("category", category);

            return this;
        }

        public ArgumentsBuilder brand(String brand){

            this.args.put("brand", brand);

            return this;
        }

        public ArgumentsBuilder lat(String lat){

            this.args.put("lat", lat);

            return this;
        }

        public ArgumentsBuilder lon(String lon){

            this.args.put("lon", lon);

            return this;
        }

        public ArgumentsBuilder city(String city){

            this.args.put("city", city);

            return this;
        }

        public ArgumentsBuilder zip(String zip){

            this.args.put("zip", zip);

            return this;
        }

        public ArgumentsBuilder special_offer(String specialOffer){

            this.args.put("specialOffer", specialOffer);

            return this;
        }

        public ArgumentsBuilder item_id(String itemId){

            this.args.put("itemId", itemId);

            return this;
        }

        public ArgumentsBuilder count(String count){

            this.args.put("count", count);

            return this;
        }

        public ArgumentsBuilder num_items(String numItems){

            this.args.put("numItems", numItems);

            return this;
        }

        public ArgumentsBuilder facet_range(String range){

            this.args.put("facet.range", range);

            return this;
        }

        public ArgumentsBuilder category_id(String category_id){

            this.args.put("categoryId", category_id);

            return this;
        }

        public ArgumentsBuilder response_group(String responseGroup){

            this.args.put("responseGroup", responseGroup);

            return this;
        }

        public ArgumentsBuilder ids(String ids){

            this.args.put("ids", ids);

            return this;
        }
        @Override
        public Object build() {

            int args_counter = 0;

            StringBuilder args_builder = new StringBuilder();

            for (Map.Entry argument_entry : this.args.entrySet()){

                String key = argument_entry.getKey().toString();
                String value = argument_entry.getValue().toString();

                String key_value = key + "=" + value;

                if ((this.args.size() - 1) != args_counter){

                    key_value += "&";

                    args_builder.append(key_value);

                }else if (this.args.size() == 1 || (this.args.size() - 1) == args_counter) {

                    args_builder.append(key_value);
                }

                args_counter += 1;
            }

            this.arguments = args_builder.toString();

            return this.arguments;
        }
    }

    public static class EndpointBuilder implements Builder {

        private String walmartUrl;
        private TreeMap<Integer, String> endpointMap;


        public EndpointBuilder ()
        {

            endpointMap = new TreeMap<Integer, String>();
        }

        public EndpointBuilder item_id(String id){

            this.endpointMap.put(2, id);

            return this;
        }

        public EndpointBuilder feeds(){

            this.endpointMap.put(1, "feeds");

            return this;
        }

        public EndpointBuilder preorder(){

            this.endpointMap.put(2, "preorder");

            return this;
        }

        public EndpointBuilder bestsellers(){

            this.endpointMap.put(2, "bestsellers");

            return this;
        }

        public EndpointBuilder rollback(){

            this.endpointMap.put(2, "rollback");

            return this;
        }

        public EndpointBuilder specialbuy(){

            this.endpointMap.put(2, "specialbuy");

            return this;
        }

        public EndpointBuilder reviews(){

            this.endpointMap.put(1, "reviews");

            return this;
        }

        public EndpointBuilder postbrowse(){

            this.endpointMap.put(1, "postbrowse");

            return this;
        }

        public EndpointBuilder nbp(){

            this.endpointMap.put(1, "nbp");

            return this;
        }


        public EndpointBuilder stores(){

            this.endpointMap.put(1, "stores");

            return this;
        }


        public EndpointBuilder paginated(){

            this.endpointMap.put(1, "paginated/items");

            return this;
        }

        public EndpointBuilder itemSearch()
        {

            this.endpointMap.put(1, "items");
            return this;
        }

        public EndpointBuilder search()
        {

            try{

                this.endpointMap.put(1, "search");

            }catch (NullPointerException e){

                e.printStackTrace();
            }


            return this;
        }

        public EndpointBuilder taxonomy()
        {
            this.endpointMap.put(1, "taxonomy");

            return this;
        }


        @Override
        public Object build() {

            Set urlMapSet = this.endpointMap.entrySet();

            StringBuilder urlBuilder = new StringBuilder();

            urlBuilder.append(Base.walmart);

            Iterator iterator = urlMapSet.iterator();

            while (iterator.hasNext()){

                Map.Entry urlMapEntry = (Map.Entry) iterator.next();

                urlBuilder.append("/");
                urlBuilder.append(urlMapEntry.getValue());

            }

            this.walmartUrl = urlBuilder.toString();

            return this.walmartUrl;
        }
    }

    private Walmart(Walmart.EndpointBuilder builder)
    {
        try {

            this.url.put(1, builder.walmartUrl);

        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    private Walmart(Walmart.ArgumentsBuilder builder){

        try {

            this.url.put(2, builder.arguments);

        }catch (NullPointerException e){

            e.printStackTrace();
        }
    }


}
