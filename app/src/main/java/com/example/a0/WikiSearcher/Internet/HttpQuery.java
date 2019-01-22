package com.example.a0.WikiSearcher.Internet;

import android.os.AsyncTask;

import com.example.a0.WikiSearcher.Model.JsonListener;
import com.example.a0.WikiSearcher.activity.Main2Activity;

public class HttpQuery extends AsyncTask<String, Void, String> {

    Main2Activity main2Activity;
    private String url;
    private String pageId;

    public HttpQuery(Main2Activity main2Activity) {
        this.main2Activity = main2Activity;
    }

    @Override
    protected String doInBackground(String... strings) {
        String s = null;
        try {
            s = new JsonListener().getHttpString((strings[0]));
        } catch (Exception e) {

        }


        return s;
    }

    @Override
    protected void onPostExecute(String s) {
        try {
            main2Activity.doSomething(s);
        } catch (Exception e) {

        }

    }

    public interface DOHttp {
        public void doSomething(String s) throws Exception;
    }
}
