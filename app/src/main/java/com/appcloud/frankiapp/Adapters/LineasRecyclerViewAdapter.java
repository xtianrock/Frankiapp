package com.appcloud.frankiapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Lineaoferta;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;

import java.util.List;


public class LineasRecyclerViewAdapter extends RecyclerView.Adapter<LineasRecyclerViewAdapter.ViewHolder> {

    private final List<Lineaoferta> lineas;
    private final LineaClickListener mListener;

    public LineasRecyclerViewAdapter(List<Lineaoferta> lineas, LineaClickListener listener) {
        this.lineas = lineas;
        mListener = listener;
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



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != mListener) {
                    // Notify the active callbacks interface (the activity, if the
                    // fragment is attached to one) that an item has been selected.
                    mListener.onItemClick(holder.linea);
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

        public Lineaoferta linea;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvTarifa = (TextView) view.findViewById(R.id.tv_linea_tarifa);



        }

    }

    public interface LineaClickListener {
        void onItemClick(Lineaoferta linea);
    }
}
