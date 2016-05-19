package com.appcloud.frankiapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Configuration;

import java.util.List;

/**
 * Created by cristian on 05/05/2016.
 */
public class ClientesBottomSheetAdapter extends RecyclerView.Adapter<ClientesBottomSheetAdapter.ViewHolder> {

    private final List<Cliente> clientes;
    ClienteBSheetClickListener clienteBsheetClickListener;


    public ClientesBottomSheetAdapter(List<Cliente> clientes, ClienteBSheetClickListener listener) {
        this.clientes = clientes;
        this.clienteBsheetClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_bottomsheet_cliente, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.cliente = clientes.get(position);

        holder.tvNombre.setText( holder.cliente.getNombre());
        holder.tvApellidos.setText(holder.cliente.getApellidos());
        holder.tvPoblacion.setText(holder.cliente.getPoblacion());
        holder.tvTlf.setText(holder.cliente.getTelefono());

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clienteBsheetClickListener.onItemClick(holder.cliente);
            }
        });
    }


    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNombre;
        public final TextView tvApellidos;
        public final TextView tvPoblacion;
        public final TextView tvTlf;
        public Cliente cliente;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = (TextView) view.findViewById(R.id.tv_cliente_nombre);
            tvApellidos = (TextView) view.findViewById(R.id.tv_cliente_apellidos);
            tvPoblacion = (TextView) view.findViewById(R.id.tv_cliente_poblacion);
            tvTlf = (TextView) view.findViewById(R.id.tv_cliente_telefono);

        }
    }

    public interface ClienteBSheetClickListener {
        void onItemClick(Cliente cliente);
    }
}