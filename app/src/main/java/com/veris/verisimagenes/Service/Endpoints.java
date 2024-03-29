package com.veris.verisimagenes.Service;

import com.veris.verisimagenes.Service.ApiModels.LoginResponse;
import com.veris.verisimagenes.Service.ApiModels.OrdenesRequest;
import com.veris.verisimagenes.Service.ApiModels.OrdenesResponse;
import com.veris.verisimagenes.Service.ApiModels.SucursalesResponse;
import com.veris.verisimagenes.Service.ApiModels.TiposSucursalResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Endpoints {

    //DESARROLLO
    String strGeneral = "general";
    String strSeguridad = "seguridad";
    String strAgendamiento = "agendamiento";


    //TEST
    /*String strGeneral = "generaltest";
    String strSeguridad = "seguridadtest";
    String strAgendamiento = "agendamientotest";*/


    //PRODUCCION
    /*String strGeneral = "general";
    String strSeguridad = "seguridad";
    String strAgendamiento = "agendamiento";*/

    @POST(strSeguridad+"/v1/autenticacion/login")
    Call<LoginResponse> login(@Header("Application") String Application,
                              @Header("Authorization") String Authorization);

    @GET(strGeneral+"/v1/tipos_sucursal")
    Call<TiposSucursalResponse> obtenerTipoSucursales(@Header("Application") String Application,
                                                      @Header("IdOrganizacion") String IdOrganizacion,
                                                      @Header("Authorization") String Authorization);

    @GET(strSeguridad+"/v1/usuarios/{secuenciaUsuario}/sucursales")
    Call<SucursalesResponse> obtenerSucursales(@Header("Authorization") String Authorization,
                                               @Header("Application") String Application,
                                               @Path("secuenciaUsuario") int secuenciaUsuario,
                                               @Query("tipoSucursal") String tipoSucursal);


    @GET("apoyosdx/v1/imagenes/atender_pacientes/listado_pacientes_x_atender")
    Call<OrdenesResponse> obtenerOrdenes(@Header("Authorization") String Authorization,
                                         @Header("Application") String Application,
                                         @Header("IdOrganizacion") String IdOrganizacion,
                                         @Header("Accept-Language") String Language,
                                         @Query("codigoEmpresa")Integer codigoEmpresa,
                                         @Query("codigoSucursal")Integer codigoSucursal,
                                         @Query("fechaInicio")String fechaInicio,
                                         @Query("fechaFin")String fechaFin,
                                         @Query("codigoProfesional")Integer codigoProfesional);


    @POST("reporteshistoriaclinica/v1/"+strGeneral+"/orden_servicio")
    Call<String> obtenerPDF(@Header("Authorization") String Authorization,
                          @Header("Application") String Application,
                          @Header("IdOrganizacion") String IdOrganizacion,
                          @Header("Accept-Language") String Language,
                          @Body List<OrdenesRequest> ordenesRequests);

}
