package com.appcloud.frankiapp.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.appcloud.frankiapp.Adapters.ClienteRecyclerViewAdapter;
import com.appcloud.frankiapp.Adapters.LineasRecyclerViewAdapter;
import com.appcloud.frankiapp.Adapters.OfertasRecyclerViewAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.POJO.Lineaoferta;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;

import java.util.ArrayList;

public class ActivityOferta extends AppCompatActivity {

    Context context = this;
    FloatingActionButton fab;
    TextView tvInicialTarifa, tvCuotaTarifa, tvInicialTerminal, tvCuotaterminal;
    RecyclerView recyclerView;
    LineasRecyclerViewAdapter.LineaClickListener lineaClickListener;
    Oferta oferta;
    int codigoOferta;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_oferta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        setTitle("Nueva Oferta");

        codigoOferta = getIntent().getIntExtra("oferta",-1);
        if(codigoOferta!=-1)
        {
           oferta = DatabaseHelper.getInstance(context).getOferta(codigoOferta);
        }
        else
        {
            oferta = new Oferta();
            DatabaseHelper.getInstance(this).createCabecceraOferta(oferta);
        }
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView)findViewById(R.id.list);
        tvInicialTarifa = (TextView)findViewById(R.id.tv_inicial_tarifa);
        tvCuotaTarifa = (TextView)findViewById(R.id.tv_cuota_tarifa);
        tvInicialTerminal = (TextView)findViewById(R.id.tv_inicial_terminal);
        tvCuotaterminal = (TextView)findViewById(R.id.tv_cuota_terminal);

        tvInicialTarifa.setText(String.valueOf(oferta.getPagoInicial()));
        tvCuotaTarifa.setText(String.valueOf(oferta.getCuotaMenseual()));
       // tvInicialTerminal.setText(String.valueOf(oferta.getPagoInicial()));
       // tvCuotaterminal.setText(String.valueOf(oferta.getPagoInicial()));


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        lineaClickListener = new LineasRecyclerViewAdapter.LineaClickListener() {
            @Override
            public void onItemClick(Lineaoferta linea) {

            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ActivityLinea.class);
                intent.putExtra("oferta",oferta.getCodOferta());
                startActivity(intent);
            }
        });

    }

    private class LineasAsynctask extends AsyncTask<String, Void, ArrayList<Lineaoferta>>
    {
        int codOferta;
        public LineasAsynctask(int codOferta)
        {
            this.codOferta = codOferta;
        }

        @Override
        protected ArrayList<Lineaoferta> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(context).getAllLineasOferta(codOferta);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute( ArrayList<Lineaoferta> result )
        {
            if(result!=null)
            {
                recyclerView.setAdapter(new LineasRecyclerViewAdapter(result,lineaClickListener));
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        LineasAsynctask lineasAsynctask = new LineasAsynctask(codigoOferta);
        lineasAsynctask.execute();
    }
}
