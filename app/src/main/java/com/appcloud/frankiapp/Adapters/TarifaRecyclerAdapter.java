package com.appcloud.frankiapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appcloud.frankiapp.Interfaces.OnTarifaInteractionListener;
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.R;

import java.util.List;

/**
 * Created by cristian on 29/04/2016.
 */
public class TarifaRecyclerAdapter extends RecyclerView.Adapter<TarifaRecyclerAdapter.ViewHolder> {

    private final List<Tarifa> mValues;
    private final OnTarifaInteractionListener mListener;

    public TarifaRecyclerAdapter(List<Tarifa> items, OnTarifaInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.carview_tarifas, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvNombre.setText(mValues.get(position).getPlanPrecios());

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
        public Tarifa mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = (TextView) view.findViewById(R.id.tv_tarifa_nombre);
        }

    }
}
