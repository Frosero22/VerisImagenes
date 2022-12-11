package com.veris.verisimagenes.Util;

import android.app.ProgressDialog;
import android.content.Context;

public class Loaders {

    public static ProgressDialog progressDialog;


    public void inicializaProgress(Context context){
        progressDialog = new ProgressDialog(context);
        progressDialog.setCancelable(false);


    }

    public void muestraProgress(){
        progressDialog.show();
    }

    public  void mensaje(String mensaje){

        progressDialog.setMessage(mensaje);
    }

    public void cierraProgress(){
        progressDialog.dismiss();
    }

}
