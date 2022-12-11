package com.veris.verisimagenes.Util;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import java.util.HashMap;

public class GenHeaders {

    public static String generarHeader(Context context, String usuario, String clave) {
        HashMap<String, String> params = new HashMap<String, String>();
        String auth = "";
        if(usuario != null && clave != null){
            String credentials = usuario + ":" + clave;
            auth =  "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.URL_SAFE|Base64.NO_WRAP);
            //auth = new String(Base64.encode((usuario+":"+clave).getBytes(), Base64.URL_SAFE|Base64.NO_WRAP));
            Log.d(TAG, auth);
        }

        params.put("Authorization", auth);

        return auth;
    }

}
