package com.veris.verisimagenes.Activitys;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.MenuItemCompat;

import com.github.barteksc.pdfviewer.PDFView;
import com.google.gson.Gson;
import com.veris.verisimagenes.Adapters.AgendaDelDiaAdapter;
import com.veris.verisimagenes.Adapters.DetalleOrdenAdapter;
import com.veris.verisimagenes.Models.DetalleOrden;
import com.veris.verisimagenes.Models.Login;
import com.veris.verisimagenes.Models.Ordenes;
import com.veris.verisimagenes.Models.Sucursales;
import com.veris.verisimagenes.R;
import com.veris.verisimagenes.Service.ApiClient;
import com.veris.verisimagenes.Service.ApiClientNgrok;
import com.veris.verisimagenes.Service.ApiModels.OrdenesRequest;
import com.veris.verisimagenes.Service.ApiModels.OrdenesResponse;
import com.veris.verisimagenes.Service.Endpoints;
import com.veris.verisimagenes.Util.Loaders;
import com.veris.verisimagenes.Util.Mensaje;
import com.veris.verisimagenes.Util.MesUtil;
import com.veris.verisimagenes.Util.Routes;
import com.veris.verisimagenes.Util.Sesiones;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AgendaDelDiaActivity extends AppCompatActivity {

    private String fechaInicio;
    private String fechaFin;
    private ImageView refresca;
    private TextView textView2;
    private TextView textView;
    private Button btncerrarpdf;

    private PDFView pdfView;

    private Loaders loaders = new Loaders();
    private Endpoints service = ApiClient.getInstance();
    private Endpoints serviceNgrok = ApiClientNgrok.getInstance();

    private Login objLogin = new Login();
    private Sucursales objSucursales = new Sucursales();

    private SimpleDateFormat objSimpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
    private AgendaDelDiaAdapter agendaDelDiaAdapter;
    private DetalleOrdenAdapter detalleOrdenAdapter;

    private GridView lista_paciente_pendientes;
    private List<Ordenes> lsOrdenes = new ArrayList<>();
    private List<Ordenes> lsAuxiliar = new ArrayList<>();
    private List<DetalleOrden> lsDetalleOrden = new ArrayList<>();

    private List<DetalleOrden> lsDetalleOrdenAgregadas = new ArrayList<>();

    private final okhttp3.OkHttpClient client = new okhttp3.OkHttpClient();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.agenda_del_dia_activity);
        init();
    }

    public void init(){
        getSupportActionBar().setTitle("Atrás");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        objLogin = Sesiones.obtieneDatosLogin(AgendaDelDiaActivity.this);
        objSucursales = Sesiones.obtieneDatosSucursal(AgendaDelDiaActivity.this);
        loaders.inicializaProgress(AgendaDelDiaActivity.this);

        pdfView = findViewById(R.id.pdfView);
        textView2 = findViewById(R.id.textView2);
        textView = findViewById(R.id.textView);
        lista_paciente_pendientes = findViewById(R.id.lista_paciente_pendientes);
        refresca = findViewById(R.id.refresca);

        btncerrarpdf = findViewById(R.id.btncerrarpdf);
        btncerrarpdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                textView.setVisibility(View.VISIBLE);
                textView2.setVisibility(View.VISIBLE);
                lista_paciente_pendientes.setVisibility(View.VISIBLE);
                refresca.setVisibility(View.VISIBLE);
                pdfView.setVisibility(View.GONE);
                btncerrarpdf.setVisibility(View.GONE);



            }
        });



        SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
        SimpleDateFormat dia = new SimpleDateFormat("dd");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");

        Date d = new Date();

        String dayOfTheWeek = sdf.format(d);
        String diaActual = dia.format(d);
        String anioActual = year.format(d);

        Calendar fecha = Calendar.getInstance();
        int mes = fecha.get(Calendar.MONTH) + 1;

        String month = MesUtil.obtenerMes(mes);





        textView2.setText(dayOfTheWeek.toUpperCase()+" "+diaActual+" DE "+month.toUpperCase()+" DEL "+anioActual);



        refresca.setOnClickListener(view -> {

            lsOrdenes.clear();
            lsOrdenes = null;
            lsOrdenes = new ArrayList<>();

            lsDetalleOrden.clear();
            lsDetalleOrden = null;
            lsDetalleOrden = new ArrayList<>();

            obtenerOrdenes();

        });

        fechaInicio = objSimpleDateFormat.format(new Date());
        fechaFin = objSimpleDateFormat.format(new Date());

        agendaDelDiaAdapter = new AgendaDelDiaAdapter(lsOrdenes,lsDetalleOrden, AgendaDelDiaActivity.this);
        detalleOrdenAdapter = new DetalleOrdenAdapter(lsDetalleOrden,AgendaDelDiaActivity.this);


        obtenerOrdenes();

    }

    @Override
    public boolean onCreateOptionsMenu(android.view.Menu menu) {
        getMenuInflater().inflate(R.menu.menu_gen,menu);
        MenuItem cerrarSesion = menu.findItem(R.id.cerrarSesion);
        MenuItem buscar = menu.findItem(R.id.buscar);

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(buscar);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscador(newText);
                return false;
            }
        });



        cerrarSesion.setOnMenuItemClickListener(item -> {
            Sesiones.borrarLogin(AgendaDelDiaActivity.this);
            Routes.goToLogin(AgendaDelDiaActivity.this);
            return false;
        });

        return true;

    }

    public void buscador(String texto){

        if(texto.length() == 0){
            lsOrdenes.clear();
            lsOrdenes.addAll(lsAuxiliar);
        }else{

            lsOrdenes.clear();
            for(Ordenes i : lsAuxiliar){
                if(i.numeroOrden.toString().equals(texto) || i.nombrePaciente.toLowerCase().contains(texto)){
                    lsOrdenes.add(i);

                }
            }

        }

        agendaDelDiaAdapter = new AgendaDelDiaAdapter(lsOrdenes,lsDetalleOrden, AgendaDelDiaActivity.this);
        lista_paciente_pendientes.setAdapter(agendaDelDiaAdapter);
    }


    public void obtenerOrdenes(){

        loaders.mensaje("Cargando...");
        loaders.muestraProgress();

        Call<OrdenesResponse> call = service.obtenerOrdenes(objLogin.accessToken,
                                                                objLogin.Application,
                                                                objLogin.IdOrganization,

                                                      "es",
                                                  1,
                                                                objSucursales.codigoSucursal,
                                                                "07/12/2022",
                                                                "07/12/2022",
                                                                2028);

        call.enqueue(new Callback<OrdenesResponse>() {
            @Override
            public void onResponse(Call<OrdenesResponse> call, Response<OrdenesResponse> response) {

                loaders.cierraProgress();

                try{

                    if(response.code() == 200){
                        if(response.body() != null){

                            estructuraOrdenes(response.body().data);

                        }
                    }else if(response.code() == 401){

                        Toast.makeText(AgendaDelDiaActivity.this, "Sesión caducada, por favor vuelva a ingresar", Toast.LENGTH_SHORT).show();
                        Sesiones.borrarLogin(AgendaDelDiaActivity.this);
                        Routes.goToLogin(AgendaDelDiaActivity.this);

                    }else{
                        Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error :"+response.errorBody().string());
                    }


                }catch (Exception e){
                    loaders.cierraProgress();
                    e.printStackTrace();
                    Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error en el aplicativo :"+e.getMessage());

                }

            }

            @Override
            public void onFailure(Call<OrdenesResponse> call, Throwable t) {
                loaders.cierraProgress();
                t.printStackTrace();
                Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error en el aplicativo :"+t.getMessage());
            }
        });

    }


    public void estructuraOrdenes(List<Ordenes> lsOrdenes){

        List<Ordenes> lsOrdenesFiltrada = new ArrayList<>();
        List<DetalleOrden> lsDetalleOrden = new ArrayList<>();

        for(Ordenes ord : lsOrdenes){

            if (lsOrdenesFiltrada != null) {

                if(lsOrdenesFiltrada.size() > 0){

                    boolean esta = false;

                    for(Ordenes ordenesFiltadas : lsOrdenesFiltrada){


                        if (String.valueOf(ord.numeroOrden).equalsIgnoreCase(String.valueOf(ordenesFiltadas.numeroOrden))) {
                            esta = true;
                            break;
                        }

                    }

                    if(!esta){
                        lsOrdenesFiltrada.add(ord);
                    }


                }else{

                    lsOrdenesFiltrada.add(ord);

                }

            }else{
                lsOrdenesFiltrada.add(ord);
            }


        }

        for(Ordenes detOrden : lsOrdenes){
            DetalleOrden detalleOrden = new DetalleOrden();
            detalleOrden.codigo_empresa = detOrden.codigoEmpresaIntervalo;
            detalleOrden.numero_orden = detOrden.numeroOrden;
            detalleOrden.nombre_prestacion = detOrden.nombrePrestacion;
            detalleOrden.nombre_paciente = detOrden.nombrePaciente;
            detalleOrden.numeroTransaccion = detOrden.numeroTransaccion;
            lsDetalleOrden.add(detalleOrden);
        }





        this.lsOrdenes.addAll(lsOrdenesFiltrada);
        this.lsAuxiliar.addAll(lsOrdenesFiltrada);
        this.lsDetalleOrden.addAll(lsDetalleOrden);


        armaVista();



    }


    public void armaVista(){

        loaders.cierraProgress();

        agendaDelDiaAdapter = new AgendaDelDiaAdapter(lsOrdenes,lsDetalleOrden, AgendaDelDiaActivity.this);
        lista_paciente_pendientes.setAdapter(agendaDelDiaAdapter);

    }


    public void levantarModalDetalle(DetalleOrden detalleOrden){

        final AlertDialog.Builder builder = new AlertDialog.Builder(AgendaDelDiaActivity.this);
        LayoutInflater li = (LayoutInflater) AgendaDelDiaActivity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View vieW = li.inflate(R.layout.ordenes_item, null,false);
        final AlertDialog dialog = builder.create();
        dialog.setView(vieW);
        dialog.setCancelable(true);

        GridView ordenes_existentes = vieW.findViewById(R.id.ordenes_existentes);

        TextView nombrePaciente = vieW.findViewById(R.id.nombre_paciente);
        nombrePaciente.setText(detalleOrden.nombre_paciente);



        List<DetalleOrden> ls = new ArrayList<>();
        for(DetalleOrden detalle : lsDetalleOrden){
            if(detalle.numero_orden == detalleOrden.numero_orden){
                ls.add(detalle);
            }
        }

        detalleOrdenAdapter = new DetalleOrdenAdapter(ls,AgendaDelDiaActivity.this);
        ordenes_existentes.setAdapter(detalleOrdenAdapter);

        Button btn_vizualizar = vieW.findViewById(R.id.btn_vizualizar);
        btn_vizualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(lsDetalleOrdenAgregadas != null){
                    if(lsDetalleOrdenAgregadas.size() > 0){
                        vizualizarPDF();
                        lsDetalleOrdenAgregadas = null;
                        lsDetalleOrdenAgregadas = new ArrayList<>();
                        dialog.dismiss();
                    }else{
                        Toast.makeText(AgendaDelDiaActivity.this, "Seleccióne alguna orden", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(AgendaDelDiaActivity.this, "Seleccióne alguna orden", Toast.LENGTH_SHORT).show();
                }


            }
        });

        Button btn_cerrar = vieW.findViewById(R.id.btn_cerrar);
        btn_cerrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lsDetalleOrdenAgregadas = null;
                lsDetalleOrdenAgregadas = new ArrayList<>();
                dialog.dismiss();
            }
        });


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            dialog.create();
        }
        dialog.show();
    }


    @Nullable
    @Override
    public Intent getParentActivityIntent() {
        Routes.goToSucursales(AgendaDelDiaActivity.this,objSucursales.codigoTipoSucursal);
        return super.getParentActivityIntent();
    }

    @Override
    public void onBackPressed() {
        Routes.goToSucursales(AgendaDelDiaActivity.this,objSucursales.codigoTipoSucursal);
        super.onBackPressed();
    }

    public void vizualizarPDF(){
        loaders.mensaje("Descargando PDF ...");
        loaders.muestraProgress();


        int base64 = 0;

        List<OrdenesRequest> ordenesRequests = new ArrayList<>();

        for(DetalleOrden det : lsDetalleOrdenAgregadas){
            OrdenesRequest ordenesRequest = new OrdenesRequest();
            ordenesRequest.codigoEmpresa = 1;
            ordenesRequest.numeroOrden = det.numero_orden;
            base64 = ordenesRequest.numeroOrden;
            ordenesRequests.add(ordenesRequest);
        }


        OkHttpClient cliente = client.newBuilder()

                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)

                .build();

        Gson gson = new Gson();
        String ordenes = gson.toJson(ordenesRequests);

        String ver = String.valueOf(base64);

        String ord = Base64.encodeToString(ver.getBytes(), Base64.URL_SAFE| Base64.NO_WRAP);

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8");
        RequestBody postBody =RequestBody.create(mediaType, ord.toString());

        Request request = new Request.Builder()
                .url("https://api-phantomx.veris.com.ec/reportes/v1/gestion_medica/orden_medica?codigoEmpresa=1")
                .method("POST", postBody)
                .addHeader("Application", objLogin.Application)
                .addHeader("IdOrganizacion", objLogin.IdOrganization)
                .addHeader("Authorization", objLogin.accessToken)
                .addHeader("Accept-Language","es")
                .addHeader("Accept","application/pdf")
                .build();


        int finalBase6 = base64;

        cliente.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NotNull okhttp3.Call call, @NotNull IOException e) {
                loaders.cierraProgress();
                e.printStackTrace();
                Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error en el aplicativo :"+e.getMessage());
            }

            @Override
            public void onResponse(@NotNull okhttp3.Call call, @NotNull okhttp3.Response response) throws IOException {
                try{

                    loaders.cierraProgress();

                    if(response.code() == 200){


                        runOnUiThread(() -> {
                            refresca.setVisibility(View.GONE);
                            textView.setVisibility(View.GONE);
                            textView2.setVisibility(View.GONE);
                            lista_paciente_pendientes.setVisibility(View.GONE);
                            btncerrarpdf.setVisibility(View.VISIBLE);

                            pdfView.setVisibility(View.VISIBLE);
                        });


                        pdfView.fromStream(response.body().byteStream()).load();

                    }else if(response.code() == 401){
                        Toast.makeText(AgendaDelDiaActivity.this, "Sesión caducada, por favor vuelva a ingresar", Toast.LENGTH_SHORT).show();
                        Sesiones.borrarLogin(AgendaDelDiaActivity.this);
                        Routes.goToLogin(AgendaDelDiaActivity.this);
                    }else{
                        loaders.cierraProgress();
                        Looper.prepare();
                        Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error al obtener PDF, Contacte a Soporte ");
                        Looper.loop();

                    }



                }catch (Exception e){
                    loaders.cierraProgress();
                    e.printStackTrace();
                    Looper.prepare();
                    Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error en el aplicativo :"+e.getMessage());
                    Looper.loop();
                }
            }

        });

    }



    public void descargaPDFactura(int numeroTransaccion) {

        loaders.mensaje("Descargando Factura ...");
        loaders.muestraProgress();



        OkHttpClient cliente = client.newBuilder()

                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
                .callTimeout(30, TimeUnit.SECONDS)

                .build();



        Request request = new Request.Builder()
                .url("https://api-phantomx.veris.com.ec/reportes/v1/facturacion/comprobante_paciente?format=pdf&codigoEmpresa="+objSucursales.codigoEmpresa+"&numeroTransaccion="+numeroTransaccion)
                .method("GET", null)
                .addHeader("Application", objLogin.Application)
                .addHeader("IdOrganizacion", objLogin.IdOrganization)
                .addHeader("Authorization", objLogin.accessToken)
                .addHeader("Accept-Language","es")
                .build();


        cliente.newCall(request).enqueue(new okhttp3.Callback() {
            @Override
            public void onFailure(@NonNull okhttp3.Call call, @NonNull IOException e) {
                loaders.cierraProgress();
                e.printStackTrace();
                Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error en el aplicativo :"+e.getMessage());
            }

            @Override
            public void onResponse(@NonNull okhttp3.Call call, @NonNull okhttp3.Response response) throws IOException {

                runOnUiThread(() -> {
                    try{

                        if(response.code() == 200){

                            loaders.cierraProgress();


                            runOnUiThread(() -> {
                                refresca.setVisibility(View.GONE);
                                textView.setVisibility(View.GONE);
                                textView2.setVisibility(View.GONE);
                                lista_paciente_pendientes.setVisibility(View.GONE);
                                btncerrarpdf.setVisibility(View.VISIBLE);

                                pdfView.setVisibility(View.VISIBLE);
                            });


                            pdfView.fromStream(response.body().byteStream()).load();



                        }else if(response.code() == 401){

                            Toast.makeText(AgendaDelDiaActivity.this, "Sesión caducada, por favor vuelva a ingresar", Toast.LENGTH_SHORT).show();
                            Sesiones.borrarLogin(AgendaDelDiaActivity.this);
                            Routes.goToLogin(AgendaDelDiaActivity.this);

                        }else{
                            loaders.cierraProgress();
                            Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error al obtener PDF, Contacte a Soporte ");
                        }

                    }catch (Exception e){
                        loaders.cierraProgress();
                        e.printStackTrace();
                        Mensaje.mensaje(AgendaDelDiaActivity.this,"Ocurrio un error en el aplicativo :"+e.getMessage());
                    }
                });


            }
        });



    }



    public void agregarDetalleOrden(DetalleOrden detalleOrden) {

        boolean elimina = false;

        if(lsDetalleOrdenAgregadas != null){
            if(lsDetalleOrdenAgregadas.size() > 0){

                for(DetalleOrden det : lsDetalleOrdenAgregadas){
                    if(det.linea_detalle == detalleOrden.linea_detalle){
                        lsDetalleOrdenAgregadas.remove(detalleOrden);
                        elimina = true;
                        break;
                    }
                }

                if(!elimina){
                    lsDetalleOrdenAgregadas.add(detalleOrden);
                }

            }else{
                lsDetalleOrdenAgregadas.add(detalleOrden);
            }
        }else{
            lsDetalleOrdenAgregadas.add(detalleOrden);
        }

        Log.e("TAMANIO","TAMANIO DE LISTA "+lsDetalleOrdenAgregadas.size());

    }


}