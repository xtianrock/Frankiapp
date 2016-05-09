package com.appcloud.frankiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.R;

import java.util.List;

/**
 * Created by cristian on 09/05/2016.
 */
public class SpinnerTarifaAdapter extends ArrayAdapter<Tarifa> {

    // Your sent context
    private Context context;
    // Your custom values for the spinner (User)
    List<Tarifa> tarifas;

    public SpinnerTarifaAdapter(Context context, int textViewResourceId,
                       List<Tarifa> tarifas) {
        super(context, textViewResourceId, tarifas);
        this.context = context;
        this.tarifas = tarifas;
    }

    public int getCount(){
        return tarifas.size();
    }

    public Tarifa getItem(int position){
        return tarifas.get(position);
    }

    public long getItemId(int position){
        return position;
    }


    // And the "magic" goes here
    // This is for the "passive" state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.linea_spinner_tarifa, parent, false);
        TextView label = (TextView)view.findViewById(R.id.tv_spinner_tarifa);
        label.setText(tarifas.get(position).getPlanPrecios());
        label.setVisibility(View.VISIBLE);
        return view;
    }


    @Override
    public boolean isEnabled(int position) {
        if(position == 0)
        {
            // Disable the first item from Spinner
            // First item will be use for hint
            return false;
        }
        else
        {
            return true;
        }
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View dropDownView = LayoutInflater.from(getContext()).inflate(R.layout.linea_spinner_tarifa, parent, false);

        TextView label = (TextView)dropDownView.findViewById(R.id.tv_spinner_tarifa_dropdown);
        label.setText(tarifas.get(position).getPlanPrecios());
        label.setVisibility(View.VISIBLE);
        return dropDownView;
    }
}
