package com.myexplorer.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Validation {	
	// ≈–∂œÕ¯¬Á «∑Òø…”√
	public static boolean isNetAvailable(Context context) {  
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);    
        NetworkInfo info = manager.getActiveNetworkInfo();  
        return (info != null && info.isAvailable());  
    }  
	
}
