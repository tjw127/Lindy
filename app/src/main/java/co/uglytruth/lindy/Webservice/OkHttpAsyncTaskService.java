package co.uglytruth.lindy.Webservice;

import android.os.AsyncTask;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class OkHttpAsyncTaskService extends AsyncTask<Object, Object, Object> {
    private String url;
    private OkHttpClient client;
//    public OkHttpAsyncTaskResponse okHttpAsyncTaskResponse;
    public OkHttpAsyncTaskService(String url){
        this.url = url;
    }
    @Override
    protected Object doInBackground(Object... objects) {

        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = null;
        try {
            response = client.newCall(request).execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            return response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
//        this.okHttpAsyncTaskResponse.onOkHttpAsyncTaskFinished(o);
    }
}
