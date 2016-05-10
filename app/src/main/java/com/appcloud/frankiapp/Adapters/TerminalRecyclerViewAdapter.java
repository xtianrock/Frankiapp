package com.appcloud.frankiapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;

import java.util.List;


public class TerminalRecyclerViewAdapter extends RecyclerView.Adapter<TerminalRecyclerViewAdapter.ViewHolder> {

    private final List<Terminal> mValues;
    private final OnTerminalInteractionListener mListener;

    public TerminalRecyclerViewAdapter(List<Terminal> items, OnTerminalInteractionListener listener) {
        mValues = items;
        mListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.cardview_terminales, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.mItem = mValues.get(position);
        holder.tvNombre.setText(mValues.get(position).getDescripcion());
        holder.tvColor.setText(mValues.get(position).getColor());
        holder.tvLte.setText(mValues.get(position).getLte());
        holder.tvSim.setText(mValues.get(position).getSim());

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
        public final TextView tvColor;
        public final TextView tvLte;
        public final TextView tvSim;
        public Terminal mItem;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = (TextView) view.findViewById(R.id.tv_terminal_nombre);
            tvColor = (TextView) view.findViewById(R.id.tv_terminal_color);
            tvLte = (TextView) view.findViewById(R.id.tv_terminal_lte);
            tvSim = (TextView) view.findViewById(R.id.tv_terminal_sim);

        }

    }
}
