package com.veris.verisimagenes.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.veris.verisimagenes.Activitys.AgendaDelDiaActivity;
import com.veris.verisimagenes.Models.DetalleOrden;
import com.veris.verisimagenes.Models.Ordenes;
import com.veris.verisimagenes.R;

import java.util.ArrayList;
import java.util.List;

public class AgendaDelDiaAdapter implements ListAdapter {

    public static List<Ordenes> listaOrdenes = new ArrayList();
    public static List<DetalleOrden> listaDetalleOrden = new ArrayList();
    public static Context context;
    private LayoutInflater mInflater;
    public AgendaDelDiaActivity agendaDelDiaActivity;

    public AgendaDelDiaAdapter(List<Ordenes> listado,List<DetalleOrden> listaDetalleOrden, Context context) {
        this.context = context;
        this.listaOrdenes = listado;
        this.listaDetalleOrden = listaDetalleOrden;
        this.mInflater = LayoutInflater.from(context);
        this.agendaDelDiaActivity = (AgendaDelDiaActivity)context;
    }


    @Override
    public boolean areAllItemsEnabled() {
        return false;
    }

    @Override
    public boolean isEnabled(int i) {
        return false;
    }

    @Override
    public void registerDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public void unregisterDataSetObserver(DataSetObserver dataSetObserver) {

    }

    @Override
    public int getCount() {
        return listaOrdenes.size();
    }

    @Override
    public Object getItem(int i) {
        return listaOrdenes.get(i);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public View getView(int i, View vview, ViewGroup viewGroup) {

        vview = mInflater.inflate(R.layout.agenda_item, null);

        LinearLayout lnNombrePrestacion = vview.findViewById(R.id.lnNombrePrestacion);

        CardView card_view = vview.findViewById(R.id.card_view);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("ORDENES","ORDENES ESCOGIDO "+listaOrdenes.get(i).numero_orden);
                //Routes.goToSucursales(context,listaTiposSucursal.get(i).codigoTipoSucursal);
            }
        });

        TextView numero_orden = vview.findViewById(R.id.numero_orden);
        numero_orden.setText(String.valueOf(listaOrdenes.get(i).numero_orden));

        TextView hora_inicio = vview.findViewById(R.id.hora_inicio);
        hora_inicio.setText(listaOrdenes.get(i).hora_inicio);

        String prestaciones = null;

        for(DetalleOrden detalleOrden : listaDetalleOrden){
            if(detalleOrden.numero_orden == listaOrdenes.get(i).numero_orden){
                if(prestaciones != null){
                    if(prestaciones.length() > 0){
                        prestaciones = prestaciones + detalleOrden.nombre_prestacion;
                    }else{
                        ViewGroup.LayoutParams layoutParams = lnNombrePrestacion.getLayoutParams();
                        layoutParams.height = 46;
                        lnNombrePrestacion.setLayoutParams(layoutParams);
                        prestaciones = prestaciones+","+detalleOrden.nombre_prestacion;
                    }
                }else{
                    prestaciones = "";
                    prestaciones = prestaciones + detalleOrden.nombre_prestacion;
                }

            }
        }


        TextView nombre_prestaciones = vview.findViewById(R.id.nombre_prestaciones);
        nombre_prestaciones.setText(prestaciones);

        TextView nombre_doctor = vview.findViewById(R.id.nombre_doctor);
        nombre_doctor.setText(listaOrdenes.get(i).nombre_medico);

        TextView nombre_paciente = vview.findViewById(R.id.nombre_paciente);
        nombre_paciente.setText(listaOrdenes.get(i).nombre_paciente);

        Button btn_ver_detalle = vview.findViewById(R.id.btn_ver_detalle);
        btn_ver_detalle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                agendaDelDiaActivity.levantarModalDetalle(listaDetalleOrden.get(i));


            }
        });





        return vview;
    }

    @Override
    public int getItemViewType(int i) {
        return 0;
    }

    @Override
    public int getViewTypeCount() {
        if(getCount()<1) return 1;
        return getCount();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }
}