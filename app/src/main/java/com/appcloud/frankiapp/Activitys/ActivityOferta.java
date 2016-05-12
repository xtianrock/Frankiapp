package com.appcloud.frankiapp.Activitys;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appcloud.frankiapp.Adapters.LineasRecyclerViewAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.POJO.Lineaoferta;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Configuration;

import java.util.ArrayList;

public class ActivityOferta extends AppCompatActivity {

    Context context = this;
    LinearLayout lnResumen;
    FloatingActionButton fab;
    TextView tvTitulo, tvInicialTarifa, tvCuotaTarifa, tvInicialTerminal, tvCuotaterminal;
    RecyclerView recyclerView;
    LineasRecyclerViewAdapter.LineaClickListener lineaClickListener;
    Oferta oferta;
    int codigoOferta = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme_NoActionBar_ok);
        setContentView(R.layout.activity_oferta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);


        lnResumen = (LinearLayout) findViewById(R.id.ln_resumen);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView)findViewById(R.id.list);
        tvTitulo = (TextView)findViewById(R.id.tv_toolbar_titulo);
        tvInicialTarifa = (TextView)findViewById(R.id.tv_inicial_tarifa);
        tvCuotaTarifa = (TextView)findViewById(R.id.tv_cuota_tarifa);
        tvInicialTerminal = (TextView)findViewById(R.id.tv_inicial_terminal);
        tvCuotaterminal = (TextView)findViewById(R.id.tv_cuota_terminal);


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        lineaClickListener = new LineasRecyclerViewAdapter.LineaClickListener() {
            @Override
            public void onItemClick(Lineaoferta linea) {
                Intent intent = new Intent(context,ActivityLinea.class);
                intent.putExtra("oferta",codigoOferta);
                intent.putExtra("lineaActual",linea.getCodLinea());
                startActivity(intent);
            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,ActivityLinea.class);
                intent.putExtra("oferta",codigoOferta);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        if(codigoOferta==-1)
            codigoOferta = getIntent().getIntExtra("oferta",-1);
        if(codigoOferta!=-1)
        {
            oferta = DatabaseHelper.getInstance(context).getOferta(codigoOferta);
            switch (oferta.getEstado())
            {
                case Configuration.BORRADOR: lnResumen.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOK));
                    tvTitulo.setText("Cristian Vizcaino Alvarez");
                    break;
            }
        }
        else
        {
            oferta = new Oferta();
            codigoOferta = (int)DatabaseHelper.getInstance(this).createCabecceraOferta(oferta);
            setTitle("Nueva Oferta");
        }
        if(getIntent().getBooleanExtra("linea_guardada",false))
            Snackbar.make(fab, "Linea añadida con exito", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show();
        tvInicialTarifa.setText(String.valueOf(oferta.getPagoInicialTarifa()));
        tvCuotaTarifa.setText(String.valueOf(oferta.getCuotaTarifa()));
        tvInicialTerminal.setText(String.valueOf(oferta.getPagoInicialTerminal()));
        tvCuotaterminal.setText(String.valueOf(oferta.getCuotaTerminal()));

        LineasAsynctask lineasAsynctask = new LineasAsynctask(codigoOferta);
        lineasAsynctask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oferta, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_presentar) {
            oferta.setEstado(Configuration.PRESENTADA);
            DatabaseHelper.getInstance(context).updateCabeceraOferta(oferta);
            return true;
        }

        if (id == R.id.action_delete) {
            DatabaseHelper.getInstance(context).deleteOferta(oferta);
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
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
}
