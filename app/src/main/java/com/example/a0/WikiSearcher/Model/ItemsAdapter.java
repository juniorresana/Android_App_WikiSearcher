package com.example.a0.WikiSearcher.Model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.a0.WikiSearcher.R;
import com.example.a0.WikiSearcher.activity.MainActivity;

import java.util.ArrayList;

public class ItemsAdapter extends ArrayAdapter<Items> {

    private ArrayList<Items> list;
    private int res;
    SharedPreferences.Editor editor;

    public ItemsAdapter(Context context,int resource, ArrayList<Items> list) {
        super(context, resource);
        this.list = list;
        this.res = resource;
        this.editor = MainActivity.mainSharedPreferences.edit();
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Items getItem(int position) {
        return list.get(position);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView==null){
            convertView = LayoutInflater.from(getContext()).inflate(res, parent,false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        final Items items = list.get(position);
        viewHolder.textViewTitle.setText(items.getTitle());
        viewHolder.textViewDescription.setText(items.getDescription());

        if (MainActivity.mainSharedPreferences.contains(items.getPageId())){
            viewHolder.textViewStar.setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_black_24dp));
        }

        viewHolder.textViewStar.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                if (!MainActivity.mainSharedPreferences.contains(items.getPageId())) {
                    ((TextView) v).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_black_24dp));
                    new DBHelper(getContext()).write(items);
                    editor.putString(items.getPageId(), items.getPageId());
                    editor.commit();

                }else {
                    editor.remove(items.getPageId());
                    editor.commit();
                    new DBHelper(getContext()).delete(items.getPageId());
                    ((TextView) v).setBackground(ContextCompat.getDrawable(getContext(), R.drawable.ic_star_border_empty_24dp));

                }
            }
        });



        return convertView;
    }

    private class ViewHolder{
        final TextView textViewTitle;
        final TextView textViewDescription;
        final TextView textViewStar;
        ViewHolder(View view){
            textViewTitle = view.findViewById(R.id.items_title);
            textViewDescription = view.findViewById(R.id.items_description);
            textViewStar = view.findViewById(R.id.items_star);
        }
    }
}
