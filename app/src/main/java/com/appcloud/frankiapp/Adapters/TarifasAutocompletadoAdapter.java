package com.appcloud.frankiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
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


    private Filter mFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            return String.valueOf(((Tarifa) resultValue).getCodTarifa());
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            FilterResults results = new FilterResults();

            if (constraint != null) {
                ArrayList<Tarifa> suggestions = new ArrayList<>();
                for (Tarifa customer : tarifas) {
                    // Note: change the "contains" to "startsWith" if you only want starting matches
                    if (customer.getPlanPrecios().toLowerCase().contains(constraint.toString().toLowerCase()))
                        suggestions.add(customer);

                    else if(String.valueOf(customer.getCodTarifa()).contains(constraint.toString().toLowerCase()))
                        suggestions.add(customer);
                }

                results.values = suggestions;
                results.count = suggestions.size();
            }

            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            clear();
            if (results != null && results.count > 0) {
                // we have filtered results
                addAll((ArrayList<Tarifa>) results.values);
            } else {
                // no filter, do something
            }
            notifyDataSetChanged();
        }
    };

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
            view = layoutInflater.inflate(R.layout.linea_autocompletado_tarifa, null);
        }

        Tarifa tarifa = getItem(position);

        TextView descripcion = (TextView) view.findViewById(R.id.tv_descrip);
        descripcion.setText(tarifa.getPlanPrecios());
        TextView id=(TextView)view.findViewById(R.id.tv_id);
        id.setText("ref: "+tarifa.getCodTarifa());

        return view;
    }

    @Override
    public Filter getFilter() {
        return mFilter;
    }
}