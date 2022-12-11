package com.veris.verisimagenes.Util;

public class MesUtil {

    public static String obtenerMes(int mes){
        String result = "";

        switch(mes){
            case 1:
            {
                result="Enero";
                break;
            }
            case 2:
            {
                result="Febrero";
                break;
            }
            case 3:
            {
                result="Marzo";
                break;
            }
            case 4:
            {
                result="Abril";
                break;
            }
            case 5:
            {
                result="Mayo";
                break;
            }
            case 6:
            {
                result="Junio";
                break;
            }
            case 7:
            {
                result="Julio";
                break;
            }
            case 8:
            {
                result="Agosto";
                break;
            }
            case 9:
            {
                result="Septiembre";
                break;
            }
            case 10:
            {
                result="Octubre";
                break;
            }
            case 11:
            {
                result="Noviembre";
                break;
            }
            case 12:
            {
                result="Diciembre";
                break;
            }
            default:
            {
                result="Error";
                break;
            }
        }

        return result;
    }

}
