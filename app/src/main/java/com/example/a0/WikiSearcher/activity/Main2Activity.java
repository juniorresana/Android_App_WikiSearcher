package com.example.a0.WikiSearcher.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.a0.WikiSearcher.Internet.HttpQuery;
import com.example.a0.WikiSearcher.R;

import org.json.JSONObject;

public class Main2Activity extends AppCompatActivity implements HttpQuery.DOHttp {

    String url = "https://"+MainActivity.langWiki+".wikipedia.org/w/api.php?action=query&prop=extracts&explaintext=2&format=json&utf8";
    String pageId;

    TextView textView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        textView = findViewById(R.id.activit_main2_textview);

        Intent intent = getIntent();
        pageId = intent.getStringExtra("pageId");
        TextView textViewTitle = findViewById(R.id.activit_main2_textview_title);
        textViewTitle.setText(intent.getStringExtra("title"));

        HttpQuery httpQuery = new HttpQuery(Main2Activity.this);
        httpQuery.execute(url+"&pageids="+pageId);

    }

    public String getText(String document) throws Exception{



        JSONObject jsonObject = new JSONObject(document);
        jsonObject = jsonObject.getJSONObject("query").getJSONObject("pages");
        jsonObject = jsonObject.getJSONObject(pageId);


        if (jsonObject.has("extract")) {


            String extract = jsonObject.getString("extract");
            extract = extract.replaceAll("(\\\\n\\\\n+|==)", "");
            return extract;
        } else{
            return null;
        }
    }

    @Override
    public void doSomething(String text) throws Exception {
        TextView textView = findViewById(R.id.activit_main2_textview);
        textView.setText(getText(text));



    }


    public void sharetext(View view) {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT,
                "this text shared from app: https://play.google.com/store/apps/details?id=" + getPackageName() +
                         "\n\n\n" +
                textView.getText().toString());
        sendIntent.setType("text/plain");
        startActivity(sendIntent);
    }
}
