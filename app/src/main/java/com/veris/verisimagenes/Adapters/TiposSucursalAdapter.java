package com.veris.verisimagenes.Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.bumptech.glide.Glide;
import com.veris.verisimagenes.Models.TipoSucursal;
import com.veris.verisimagenes.R;
import com.veris.verisimagenes.Util.Routes;
import com.veris.verisimagenes.Util.Sesiones;

import java.util.ArrayList;
import java.util.List;

public class TiposSucursalAdapter implements ListAdapter {

    public static List<TipoSucursal> listaTiposSucursal = new ArrayList();
    public static Context context;
    private LayoutInflater mInflater;

    public TiposSucursalAdapter(List<TipoSucursal> listado, Context context) {
        this.context = context;
        this.listaTiposSucursal = listado;
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
        return listaTiposSucursal.size();
    }

    @Override
    public Object getItem(int i) {
        return listaTiposSucursal.get(i);
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

        vview = mInflater.inflate(R.layout.tipo_sucursal_item, null);

        CardView card_view = vview.findViewById(R.id.card_view);
        card_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TIPO"," TIPO DE SUCURSAL ESCOGIDO "+listaTiposSucursal.get(i).codigoTipoSucursal);
                Sesiones.guardaTipoSucursal(context,listaTiposSucursal.get(i));
                Routes.goToSucursales(context,listaTiposSucursal.get(i).codigoTipoSucursal);
            }
        });

        ImageView imageview = vview.findViewById(R.id.img);
        TextView textView = vview.findViewById(R.id.txvTipoSucursal);

        textView.setText(listaTiposSucursal.get(i).nombreTipoSucursal);


        Glide.with(context).load(listaTiposSucursal.get(i).urlLogo)
                .error(R.drawable.logo)
                .into(imageview);

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
