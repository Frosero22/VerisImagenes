package com.veris.verisimagenes.Activitys;

import android.os.Bundle;
import android.widget.Button;
import android.widget.GridView;

import androidx.appcompat.app.AppCompatActivity;


import com.veris.verisimagenes.Adapters.SucursalAdapter;
import com.veris.verisimagenes.Models.Login;
import com.veris.verisimagenes.Models.Sucursales;
import com.veris.verisimagenes.R;
import com.veris.verisimagenes.Service.ApiClient;
import com.veris.verisimagenes.Service.ApiModels.SucursalesResponse;
import com.veris.verisimagenes.Service.Endpoints;
import com.veris.verisimagenes.Util.Loaders;
import com.veris.verisimagenes.Util.Mensaje;
import com.veris.verisimagenes.Util.Routes;
import com.veris.verisimagenes.Util.Sesiones;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SucursalesActivity extends AppCompatActivity {

    private String codigoTipoSucursal;
    public static List<Sucursales> listaSucursal = new ArrayList();
    private GridView grid_sucursales;
    private SucursalAdapter sucursalAdapter;

    private Loaders loaders = new Loaders();
    private Endpoints service = ApiClient.getInstance();

    private Login objLogin = new Login();

    private Button btnAtras;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sucursales_activity);
        init();
    }

    public void init(){
        Bundle bundle = this.getIntent().getExtras();
        codigoTipoSucursal = bundle.getString("codigoTipoSucursal");
        objLogin = Sesiones.obtieneDatosLogin(SucursalesActivity.this);
        loaders.inicializaProgress(SucursalesActivity.this);

        grid_sucursales = findViewById(R.id.grid_sucursales);
        btnAtras = findViewById(R.id.btnAtras);
        btnAtras.setOnClickListener(view -> Routes.goToTipoSucursal(SucursalesActivity.this));


        listaSucursal.clear();
        listaSucursal = null;
        listaSucursal = new ArrayList<>();

        listaSucursales();
    }

    public void listaSucursales(){

        loaders.mensaje("Cargando...");
        loaders.muestraProgress();

        Call<SucursalesResponse> call = service.obtenerSucursales("Bearer "+objLogin.accessToken,
                                                                  objLogin.Application,
                                                                 "TODOS",
                                                                  objLogin.secuenciaUsuario);

        call.enqueue(new Callback<SucursalesResponse>() {
            @Override
            public void onResponse(Call<SucursalesResponse> call, Response<SucursalesResponse> response) {
                try{

                    loaders.cierraProgress();

                    if(response.code() == 200){

                        if(response.body().data != null){
                            if(response.body().data.size() > 0){

                                listaSucursal.addAll(response.body().data);
                                sucursalAdapter = new SucursalAdapter(listaSucursal, SucursalesActivity.this);
                                grid_sucursales.setAdapter(sucursalAdapter);

                            }
                        }
                    }else{

                        Mensaje.mensaje(SucursalesActivity.this,"Ocurrió un error: "+response.body().message);

                    }





                }catch (Exception e){
                    e.printStackTrace();
                    Mensaje.mensaje(SucursalesActivity.this,"Ocurrió un error: "+e.getLocalizedMessage());
                }
            }

            @Override
            public void onFailure(Call<SucursalesResponse> call, Throwable t) {
                loaders.cierraProgress();
                Mensaje.mensaje(SucursalesActivity.this,"Ocurrió un error: "+t.getLocalizedMessage());
            }
        });

    }
}