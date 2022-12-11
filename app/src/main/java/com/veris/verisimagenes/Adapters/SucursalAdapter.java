package com.veris.verisimagenes.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.veris.verisimagenes.Models.Sucursales;
import com.veris.verisimagenes.R;
import com.veris.verisimagenes.Util.Routes;
import com.veris.verisimagenes.Util.Sesiones;

import java.util.ArrayList;
import java.util.List;

public class SucursalAdapter implements ListAdapter {

    public static List<Sucursales> listaSucursal = new ArrayList();
    public static Context context;
    private LayoutInflater mInflater;

    public SucursalAdapter(List<Sucursales> listado, Context context) {
        this.context = context;
        this.listaSucursal = listado;
        this.mInflater = LayoutInflater.from(context);
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
        return listaSucursal.size();
    }

    @Override
    public Object getItem(int i) {
        return listaSucursal.get(i);
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

        vview = mInflater.inflate(R.layout.sucursal_item, null);

        CardView card_view = vview.findViewById(R.id.card_view);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("SUCURSAL","SUCURSAL ESCOGIDO "+listaSucursal.get(i).codigoTipoSucursal);
                Sesiones.guardaSucursal(context,listaSucursal.get(i));
                Routes.goToAgenda(context);
            }
        });

        TextView textView = vview.findViewById(R.id.nombreSucursal);

        textView.setText(listaSucursal.get(i).nombreSucursal);



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
