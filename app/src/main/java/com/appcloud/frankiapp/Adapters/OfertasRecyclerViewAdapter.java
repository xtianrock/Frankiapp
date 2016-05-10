package com.appcloud.frankiapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;

import java.util.List;


public class OfertasRecyclerViewAdapter extends RecyclerView.Adapter<OfertasRecyclerViewAdapter.ViewHolder> {

    private final List<Oferta> ofertas;
    private final OfertaClickListener mListener;

    public OfertasRecyclerViewAdapter(List<Oferta> ofertas, OfertaClickListener listener) {
        this.ofertas = ofertas;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_oferta, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.oferta = ofertas.get(position);
      /*  holder.tvNombre.setText(holder.oferta.get());
        holder.tvApellidos.setText(ofertas.get(position).getApellidos());
        holder.tvPoblacion.setText(ofertas.get(position).getPoblacion());
        holder.tvTelefono.setText(ofertas.get(position).getTelefono());*/


        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClick(holder.oferta);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return ofertas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNombre;
        public final TextView tvApellidos;
        public final TextView tvFecha;
        public final TextView tvPoblacion;

        public Oferta oferta;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = (TextView) view.findViewById(R.id.tv_oferta_nombre);
            tvApellidos = (TextView) view.findViewById(R.id.tv_oferta_apellidos);
            tvFecha = (TextView) view.findViewById(R.id.tv_oferta_fecha);
            tvPoblacion = (TextView) view.findViewById(R.id.tv_oferta_poblacion);


        }

    }

    public interface OfertaClickListener {
        void onItemClick(Oferta oferta);
    }
}
