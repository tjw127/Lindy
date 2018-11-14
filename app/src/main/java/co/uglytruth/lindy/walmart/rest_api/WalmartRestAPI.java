package co.uglytruth.lindy.walmart.rest_api;

import android.util.Log;

import co.uglytruth.lindy.abstract_classes.WalmartRestApiTemplate;
import co.uglytruth.lindy.credentials.Credentials;
import co.uglytruth.lindy.walmart.Walmart;

public class WalmartRestAPI {

    public static class Search extends WalmartRestApiTemplate{

        private Walmart.ArgumentsBuilder argumentsBuilder;
        private Walmart.EndpointBuilder endpointBuilder;
        private Walmart.RequestBuilder requestBuilder;

        public Search(Walmart.EndpointBuilder endpointBuilder, Walmart.ArgumentsBuilder argumentsBuilder, Walmart.RequestBuilder requestBuilder){

            this.argumentsBuilder = argumentsBuilder;
            this.endpointBuilder = endpointBuilder;
            this.requestBuilder = requestBuilder;

        }
        @Override
        protected Object stepOne() {
            return this.endpointBuilder.search().build();

        }

        @Override
        protected Object stepTwo(Object step_one) {

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(String.valueOf(step_one));
            stringBuilder.append("?");
            this.argumentsBuilder.apiKey(Credentials.WALMART_KEY);
            this.argumentsBuilder.lsPublisherId(Credentials.Walmart_Publisher_Id);

            stringBuilder.append(String.valueOf(this.argumentsBuilder.build()));

            Log.d("WalmartRestApi", stringBuilder.toString());
            return stringBuilder.toString();
        }

        @Override
        protected Object stepThree(Object step_two) {

            this.requestBuilder.url((String)step_two);
            return this.requestBuilder.build();
        }
    }

}
