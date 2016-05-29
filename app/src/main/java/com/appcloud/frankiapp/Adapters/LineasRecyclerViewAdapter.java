package com.appcloud.frankiapp.Adapters;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Lineaoferta;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Configuration;

import java.util.List;


public class LineasRecyclerViewAdapter extends RecyclerView.Adapter<LineasRecyclerViewAdapter.ViewHolder> {

    private final List<Lineaoferta> lineas;
    private final LineaClickListener mListener;
    private final String estadoOferta;
    private Context context;

    public LineasRecyclerViewAdapter(List<Lineaoferta> lineas, LineaClickListener listener, String estado, Context context) {
        this.lineas = lineas;
        this.context = context;
        mListener = listener;
        this.estadoOferta = estado;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_linea, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.linea = lineas.get(position);
        holder.tvTarifa.setText(holder.linea.getPlanPrecios());
        // holder.lnColor.setBackground(Color.);

        if (holder.linea.getTipoPlan().equalsIgnoreCase(Configuration.MOVIL))
            holder.ivTipoPlan.setImageResource(R.drawable.ic_terminal_negro);
        else
            holder.ivTipoPlan.setImageResource(R.drawable.ic_local_phone_black_24dp);

        switch (this.estadoOferta)
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
                    mListener.onItemClick(v, holder.linea);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return lineas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvTarifa;
        public final ImageView ivTipoPlan;
        public final LinearLayout lnColor;


        public Lineaoferta linea;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            lnColor = (LinearLayout)view.findViewById(R.id.ln_color);
            tvTarifa = (TextView) view.findViewById(R.id.tv_linea_tarifa);
            ivTipoPlan = (ImageView) view.findViewById(R.id.iv_tipoPlan);
        }

    }

    public interface LineaClickListener {
        void onItemClick(View vista, Lineaoferta linea);
    }
}
