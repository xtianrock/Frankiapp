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

/**
 * Created by cristian on 09/05/2016.
 */
public class SpinnerConvergenciaAdapter extends ArrayAdapter<String> {

    Context context;
    Tarifa tarifa;
    String[] tiposConvergencia;
    ConvergenciaClickListener convergenciaClickListener;

    public SpinnerConvergenciaAdapter(Context context, int resource, String[] tiposCovergencia, Tarifa tarifa, ConvergenciaClickListener listener) {
        super(context, resource);
        this.context = context;
        this.tiposConvergencia = tiposCovergencia;
        this.tarifa = tarifa;
        this.convergenciaClickListener = listener;
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


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = LayoutInflater.from(getContext()).inflate(R.layout.linea_spinner_convergencia, parent, false);
        TextView nombre = (TextView)view.findViewById(R.id.tv_spinner_conv_nombre);
        TextView precio = (TextView)view.findViewById(R.id.tv_spinner_conv_precio);

        nombre.setText(tiposConvergencia[position]);

        switch (tiposConvergencia[position])
        {
            case Terminal.MINI: precio.setText(String.valueOf(tarifa.getPrecioConvMINIS()));
                break;
            case Terminal.S: precio.setText(String.valueOf(tarifa.getPrecioConvS()));
                break;
            case Terminal.M: precio.setText(String.valueOf(tarifa.getPrecioConvM()));
                break;
            case Terminal.L: precio.setText(String.valueOf(tarifa.getPrecioConvL()));
                break;
            case Terminal.XL: precio.setText(String.valueOf(tarifa.getPrecioConvXL()));
                break;
            case Terminal.XXL: precio.setText(String.valueOf(tarifa.getPrecioConvXXL()));
                break;
        }


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
            case Terminal.MINI: precio.setText(String.valueOf(tarifa.getPrecioConvMINIS()));
                break;
            case Terminal.S: precio.setText(String.valueOf(tarifa.getPrecioConvS()));
                break;
            case Terminal.M: precio.setText(String.valueOf(tarifa.getPrecioConvM()));
                break;
            case Terminal.L: precio.setText(String.valueOf(tarifa.getPrecioConvL()));
                break;
            case Terminal.XL: precio.setText(String.valueOf(tarifa.getPrecioConvXL()));
                break;
            case Terminal.XXL: precio.setText(String.valueOf(tarifa.getPrecioConvXXL()));
                break;
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convergenciaClickListener.onItemClick(precio.getText().toString());
            }
        });
        return view;
    }

    public interface ConvergenciaClickListener {
        void onItemClick(String convergencia);
    }
}
