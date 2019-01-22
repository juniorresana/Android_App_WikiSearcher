package com.example.a0.WikiSearcher.activity;

import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Pair;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a0.WikiSearcher.Model.Items;
import com.example.a0.WikiSearcher.Model.ItemsAdapter;
import com.example.a0.WikiSearcher.Model.JsonListener;
import com.example.a0.WikiSearcher.Model.Langs;
import com.example.a0.WikiSearcher.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    public static View rootView;
    public static Context context;
    public static String langWiki = "en";
    public static SharedPreferences mainSharedPreferences;

    static ArrayList<Items> searchList;
    ListView listView;
    static ItemsAdapter itemsAdapter;
    String wiki = "";
    ArrayList<AsyncTask> listTask;
    static EditText editText;

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainSharedPreferences = getSharedPreferences("pageids", MODE_PRIVATE);


        rootView = findViewById(android.R.id.content);
        context = getBaseContext();
        searchList = new ArrayList<>();

        listView = findViewById(R.id.list);
        itemsAdapter = new ItemsAdapter(this, R.layout.items, searchList);
        listView.setAdapter(itemsAdapter);


        editText = findViewById(R.id.edit);

        listTask = new ArrayList<>();

        initTextWatcher();

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Items items = (Items) parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, items.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Main2Activity.class);
                intent.putExtra("pageId", items.getPageId());
                intent.putExtra("title", items.getTitle());



                Pair pair = new Pair<View, String>(view, "trans");

                ActivityOptions activityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, pair);
                startActivity(intent, activityOptions.toBundle());
            }
        });

        View.OnTouchListener onTouchListener = new View.OnTouchListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                editText.requestFocus();
                editText.clearFocus();
                InputMethodManager inputMethodManager = (InputMethodManager) getBaseContext().getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
                String loc = inputMethodManager.getCurrentInputMethodSubtype().getLocale();
                Locale l = new Locale(loc);


                if (!Arrays.asList(Langs.langs).contains(loc)) {

                }

                inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
                return false;
            }
        };

        TextView textViewt1 = findViewById(R.id.t1);
        TextView textViewt2 = findViewById(R.id.t2);

        textViewt1.setOnTouchListener(onTouchListener);
        textViewt2.setOnTouchListener(onTouchListener);
        textViewt1.setOnClickListener(intent);
        textViewt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT,
                        "Wikipedia Reader app: https://play.google.com/store/apps/details?id=" + getPackageName());
                sendIntent.setType("text/plain");
                startActivity(sendIntent);
            }
        });

        listView.setOnTouchListener(onTouchListener);


    }


    //////////////////////////////////
    ////////////////////////////////////


    public void searchWord(String s) {
        Load load1 = new Load();

        if (listTask.size() != 0) {

            for (AsyncTask asyncTask : listTask) {
                if (asyncTask != load1) {
                    asyncTask.cancel(true);
                }
            }
            listTask.add(load1.execute(s));
        } else {
            listTask.add(load1.execute(s));
        }
    }

    ///////////////////////////////////////////


    public void updateListView(ArrayList arrayList) {
        ListView listView = findViewById(R.id.list);
        if (arrayList != null && arrayList.size() != 0)
            listView.setAdapter(new ItemsAdapter(getBaseContext(), R.layout.items, arrayList));
    }

    public void clearedit(View view) {
        editText.getText().clear();
    }

    ////////////////////////////////////////////


    public class Load extends AsyncTask<String, Void, ArrayList> {
        @Override
        protected ArrayList doInBackground(String... strings) {
            ArrayList list = null;
            JsonListener jsonListener = new JsonListener();
            InputMethodManager inputMethodManager = (InputMethodManager) getBaseContext().getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
            String loc = inputMethodManager.getCurrentInputMethodSubtype().getLocale();

            if (!Arrays.asList(Langs.langs).contains(loc)) {
                if (Langs.intellectualLang(loc, Langs.langs) != null) {
                    langWiki = Langs.intellectualLang(loc, Langs.langs);
                } else {
                    langWiki = "en";
                }
            } else {
                langWiki = loc;
            }

            try {
                String s = "https://" + langWiki + ".wikipedia.org/w/api.php?action=query&generator=search&prop=extracts&gsrsearch=" + strings[0] + "&gsrlimit=20&exintro=1&explaintext=1&exchars=350&exlimit=20&format=json&utf8";

                jsonListener = new JsonListener();
                list = jsonListener.build(s);
            } catch (Exception e) {

            }
            return list;
        }

        @Override
        protected void onPostExecute(ArrayList arrayList) {
            if (isCancelled() == false) {
                updateListView(arrayList);
            }
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
        }
    }

    //////////////////////////////////////////////////////

    void initTextWatcher() {
        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                try {
                    searchWord(URLEncoder.encode(s.toString(), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    Toast.makeText(MainActivity.this, "error encode", Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText e = editText;

            }
        };
        editText.addTextChangedListener(textWatcher);

    }

//    void initSpinner(){
//        final SharedPreferences sharedPreferences = getSharedPreferences("position", MODE_PRIVATE);
//        final SharedPreferences.Editor editor = sharedPreferences.edit();
//
//
//        Spinner spinner = findViewById(R.id.spinner);
//        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, Langs.langs);
//        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinner.setAdapter(arrayAdapter);
//
//        if (sharedPreferences.contains("pos")){
//
//            spinner.setSelection(sharedPreferences.getInt("pos", 0));
//            langWiki = Langs.langs[sharedPreferences.getInt("pos", 0)];
//            if (langWiki.equals("auto")){
//                InputMethodManager inputMethodManager = (InputMethodManager) getBaseContext().getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
//                langWiki = inputMethodManager.getCurrentInputMethodSubtype().getLocale();
//            }
//        }
//        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                langWiki = Langs.langs[position];
//                if (langWiki.equals("auto")){
//                    InputMethodManager inputMethodManager = (InputMethodManager) getBaseContext().getSystemService(getBaseContext().INPUT_METHOD_SERVICE);
//                    langWiki = inputMethodManager.getCurrentInputMethodSubtype().getLocale();
//                }
//
//                editor.putInt("pos", (position));
//                editor.commit();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });
//    }

    View.OnClickListener intent = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(MainActivity.this, Main3Activity.class));
        }
    };


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}

