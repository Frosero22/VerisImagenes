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

    public AgendaDelDiaAdapter(List<Ordenes> listado,Context context) {
        this.context = context;
        this.listaOrdenes = listado;
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
                Log.e("ORDENES","ORDENES ESCOGIDO "+listaOrdenes.get(i).numeroOrden);
                //Routes.goToSucursales(context,listaTiposSucursal.get(i).codigoTipoSucursal);
            }
        });

        TextView numero_orden = vview.findViewById(R.id.numero_orden);
        numero_orden.setText(String.valueOf(listaOrdenes.get(i).numeroOrden));

        TextView hora_inicio = vview.findViewById(R.id.hora_inicio);
        hora_inicio.setText(listaOrdenes.get(i).horaInicioReserva+" - "+listaOrdenes.get(i).horaFinReserva);


        TextView nombre_prestaciones = vview.findViewById(R.id.nombre_prestaciones);
        nombre_prestaciones.setText(listaOrdenes.get(i).nombrePrestacion);

        TextView nombre_doctor = vview.findViewById(R.id.nombre_doctor);
        nombre_doctor.setText(listaOrdenes.get(i).nombreMedico);

        TextView nombre_paciente = vview.findViewById(R.id.nombre_paciente);
        nombre_paciente.setText(listaOrdenes.get(i).nombrePaciente+" | CI: "+listaOrdenes.get(i).numeroIdentificacion);



        Button btn_ver_detalle = vview.findViewById(R.id.btn_ver_detalle);

        /*if(!listaOrdenes.get(i).esOrdenMedica.equalsIgnoreCase("S")){
            btn_ver_detalle.setEnabled(false);
        }*/

        btn_ver_detalle.setOnClickListener(view -> agendaDelDiaActivity.levantarModalDetalle(listaOrdenes.get(i).numeroOrden));

        Button btn_ver_factura = vview.findViewById(R.id.btn_ver_factura);
        btn_ver_factura.setOnClickListener(view -> agendaDelDiaActivity.descargaPDFactura(listaOrdenes.get(i).numeroTransaccion));





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