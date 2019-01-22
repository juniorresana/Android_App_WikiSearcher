package com.example.a0.WikiSearcher.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.a0.WikiSearcher.Model.DBHelper;
import com.example.a0.WikiSearcher.Model.Items;
import com.example.a0.WikiSearcher.Model.ItemsAdapter;
import com.example.a0.WikiSearcher.R;

public class Main3Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        ListView listView = findViewById(R.id.activity3_listview);
        DBHelper dbHelper = new DBHelper(getApplicationContext());
        ItemsAdapter itemsAdapter = new ItemsAdapter(getApplicationContext(), R.layout.items, dbHelper.getAll());
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Items items = (Items) parent.getItemAtPosition(position);
                Toast.makeText(Main3Activity.this, items.getTitle(), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(Main3Activity.this, Main2Activity.class);
                intent.putExtra("pageId", items.getPageId());
                intent.putExtra("title", items.getTitle());
                startActivity(intent);
            }
        });
    }
}
