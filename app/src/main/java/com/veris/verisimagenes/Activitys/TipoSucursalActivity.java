package com.veris.verisimagenes.Activitys;

import android.os.Bundle;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;

import com.veris.verisimagenes.Adapters.TiposSucursalAdapter;
import com.veris.verisimagenes.Models.Login;
import com.veris.verisimagenes.Models.TipoSucursal;
import com.veris.verisimagenes.R;
import com.veris.verisimagenes.Service.ApiClient;
import com.veris.verisimagenes.Service.ApiModels.TiposSucursalResponse;
import com.veris.verisimagenes.Service.Endpoints;
import com.veris.verisimagenes.Util.Loaders;
import com.veris.verisimagenes.Util.Mensaje;
import com.veris.verisimagenes.Util.Sesiones;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TipoSucursalActivity extends AppCompatActivity {

    private TiposSucursalAdapter tiposSucursalAdapter;
    public static List<TipoSucursal> listaTiposSucursal = new ArrayList();
    private GridView grid_tipos_sucursales;

    private Loaders loaders = new Loaders();
    private Endpoints service = ApiClient.getInstance();

    private Login objLogin = new Login();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipo_sucursal_activity);
        init();
    }

    public void init(){
        loaders.inicializaProgress(TipoSucursalActivity.this);
        objLogin = Sesiones.obtieneDatosLogin(TipoSucursalActivity.this);
        grid_tipos_sucursales = findViewById(R.id.grid_tipos_sucursales);
        tiposSucursalAdapter = new TiposSucursalAdapter(listaTiposSucursal, TipoSucursalActivity.this);

        grid_tipos_sucursales.setAdapter(tiposSucursalAdapter);

        listaTiposSucursal.clear();
        listaTiposSucursal = null;
        listaTiposSucursal = new ArrayList<>();

        listarTiposSucursal();
    }

    public void listarTiposSucursal(){

        loaders.mensaje("Cargando ...");
        loaders.muestraProgress();

        Call<TiposSucursalResponse> call = service.obtenerTipoSucursales(objLogin.Application,
                                                                         objLogin.IdOrganization,
                                                                         "Bearer "+objLogin.accessToken);

        call.enqueue(new Callback<TiposSucursalResponse>() {
            @Override
            public void onResponse(Call<TiposSucursalResponse> call, Response<TiposSucursalResponse> response) {
                try{

                    loaders.cierraProgress();

                    if(response.code() == 200){

                        if(response.body().data != null){
                            if(response.body().data.size() > 0){

                                listaTiposSucursal.addAll(response.body().data);
                                tiposSucursalAdapter = new TiposSucursalAdapter(listaTiposSucursal, TipoSucursalActivity.this);
                                grid_tipos_sucursales.setAdapter(tiposSucursalAdapter);

                            }
                        }
                    }else{

                        Mensaje.mensaje(TipoSucursalActivity.this,"Ocurrió un error: "+response.body().message);

                    }





                }catch (Exception e){
                    e.printStackTrace();
                    Mensaje.mensaje(TipoSucursalActivity.this,"Ocurrió un error: "+e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<TiposSucursalResponse> call, Throwable t) {
                loaders.cierraProgress();
                Mensaje.mensaje(TipoSucursalActivity.this,"Ocurrió un error: "+t.getLocalizedMessage());
            }
        });

    }
}