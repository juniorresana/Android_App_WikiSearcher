package com.example.a0.WikiSearcher.Internet;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.annotation.NonNull;

public class CheckConnection {

    public static boolean check(@NonNull Context context){
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
      return   (connectivityManager.getActiveNetworkInfo()!=null);
    }
}
