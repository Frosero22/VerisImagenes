package com.veris.verisimagenes.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

public class Ordenes {


    public String fechaPlanificada;
    public String horaInicioReserva;
    public String horaFinReserva;
    public Integer codigoSitio;
    public String nombreSitio;
    public Integer idPaciente;
    public String numeroIdentificacion;
    public Integer codigoTipoIdentificacion;
    public String nombrePaciente;
    public String fechaNacimiento;
    public String generoPaciente;
    public String telefonoFijo;
    public String telefonoMovil;
    public Integer codigoReserva;
    public String estadoReserva;
    public Integer codigoTipoReserva;
    public Integer codigoEspecialidad;
    public String nombreEspecialidad;
    public String tipoAgenda;
    public Integer codigoSucursal;
    public String nombreSucursal;
    public String nombrePrestacion;
    public Integer numeroOrden;
    public Integer codigoEmpresaIntervalo;
    public Integer codigoProfesional;
    public String nombreConvenio;
    public Integer codigoOrdApoyo;
    public String nombreTipoReserva;
    public String esBloqueo;
    public String estaPagado;
    public Integer codigoPrestacion;
    public String esPrimeraVez;
    public Integer lineaDetalle;
    public String fechaRecepcion;
    public String nombreMedico;
    public Integer codigoDetalleOrden;
    public String numeroFactura;
    public String fechaEmisionFact;
    public Integer codigoMedicoRemite;
    public Integer numeroTransaccion;
    public String esOrdenMedica;


    @JsonIgnore
    public List<DetalleOrden> lsDetalle;

}
