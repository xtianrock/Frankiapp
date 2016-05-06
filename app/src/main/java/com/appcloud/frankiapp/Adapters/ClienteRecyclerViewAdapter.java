package com.appcloud.frankiapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appcloud.frankiapp.Interfaces.OnClienteInteractionListener;
import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.R;

import java.util.List;


public class ClienteRecyclerViewAdapter extends RecyclerView.Adapter<ClienteRecyclerViewAdapter.ViewHolder> {

    private final List<Cliente> mValues;
    private final OnClienteInteractionListener mListener;

    public ClienteRecyclerViewAdapter(List<Cliente> items, OnClienteInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carview_clientes, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvNombre.setText(mValues.get(position).getNombre());
        holder.tvApellidos.setText(mValues.get(position).getApellidos());
        holder.tvPoblacion.setText(mValues.get(position).getPoblacion());
        holder.tvTelefono.setText(mValues.get(position).getTelefono());


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onListFragmentInteraction(holder.mItem);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNombre;
        public final TextView tvApellidos;
        public final TextView tvPoblacion;
        public final TextView tvTelefono;
        public Cliente mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = (TextView) view.findViewById(R.id.tv_cliente_nombre);
            tvApellidos = (TextView) view.findViewById(R.id.tv_cliente_apellidos);
            tvPoblacion = (TextView) view.findViewById(R.id.tv_cliente_poblacion);
            tvTelefono = (TextView) view.findViewById(R.id.tv_cliente_telefono);


        }

    }
}
