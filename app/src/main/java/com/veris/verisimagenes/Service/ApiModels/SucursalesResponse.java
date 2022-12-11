package com.veris.verisimagenes.Service.ApiModels;


import com.veris.verisimagenes.Models.Sucursales;

import java.util.List;

public class SucursalesResponse {

    public Integer code;
    public boolean success;
    public String message;
    public List<Sucursales> data;
}
