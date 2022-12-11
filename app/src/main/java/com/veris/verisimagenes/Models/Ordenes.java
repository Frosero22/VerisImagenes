package com.veris.verisimagenes.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.math.BigDecimal;
import java.util.List;

public class Ordenes {

    public String fecha_planificada;
    public String hora_inicio;
    public String hora_fin;
    public Integer codigo_sitio;
    public String nombre_sitio;
    public BigDecimal id_paciente;
    public String numero_identificacion;
    public Integer codigo_tipo_identificacion;
    public String nombre_paciente;
    public String fecha_nacimiento;
    public String genero_paciente;
    public String telefono_fijo;
    public String telefono_movil;
    public BigDecimal codigo_reserva;
    public String estado_reserva;
    public Integer codigo_tipo_reserva;
    public Integer codigo_especialidad;
    public String nombre_especialidad;
    public String tipo_agenda;
    public Integer codigo_sucursal;
    public String nombre_sucursal;
    public String nombre_prestacion;
    public Integer numero_orden;
    public Integer codigo_empresa_intervalo;
    public Integer codigo_profesional;
    public String nombre_empresa_chequeo;
    public Integer codigo_ord_apoyo;
    public String nombre_tipo_reserva;
    public String es_bloqueo;
    public String esta_pagado;
    public Integer codigo_prestacion;
    public String es_primera_vez;
    public Integer linea_detalle;
    public String fecha_recepcion;
    public String nombre_medico;
    public Integer codigo_detalle_orden;
    public String numero_factura;
    public String fecha_emision_fact;
    public Integer codigo_medico_remite;

    @JsonIgnore
    public List<DetalleOrden> lsDetalle;

}
