package com.veris.verisimagenes.Activitys;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.textfield.TextInputEditText;
import com.veris.verisimagenes.Models.Login;
import com.veris.verisimagenes.Models.Sucursales;
import com.veris.verisimagenes.R;
import com.veris.verisimagenes.Service.ApiClient;
import com.veris.verisimagenes.Service.ApiModels.LoginResponse;
import com.veris.verisimagenes.Service.Endpoints;
import com.veris.verisimagenes.Util.GenHeaders;
import com.veris.verisimagenes.Util.Loaders;
import com.veris.verisimagenes.Util.Mensaje;
import com.veris.verisimagenes.Util.Routes;
import com.veris.verisimagenes.Util.Sesiones;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    private EditText edUsuario;
    private TextInputEditText edClave;
    private Button btn_login;

    private Endpoints service = ApiClient.getInstance();
    private Loaders loaders = new Loaders();

    private Login login = new Login();
    private Sucursales objSucursales = new Sucursales();

    //**Credenciales desarrollo:**

    private String Application = "UEhBTlRPTVhfV0VC";
    private String IdOrganization = "365509c8-9596-4506-a5b3-487782d5876e";

    //**Credenciales test:**

    //private String Application = "UEhBTlRPTVhfV0VC";
    //private String IdOrganization = "adf4e264-cd20-4653-9a44-025b13050992";

    //**Credenciales producción:**

    //private String Application = "UEhBTlRPTVhfV0VC";
    //private String IdOrganization = "365509c8-9596-4506-a5b3-487782d5876e";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        init();
    }

    public void init(){
        loaders.inicializaProgress(LoginActivity.this);
        login = Sesiones.obtieneDatosLogin(LoginActivity.this);

        objSucursales = Sesiones.obtieneDatosSucursal(LoginActivity.this);

        if(login.accessToken != null){
            if(!login.accessToken.equalsIgnoreCase("")){
                if(objSucursales.codigoSucursal != 0){
                    Routes.goToAgenda(LoginActivity.this);
                }
            }
        }

        edUsuario = findViewById(R.id.edUsuario);
        edClave = findViewById(R.id.edClave);
        btn_login = findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(validaCampos()){
                    login();
                }

            }
        });
    }

    public boolean validaCampos(){

        if(edUsuario.getText() == null || edUsuario.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Usuario es Obligatorio", Toast.LENGTH_SHORT).show();
            return false;
        }

        if(edClave.getText() == null || edClave.getText().toString().equalsIgnoreCase("")){
            Toast.makeText(this, "Clave es Obligatoria", Toast.LENGTH_SHORT).show();
            return false;
        }


        return true;
    }

    public void login(){

        loaders.mensaje("Cargando ...");
        loaders.muestraProgress();

        String header = GenHeaders.generarHeader(LoginActivity.this,edUsuario.getText().toString().toUpperCase(),edClave.getText().toString());
        Call<LoginResponse> call = service.login(this.Application,header);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                loaders.cierraProgress();
                if(response.code() == 200){
                    if(response.body() != null){
                        if(response.body().data.estadoUsuario.equalsIgnoreCase("CONFIRMED")){
                            Sesiones.guardarLogin(response.body(),LoginActivity.this,IdOrganization,Application);
                            Routes.goToTipoSucursal(LoginActivity.this);
                        }else{
                            Mensaje.mensaje(LoginActivity.this,"El estado del usuario es "+response.body().data.estadoUsuario+" por favor validar.");
                        }
                    }


                }else{
                    Mensaje.mensaje(LoginActivity.this,"Usuario y claves incorrectos");
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                loaders.cierraProgress();
                Mensaje.mensaje(LoginActivity.this,"Ocurrió un error: "+t.getLocalizedMessage());
            }
        });
    }

}