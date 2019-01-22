package com.example.a0.WikiSearcher.Model;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    private Context context;

    public DBHelper(Context context) {
        super(context, "dbItems", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table tab (title text, description text, pageid text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public ArrayList<Items> getAll() {
        ArrayList<Items> itemsArrayList = new ArrayList<>();
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        Cursor cursor = sqLiteDatabase.query("tab", null, null, null, null, null, null, null);
        while (cursor.moveToNext()) {
            itemsArrayList.add(new Items(
                    cursor.getString(cursor.getColumnIndex("title")),
                    cursor.getString(cursor.getColumnIndex("description")),
                    cursor.getString(cursor.getColumnIndex("pageid"))
            ));
        }

        return itemsArrayList;
    }

    public void write(Items items) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("title", items.getTitle());
        contentValues.put("description", items.getDescription());
        contentValues.put("pageid", items.getPageId());
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.insert("tab", null, contentValues);

    }

    public void delete(String pageid) {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.delete("tab", "pageid='" + pageid + "'", null);
    }
}
