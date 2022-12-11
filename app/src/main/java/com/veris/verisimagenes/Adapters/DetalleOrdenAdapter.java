package com.veris.verisimagenes.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListAdapter;

import androidx.cardview.widget.CardView;

import com.veris.verisimagenes.Activitys.AgendaDelDiaActivity;
import com.veris.verisimagenes.Models.DetalleOrden;
import com.veris.verisimagenes.R;

import java.util.ArrayList;
import java.util.List;

public class DetalleOrdenAdapter implements ListAdapter {

    public static List<DetalleOrden> listadoDetalle = new ArrayList();
    public static Context context;
    private LayoutInflater mInflater;
    public AgendaDelDiaActivity agendaDelDiaActivity;

    public DetalleOrdenAdapter(List<DetalleOrden> listado, Context context) {
        this.context = context;
        this.listadoDetalle = listado;
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
        return listadoDetalle.size();
    }

    @Override
    public Object getItem(int i) {
        return listadoDetalle.get(i);
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

        vview = mInflater.inflate(R.layout.ordenes_items, null);

        CardView card_view = vview.findViewById(R.id.card_view);
        CheckBox checkDetalleOrden = vview.findViewById(R.id.checkDetalleOrden);
        checkDetalleOrden.setText(listadoDetalle.get(i).nombre_prestacion);
        checkDetalleOrden.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Log.e("TAG","DETALLE DE LA ORDEN "+listadoDetalle.get(i).nombre_prestacion);
                agendaDelDiaActivity.agregarDetalleOrden(listadoDetalle.get(i));

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
