package com.appcloud.frankiapp.Adapters;

import android.app.Application;
import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Configuration;

import java.util.List;


public class OfertasRecyclerViewAdapter extends RecyclerView.Adapter<OfertasRecyclerViewAdapter.ViewHolder> {

    private final List<Oferta> ofertas;
    private final OfertaClickListener mListener;
    private Context context;

    public OfertasRecyclerViewAdapter(Context context, List<Oferta> ofertas, OfertaClickListener listener) {
        this.ofertas = ofertas;
        mListener = listener;
        this.context = context;
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

        switch (holder.oferta.getEstado())
        {
            case Configuration.BORRADOR:
                holder.lnColor.setBackgroundColor(ContextCompat.getColor(context,R.color.colorBorrador));
                break;
            case Configuration.PRESENTADA:
                holder.lnColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPresentada));
                break;
            case Configuration.FIRMADA:
                holder.lnColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorFirmada));
                break;
            case Configuration.KO:
                holder.lnColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorKO));
                break;
            case Configuration.OK:
                holder.lnColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOK));
                break;
        }
        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClick(holder.mView,holder.oferta);
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
        public final LinearLayout lnColor;
        public final TextView tvNombre;
        public final TextView tvApellidos;
        public final TextView tvFecha;
        public final TextView tvPoblacion;

        public Oferta oferta;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lnColor = (LinearLayout)view.findViewById(R.id.ln_color);
            tvNombre = (TextView) view.findViewById(R.id.tv_oferta_nombre);
            tvApellidos = (TextView) view.findViewById(R.id.tv_oferta_apellidos);
            tvFecha = (TextView) view.findViewById(R.id.tv_oferta_fecha);
            tvPoblacion = (TextView) view.findViewById(R.id.tv_oferta_poblacion);


        }

    }

    public interface OfertaClickListener {
        void onItemClick(View vista,Oferta oferta);
    }
}
