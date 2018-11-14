package co.uglytruth.lindy.walmart;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by tjw127 on 11/8/17.
 */

public interface WalmartRetrofitService {

    @GET("/wtsearch?q=women&c=5438&n=25")
    Call<WTSearch> getSearchData();
}
