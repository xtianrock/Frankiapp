package com.appcloud.frankiapp.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Configuration;

/**
 * Created by cristian on 09/05/2016.
 */
public class SpinnerConvergenciaAdapter extends ArrayAdapter<String> {

    Context context;
    Tarifa tarifa;
    String[] tiposConvergencia;
    float precioConv[];


    public SpinnerConvergenciaAdapter(Context context, int resource, String[] tiposCovergencia, Tarifa tarifa) {
        super(context, resource);
        this.context = context;
        this.tiposConvergencia = tiposCovergencia;
        this.tarifa = tarifa;
        precioConv = new float[tiposCovergencia.length];
    }

    public int getCount(){
        return tiposConvergencia.length;
    }

    public String getItem(int position){
        return tiposConvergencia[position];
    }

    public long getItemId(int position){
        return position;
    }

    public float getPrecio(int position)
    {
        return precioConv[position];
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.linea_spinner_convergencia, parent, false);
        TextView nombre = (TextView)view.findViewById(R.id.tv_spinner_conv_nombre);
        TextView precio = (TextView)view.findViewById(R.id.tv_spinner_conv_precio);

        nombre.setText(tiposConvergencia[position]);

        switch (tiposConvergencia[position])
        {
            case Configuration.MINI: precioConv[position] = tarifa.getPrecioConvMINIS();
                break;
            case Configuration.S: precioConv[position] = tarifa.getPrecioConvS();
                break;
            case Configuration.M: precioConv[position] = tarifa.getPrecioConvM();
                break;
            case Configuration.L: precioConv[position] = tarifa.getPrecioConvL();
                break;
            case Configuration.XL: precioConv[position] = tarifa.getPrecioConvXL();
                break;
            case Configuration.XXL: precioConv[position] = tarifa.getPrecioConvXXL();
                break;
        }
        precio.setText(String.valueOf(precioConv[position]));

        return view;
    }

    @Override
    public View getDropDownView(int position, View convertView,
                                ViewGroup parent) {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.linea_spinner_convergencia, parent, false);
        final TextView nombre = (TextView)view.findViewById(R.id.tv_spinner_conv_nombre);
        final TextView precio = (TextView)view.findViewById(R.id.tv_spinner_conv_precio);

        nombre.setText(tiposConvergencia[position]);

        switch (tiposConvergencia[position])
        {
            case Configuration.MINI: precio.setText(String.valueOf(tarifa.getPrecioConvMINIS()));
                break;
            case Configuration.S: precio.setText(String.valueOf(tarifa.getPrecioConvS()));
                break;
            case Configuration.M: precio.setText(String.valueOf(tarifa.getPrecioConvM()));
                break;
            case Configuration.L: precio.setText(String.valueOf(tarifa.getPrecioConvL()));
                break;
            case Configuration.XL: precio.setText(String.valueOf(tarifa.getPrecioConvXL()));
                break;
            case Configuration.XXL: precio.setText(String.valueOf(tarifa.getPrecioConvXXL()));
                break;
        }


        return view;
    }

}
