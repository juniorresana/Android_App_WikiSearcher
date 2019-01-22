package com.example.a0.WikiSearcher.Model;

import android.support.design.widget.Snackbar;

import com.example.a0.WikiSearcher.Internet.CheckConnection;
import com.example.a0.WikiSearcher.activity.MainActivity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class JsonListener {


    public String getHttpString(String url) throws Exception {

        HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(url).openConnection();
        httpURLConnection.setConnectTimeout(5000);
        httpURLConnection.connect();

        if (!CheckConnection.check(MainActivity.context)) {
            Snackbar.make(MainActivity.rootView, "no internet", Snackbar.LENGTH_SHORT).show();
        }

        InputStream inputStream = httpURLConnection.getInputStream();
        Scanner scanner = new Scanner(new InputStreamReader(inputStream));

        StringBuilder stringBuilder = new StringBuilder();
        String line;
        while (scanner.hasNextLine()) {
            line = (scanner.nextLine());

            stringBuilder.append(line);
        }
        scanner.close();
        return stringBuilder.toString();
    }

    public static String urlEncoder(String s) throws Exception {
        return URLEncoder.encode(s, "UTF-8");
    }

    public JSONObject getJson(String s) throws Exception {
        JSONObject jsonObject = new JSONObject(s).getJSONObject("query").getJSONObject("pages");
        return jsonObject;
    }

    ArrayList<String> getKeys(JSONObject jsonObject) {
        ArrayList<String> list = new ArrayList<>();
        Iterator iterator = jsonObject.keys();
        while (iterator.hasNext()) {
            list.add(iterator.next().toString());
        }
        return list;
    }

    public String parseContain(JSONObject jsonObject) {
        jsonObject.keys();
        return null;
    }

    public ArrayList<Items> build(String url) throws Exception {
        ArrayList<Items> arrayList = new ArrayList<>();
        String line = getHttpString(url);
        JSONObject jsonObject = getJson(line);
        ArrayList keys = getKeys(jsonObject);

        JSONArray names = (jsonObject.names());

        for (int i = 0; i < names.length(); i++) {
            JSONObject j = (JSONObject) jsonObject.getJSONObject(names.get(i).toString());
            if (j.has("title") && j.has("extract") && j.has("pageid")) {
                arrayList.add(new Items(j.getString("title"), j.getString("extract"), j.getString("pageid")));
            }
        }


        return arrayList;
    }
}
