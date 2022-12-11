package com.veris.verisimagenes.Service.ApiModels;


import com.veris.verisimagenes.Models.Ordenes;

import java.util.List;

public class OrdenesResponse {

    public Integer code;
    public boolean success;
    public String message;
    public List<Ordenes> data;

}
