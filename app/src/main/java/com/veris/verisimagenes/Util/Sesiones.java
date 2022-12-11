package com.veris.verisimagenes.Util;

import android.content.Context;
import android.content.SharedPreferences;

import com.veris.verisimagenes.Models.Login;
import com.veris.verisimagenes.Models.Sucursales;
import com.veris.verisimagenes.Models.TipoSucursal;
import com.veris.verisimagenes.Service.ApiModels.LoginResponse;


public class Sesiones {

    public static void guardarLogin(LoginResponse loginResponse, Context context, String IdOrganization, String Application){

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("secuenciaUsuario",loginResponse.data.secuenciaUsuario);
        editor.putInt("codigoProfesional",loginResponse.data.codigoProfesional);
        editor.putString("codigoUsuario",loginResponse.data.codigoUsuario);
        editor.putString("token",loginResponse.data.idToken);
        editor.putString("IdOrganization",IdOrganization);
        editor.putString("Application",Application);

        editor.apply();
    }

    public static void borrarLogin(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }


    public static Login obtieneDatosLogin(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_USUARIO", Context.MODE_PRIVATE);
        Login login = new Login();
        login.secuenciaUsuario = sharedPreferences.getInt("secuenciaUsuario",0);
        login.codigoUsuario = sharedPreferences.getString("codigoUsuario","");
        login.accessToken = sharedPreferences.getString("token","");
        login.Application = sharedPreferences.getString("Application","");
        login.IdOrganization = sharedPreferences.getString("IdOrganization","");
        login.codigoProfesional = sharedPreferences.getInt("codigoProfesional",0);
        return login;
    }

    public static void guardaTipoSucursal(Context context, TipoSucursal tipoSucursal) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_TIPO_SUCURSAL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("codigoTipoSucursal",tipoSucursal.codigoTipoSucursal);
        editor.putString("nombreTipoSucursal",tipoSucursal.nombreTipoSucursal);
        editor.putString("urlLogo",tipoSucursal.urlLogo);

        editor.apply();

    }

    public static TipoSucursal obtieneDatosTipoSucursal(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_TIPO_SUCURSAL", Context.MODE_PRIVATE);
        TipoSucursal tipoSucursal = new TipoSucursal();
        tipoSucursal.codigoTipoSucursal = sharedPreferences.getString("codigoTipoSucursal","");
        tipoSucursal.nombreTipoSucursal = sharedPreferences.getString("nombreTipoSucursal","");
        tipoSucursal.urlLogo = sharedPreferences.getString("urlLogo","");

        return tipoSucursal;
    }

    public static void guardaSucursal(Context context, Sucursales sucursales) {

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_SUCURSAL", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("codigoSucursal",sucursales.codigoSucursal);
        editor.putString("codigoTipoSucursal",sucursales.codigoTipoSucursal);
        editor.putString("nombreSucursal",sucursales.nombreSucursal);
        editor.putInt("codigoEmpresa",sucursales.codigoEmpresa);

        editor.apply();

    }

    public static Sucursales obtieneDatosSucursal(Context context){

        SharedPreferences sharedPreferences = context.getSharedPreferences("CRENDECIALES_SUCURSAL", Context.MODE_PRIVATE);
        Sucursales sucursales = new Sucursales();
        sucursales.codigoSucursal = sharedPreferences.getInt("codigoSucursal",0);
        sucursales.codigoEmpresa = sharedPreferences.getInt("codigoEmpresa",0);
        sucursales.nombreSucursal = sharedPreferences.getString("nombreSucursal","");
        sucursales.codigoTipoSucursal = sharedPreferences.getString("codigoTipoSucursal","");

        return sucursales;
    }

}
