package com.appcloud.frankiapp.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Configuration;

import java.util.List;

/**
 * Created by cristian on 05/05/2016.
 */
public class TerminalesBottomSheetAdapter extends RecyclerView.Adapter<TerminalesBottomSheetAdapter.ViewHolder> {

    private final List<Terminal> terminales;
    TerminalClickListener terminalClickListener;
    String planprecios;

    public TerminalesBottomSheetAdapter(List<Terminal> terminales, String planPrecios, TerminalClickListener listener) {
        this.terminales = terminales;
        this.planprecios = planPrecios;
        this.terminalClickListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.linea_buttonsheet_terminal, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        holder.terminal = terminales.get(position);
        holder.tvNombre.setText(terminales.get(position).getDescripcion());

        switch (planprecios)
        {
            case Configuration.XS:
                holder.tvInicial.setText(String.valueOf(holder.terminal.getXsInicial()));
                holder.tvCuota.setText(String.valueOf(holder.terminal.getXsCouta()));
                holder.tvPvp.setText(String.valueOf(holder.terminal.getXsPvp()));
                break;

            case Configuration.MINI:
                holder.tvInicial.setText(String.valueOf(holder.terminal.getMiniInicial()));
                holder.tvCuota.setText(String.valueOf(holder.terminal.getMiniCouta()));
                holder.tvPvp.setText(String.valueOf(holder.terminal.getMiniPvp()));
                break;

            case Configuration.S:
                holder.tvInicial.setText(String.valueOf(holder.terminal.getsInicial()));
                holder.tvCuota.setText(String.valueOf(holder.terminal.getsCouta()));
                holder.tvPvp.setText(String.valueOf(holder.terminal.getSpvp()));
                break;

            case Configuration.M:
                holder.tvInicial.setText(String.valueOf(holder.terminal.getmInicial()));
                holder.tvCuota.setText(String.valueOf(holder.terminal.getmCuota()));
                holder.tvPvp.setText(String.valueOf(holder.terminal.getmPvp()));
                break;

            case Configuration.L:
                holder.tvInicial.setText(String.valueOf(holder.terminal.getlInicial()));
                holder.tvCuota.setText(String.valueOf(holder.terminal.getlCuota()));
                holder.tvPvp.setText(String.valueOf(holder.terminal.getlPvp()));
                break;

            case Configuration.XL:
                holder.tvInicial.setText(String.valueOf(holder.terminal.getXlInicial()));
                holder.tvCuota.setText(String.valueOf(holder.terminal.getXlCuota()));
                holder.tvPvp.setText(String.valueOf(holder.terminal.getXlPvp()));
                break;


        }



        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                terminalClickListener.onItemClick(holder.terminal);
            }
        });
    }


    @Override
    public int getItemCount() {
        return terminales.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView tvNombre;
        public final TextView tvInicial;
        public final TextView tvCuota;
        public final TextView tvPvp;
        public Terminal terminal;

        public ViewHolder(View view) {
            super(view);
            mView = view;
            tvNombre = (TextView) view.findViewById(R.id.tv_terminal_desc);
            tvInicial = (TextView) view.findViewById(R.id.tv_terminal_inicial);
            tvCuota = (TextView) view.findViewById(R.id.tv_terminal_cuota);
            tvPvp = (TextView) view.findViewById(R.id.tv_terminal_pvp);

        }
    }

    public interface TerminalClickListener {
        void onItemClick(Terminal terminal);
    }
}