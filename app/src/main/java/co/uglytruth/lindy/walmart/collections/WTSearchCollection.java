package co.uglytruth.lindy.walmart.collections;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;

import co.uglytruth.lindy.Webservice.WebserviceAsyncTask;
import co.uglytruth.lindy.Webservice.contenttype.ContentType;
import co.uglytruth.lindy.Webservice.requestproperty.RequestProperty;
import co.uglytruth.lindy.Webservice.test.WebserviceTest;
import co.uglytruth.lindy.Webservice.type.WebserviceCollectionType;
import co.uglytruth.lindy.Webservice.type.WebserviceExecuteType;
import co.uglytruth.lindy.Webservice.type.WebserviceType;
import co.uglytruth.lindy.base.Base;
import co.uglytruth.lindy.walmart.WTSearch;
import co.uglytruth.lindy.walmart.WalmartRetrofitService;
import co.uglytruth.lindy.walmart.WalmartUrl;
import co.uglytruth.lindy.walmart.params.WTSearchParams;
import co.uglytruth.lindy.walmart.response.WTSearchResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tjw127 on 6/21/17.
 */

public class WTSearchCollection {

    public static class Builder{

        private String q;

        private String c;

        private String p;

        private String s;

        private String st;

        private String o;

        private String n;

        private String f;

        private String ft;

        private String ftf;

        private String fr;

        private String r;

        private String a;

        private Context context;

        private RequestProperty requestProperty;

        private WalmartUrl walmartUrl;

        private WebserviceExecuteType executeType;

        public Builder()
        {
            q = "";

            c = "";

            p = "";

            s = "";

            st = "";

            o = "";

            n = "";

            f = "";

            ft = "";

            ftf = "";

            fr = "";

            r = "";

            a = "";

        }

        public Builder q(String q){

            this.q += q;

            return this;
        }

        public Builder c(String c)
        {
            this.c += c;

            return this;
        }

        public Builder p(String p)
        {
            this.p += p;

            return this;
        }

        public Builder s(String s)
        {
            this.s += s;

            return this;
        }

        public Builder st(String st)
        {
            this.st += st;

            return this;
        }

        public Builder o(String o)
        {
            this.o += o;

            return this;
        }

        public Builder n(String n)
        {
            this.n += n;

            return this;
        }

        public Builder f(String f)
        {
            this.f += f;

            return this;
        }

        public Builder ft(String ft)
        {
            this.ft += ft;

            return this;
        }

        public Builder ftf(String ftf)
        {
            this.ftf += ftf;

            return this;
        }

        public Builder fr(String fr)
        {
            this.fr += fr;

            return this;
        }

        public Builder r(String r)
        {
            this.r += r;

            return this;
        }

        public Builder a(String a)
        {
            this.a += a;

            return this;
        }

        public Builder context(Context context)
        {
            this.context = context;

            return this;
        }

        public Builder executeType(WebserviceExecuteType type)
        {
            this.executeType = type;

            return this;
        }

        public Builder walmartUrl()
        {
            WalmartUrl walmartUrl = new WalmartUrl.Builder().wtsearch().build();

            this.walmartUrl = walmartUrl;

            return this;
        }

        public Builder requestProperty()
        {
            RequestProperty requestProperty = new RequestProperty.Builder().contentType(ContentType.JSON).accept(ContentType.JSON.toString()).accept_charset("UTF-8").build();

            this.requestProperty = requestProperty;

            return this;
        }

        public WTSearchCollection build()
        {

            WTSearchParams.Builder builder = new WTSearchParams.Builder();

            try{

                if (this.q.length() > 0)
                {
                    builder.query(this.q);
                }

            }catch (Exception e){

                e.printStackTrace();
            }


            try {

                if (this.c.length() > 0)
                {
                    builder.categoryId(this.c);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }


            try {

                if (this.p.length() > 0)
                {
                    builder.publisherId(p);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }


            try {

                if (this.s.length() > 0)
                {
                    builder.start(this.s);
                }


            }catch (Exception e)
            {
                e.printStackTrace();
            }

            try {

                if (this.st.length() > 0)
                {
                    builder.sort(this.st);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            try {

                if (this.o.length() > 0)
                {
                    builder.order(this.o);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }


            try {

                if (this.n.length() > 0)
                {
                    builder.numItems(this.n);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }


            try {

                if (this.f.length() > 0)
                {
                    builder.format(this.f);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            try {

                if (this.ft.length() > 0)
                {
                    builder.facet(this.ft);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }


            try {

                if (this.ftf.length() > 0)
                {
                    builder.facet_filter(this.ftf);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }



            try {

                if (this.fr.length() > 0)
                {
                    builder.facet_range(this.fr);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            try {

                if (this.r.length() > 0)
                {
                    builder.responseGroup(this.r);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }

            try {

                if (this.a.length() > 0)
                {
                    builder.apiKey(this.a);
                }

            }catch (Exception e)
            {
                e.printStackTrace();
            }


            WTSearchParams params = builder.build();


            WebserviceAsyncTask.Builder webServiceBuilder = new WebserviceAsyncTask.Builder();

            webServiceBuilder.url(this.walmartUrl.url + "?" + params.parameters);

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(Base.walmart)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            WalmartRetrofitService walmartRetrofitService = retrofit.create(WalmartRetrofitService.class);

           Call<WTSearch> search = walmartRetrofitService.getSearchData();

           search.enqueue(new Callback<WTSearch>() {
               @Override
               public void onResponse(Call<WTSearch> call, Response<WTSearch> response) {

                   if (response.isSuccessful())
                   {
                       WTSearch wtSearch = response.body();

                       Log.v("RetrofitSearch", " " + wtSearch.numItems);
                   }else {

                       Log.v("RetrofitSearch", " Failed");
                   }
               }

               @Override
               public void onFailure(Call<WTSearch> call, Throwable t) {

               }
           });


            if (this.requestProperty.requestPropertyMap != null)
            {
                webServiceBuilder.requestProperty(this.requestProperty.requestPropertyMap);

            }else {

                throw new NullPointerException();
            }

            if (this.context != null)
            {
                webServiceBuilder.context(this.context);
            }

            webServiceBuilder.webserviceType(WebserviceCollectionType.WTSEARCHCOLLECTION);

            webServiceBuilder.executeType(this.executeType);

            WebserviceAsyncTask webserviceAsyncTask = webServiceBuilder.build();

            return new WTSearchCollection(this);

        }

    }

    public WTSearchCollection(Builder builder)
    {


    }
}
