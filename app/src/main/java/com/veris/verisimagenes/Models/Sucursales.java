package com.veris.verisimagenes.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class Sucursales {

    public int codigoEmpresa;
    public int codigoSucursal;
    public String nombreSucursal;
    public String codigoTipoSucursal;
    public String nombreTipoSucursal;
    public String nombrePublico;
    public String direccion;
    public String referenciasCercanas;
    public String descripcionCiudad;
    public boolean centroMedico;
    public boolean habilitadoCanalesVirtuales;
    public boolean poseeFarmacia;
    public String urlImagen;
    public String telefono1;
    public String latitud;
    public String longitud;
    @JsonIgnore
    public List<HorariosAtencion> horariosAtencion;
    @JsonIgnore
    public List<DiasLaborables> diasLaborables;
    public String codTel1Pais;
    public String direccionCercanaGmaps;
    public boolean aplicaServicioMotorizado;
    public String correoElectronicoContacto;
    public Integer codigoPais;
    public String nombrePais;
    public Integer codigoProvincia;
    public String nombreProvincia;
    public Integer codigoCiudad;
    public String nombreCiudad;
    public Integer codigoSector;
    public String nombreSector;
    public Integer numeroLocalidad;
    public String codigoPaisHomologa;
    public String codigoProvinciaHomologa;
    public String codigoCiudadHomologa;
    public String fechaApertura;
    public String fechaCierre;
    public String fechaReinicio;
    public boolean esMatriz;
    public boolean esCentroMedico;
    public boolean esContactCenter;
    public boolean esOfertaVirtual;
    public boolean mostrarDatosApp;
    public boolean mostrarAppLab;
    public String codigoTipoCuenta;
    public String numeroCuenta;
    public Integer codigoSucursalIESS;
    public String tipoClasificacionFinanciera;
    public String tituloReporte;
    public String tituloFactura;
    public boolean imprimeEtiquetasCaja;
    public boolean aplicaDiaSalud;
    public boolean aplicaDescanso;
    public boolean aplicaLibre;
    public boolean aplicaPacienteNuevos;
    public boolean aplicaProfesionalesNuevos;
    public boolean aplicaRotacion;
    public boolean aplicaVerisExpress;
    public Integer codigoPaisConvencional;

}
