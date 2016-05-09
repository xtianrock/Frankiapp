package com.appcloud.frankiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cristian on 05/05/2016.
 */
public class TarifasAutocompletadoAdapter extends ArrayAdapter<Tarifa> {
    private LayoutInflater layoutInflater;
    List<Tarifa> tarifas;




    public TarifasAutocompletadoAdapter(Context context, int textViewResourceId, List<Tarifa> articulos) {
        super(context, textViewResourceId, articulos);
        // copy all the customers into a master list
        tarifas = new ArrayList<>(articulos.size());
        tarifas.addAll(articulos);
        layoutInflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;

        if (view == null) {
            view = layoutInflater.inflate(R.layout.linea_spinner_tarifa, null);
        }

        Tarifa tarifa = getItem(position);

        TextView descripcion = (TextView) view.findViewById(R.id.tv_spinner_tarifa);
        descripcion.setText(tarifa.getPlanPrecios());


        return view;
    }


}