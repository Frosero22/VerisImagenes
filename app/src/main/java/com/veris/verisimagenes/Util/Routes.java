package com.veris.verisimagenes.Util;

import android.content.Context;
import android.content.Intent;

import com.veris.verisimagenes.Activitys.AgendaDelDiaActivity;
import com.veris.verisimagenes.Activitys.LectorPDFActivity;
import com.veris.verisimagenes.Activitys.LoginActivity;
import com.veris.verisimagenes.Activitys.SucursalesActivity;
import com.veris.verisimagenes.Activitys.TipoSucursalActivity;

import java.io.IOException;

public class Routes {

    public static void goToLogin(Context context){
        Intent intent = new Intent(context, LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goToTipoSucursal(Context context){
        Intent intent = new Intent(context, TipoSucursalActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goToSucursales(Context context,String codigoTipoSucursal){
        Intent intent = new Intent(context, SucursalesActivity.class);
        intent.putExtra("codigoTipoSucursal",codigoTipoSucursal);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goToAgenda(Context context){
        Intent intent = new Intent(context, AgendaDelDiaActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    public static void goToPDFView(Context context, String file, String nombre) throws IOException {
        Intent intent = new Intent(context, LectorPDFActivity.class);
        intent.putExtra("data",file);
        intent.putExtra("nombre",nombre);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

}
