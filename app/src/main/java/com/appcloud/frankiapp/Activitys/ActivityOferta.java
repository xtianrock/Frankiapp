package com.appcloud.frankiapp.Activitys;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.appcloud.frankiapp.Adapters.ClienteRecyclerViewAdapter;
import com.appcloud.frankiapp.Adapters.ClientesBottomSheetAdapter;
import com.appcloud.frankiapp.Adapters.LineasRecyclerViewAdapter;
import com.appcloud.frankiapp.Adapters.TerminalesBottomSheetAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Fragments.ClienteFragment;
import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.POJO.Lineaoferta;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Commons;
import com.appcloud.frankiapp.Utils.Configuration;

import java.util.ArrayList;

public class ActivityOferta extends AppCompatActivity {

    Context context = this;
    LinearLayout lnResumen;
    FloatingActionButton fab;
    Button altaCliente;
    TextView tvTitulo, tvInicialTarifa, tvCuotaTarifa, tvInicialTerminal, tvCuotaterminal;
    RecyclerView recyclerView;
    LineasRecyclerViewAdapter.LineaClickListener lineaClickListener;
    BottomSheetBehavior behavior;
    MenuItem itemFirmar, itemPresentar, itemThumbok, itemThumbko, itemActionEdit, itemActionDelete;
    private BottomSheetDialog mBottomSheetDialog;
    ClientesBottomSheetAdapter clientesBSheetAdapter;
    View bottomSheet;
    ArrayList<Cliente> clientes;
    Cliente clienteSeleccionado;
    Oferta oferta;
    public int codigoOferta = -1;
    boolean firstTime = true;
    boolean edicion = false;
    String currentFragmentTag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarOferta();
        setTheme(Commons.getTema(oferta.getEstado()));
        setContentView(R.layout.activity_oferta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomSheet = findViewById(R.id.bottom_sheet_cliente);
        lnResumen = (LinearLayout) findViewById(R.id.ln_resumen);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView)findViewById(R.id.list);
        tvTitulo = (TextView)findViewById(R.id.tv_toolbar_titulo);
        tvInicialTarifa = (TextView)findViewById(R.id.tv_inicial_tarifa);
        tvCuotaTarifa = (TextView)findViewById(R.id.tv_cuota_tarifa);
        tvInicialTerminal = (TextView)findViewById(R.id.tv_inicial_terminal);
        tvCuotaterminal = (TextView)findViewById(R.id.tv_cuota_terminal);


        behavior = BottomSheetBehavior.from(bottomSheet);


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        lineaClickListener = new LineasRecyclerViewAdapter.LineaClickListener() {
            @Override
            public void onItemClick(Lineaoferta linea) {
                if ( oferta.getEstado().equalsIgnoreCase(Configuration.BORRADOR)) {
                    Intent intent = new Intent(context, ActivityLinea.class);
                    intent.putExtra("oferta", codigoOferta);
                    intent.putExtra("estado", oferta.getEstado());
                    intent.putExtra("lineaActual", linea.getCodLinea());
                    startActivity(intent);
                }
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

        if (!oferta.getEstado().equalsIgnoreCase("borrador"))
            fab.setVisibility(View.GONE);

    }

    @Override
    protected void onResume() {
        super.onResume();
        inicializarOferta();
        setActionBar();

        if(!firstTime)
            inicializarOferta();
        firstTime=false;
    }

    // nuevo cliente dado de alta en el bottomsheet
    public void informacionFragmentCliente(Cliente nuevoCliente){
        clienteAsignadoBorrador(nuevoCliente);
    }

    private void actualizaEstadoOferta(){

        Intent intent = new Intent(context, ActivityOferta.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("oferta",((ActivityOferta) context).codigoOferta);
        startActivity(intent);
        overridePendingTransition(0,0); //0 for no animation
        finish();
    }

    public void mostrarBottomSeet() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_clientes, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerViewClientes);
        recyclerView.setHasFixedSize(true);
        clientesBSheetAdapter = new ClientesBottomSheetAdapter(clientes, new ClientesBottomSheetAdapter.ClienteBSheetClickListener() {
            @Override
            public void onItemClick(Cliente cliente) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                    clienteAsignadoBorrador(cliente);
                    actualizaEstadoOferta();

                    tvTitulo.setText(clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos());

                }
            }
        });
        recyclerView.setAdapter(clientesBSheetAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

        altaCliente = (Button)view.findViewById(R.id.btnsheet_alta_cliente);

        altaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragment(new ClienteFragment(),"alta_cliente",false);
                mBottomSheetDialog.dismiss();

            }
        });
        mBottomSheetDialog.show();

    }

    private void clienteAsignadoBorrador(Cliente cliente){

        clienteSeleccionado = cliente;
        oferta.setEstado(Configuration.PRESENTADA);
        oferta.setCodCliente(cliente.getCodCliente());
        DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
        enviaOferta();

        tvTitulo.setText(clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos());
    }

    private void enviaOferta(){
        //TODO crear oferta en pdf
    }

    private void setActionBar()
    {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        switch (oferta.getEstado())
        {
            case Configuration.BORRADOR:
                lnResumen.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBorrador));
                break;
            case Configuration.PRESENTADA:
                lnResumen.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPresentada));
                break;
            case Configuration.FIRMADA:
                lnResumen.setBackgroundColor(ContextCompat.getColor(context, R.color.colorFirmada));
                break;
            case Configuration.KO:
                lnResumen.setBackgroundColor(ContextCompat.getColor(context, R.color.colorKO));
                break;
            case Configuration.OK:
                lnResumen.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOK));
                break;
        }
        if(codigoOferta!=-1)
        {
            if (oferta.getCodCliente() != 0 ) {
                Cliente cliente = DatabaseHelper.getInstance(context).getCliente(oferta.getCodCliente());
                tvTitulo.setText(cliente.getNombre() + " " + cliente.getApellidos());
            }else

                tvTitulo.setText("Borrador #"+codigoOferta);
        }
        else
        {
            oferta = new Oferta();
            codigoOferta = (int)DatabaseHelper.getInstance(this).createCabecceraOferta(oferta);
            tvTitulo.setText("Nueva Oferta");

        }
        tvInicialTarifa.setText(String.valueOf(oferta.getPagoInicialTarifa()));
        tvCuotaTarifa.setText(String.valueOf(oferta.getCuotaTarifa()));
        tvInicialTerminal.setText(String.valueOf(oferta.getPagoInicialTerminal()));
        tvCuotaterminal.setText(String.valueOf(oferta.getCuotaTerminal()));

    }

    private void inicializarOferta() {
        if(codigoOferta==-1)
            codigoOferta = getIntent().getIntExtra("oferta",-1);
        if(codigoOferta!=-1)
        {
            oferta = DatabaseHelper.getInstance(context).getOferta(codigoOferta);
        }
        else
        {
            oferta = new Oferta();
            codigoOferta = (int)DatabaseHelper.getInstance(this).createCabecceraOferta(oferta);
        }

               LineasAsynctask lineasAsynctask = new LineasAsynctask(codigoOferta);
        lineasAsynctask.execute();

        ClientesAsynctask terminalesAsynctask = new ClientesAsynctask();
        terminalesAsynctask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oferta, menu);

       // MenuInflater inflater = getMenuInflater();
       // inflater.inflate(R.menu.your_menu, menu);
        itemFirmar = menu.findItem(R.id.action_firmar);
        itemPresentar = menu.findItem(R.id.action_presentar);
        itemThumbko = menu.findItem(R.id.action_estado_ko);
        itemThumbok = menu.findItem(R.id.action_estado_ok);

        itemActionEdit = menu.findItem((R.id.action_editar));
        itemActionDelete = menu.findItem(R.id.action_delete);

        switch (oferta.getEstado()){
            case Configuration.PRESENTADA:
                itemFirmar.setVisible(true);
                itemPresentar.setVisible(false);
                itemThumbok.setVisible(false);
                itemThumbko.setVisible(false);
                break;
            case Configuration.BORRADOR:
                itemFirmar.setVisible(false);
                itemPresentar.setVisible(true);
                itemThumbok.setVisible(false);
                itemThumbko.setVisible(false);
                break;
            case Configuration.FIRMADA:
                itemFirmar.setVisible(false);
                itemPresentar.setVisible(false);
               // itemThumbok.setVisible(true);
                //itemThumbko.setVisible(true);
                break;

            default:
                itemPresentar.setVisible(false);
                itemFirmar.setVisible(false);
                itemThumbok.setVisible(false);
                itemThumbko.setVisible(false);

                itemActionEdit.setVisible(false);
                itemActionDelete.setVisible(false);

        }

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            finish();
            return true;
        }
        if (id == R.id.action_presentar) {
            if (recyclerView.getAdapter().getItemCount() > 0 ) {
                if (oferta.getCodCliente() == 0)
                    mostrarBottomSeet();
                else
                    enviaOferta();
            }
            else {
                Snackbar.make(fab, "Debe Introducir una línea de oferta antes de presentar al cliente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return true;
        }

        if (id == R.id.action_firmar) {
            oferta.setEstado(Configuration.FIRMADA);
            DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
            actualizaEstadoOferta();
            return true;
        }

        if (id == R.id.action_estado_ok) {
            oferta.setEstado(Configuration.OK);
            DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
            actualizaEstadoOferta();
            return true;
        }

        if (id == R.id.action_estado_ko) {
            oferta.setEstado(Configuration.KO);
            DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
            actualizaEstadoOferta();
            return true;
        }


        if (id == R.id.action_delete) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOferta.this);
            builder.setCancelable(true);
            builder.setTitle(getString(R.string.confirmation_action));
            builder.setMessage(getString(R.string.action_delete));
            builder.setPositiveButton("Confirmar",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            DatabaseHelper.getInstance(context).deleteOferta(oferta);
                            finish();

                        }
                    });
            builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();


            return true;
        }

        if (id == R.id.action_editar) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOferta.this);
            builder.setCancelable(true);
            builder.setTitle(getString(R.string.confirmation_action));
            builder.setMessage(getString(R.string.action_edit_oferta));
            builder.setPositiveButton("Confirmar",
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            oferta.setEstado(Configuration.BORRADOR);
                            DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
                            actualizaEstadoOferta();

                        }
                    });
                builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

            return true;
        }



        return super.onOptionsItemSelected(item);
    }

    public void switchToFragment(Fragment fragment, String title, boolean backStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getCanonicalName();
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);


        if (currentFragment == null || !TextUtils.equals(tag, currentFragmentTag)) {
            if(backStack)
            {
                currentFragmentTag = tag;
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_activity_oferta, fragment, currentFragmentTag)
                        .addToBackStack(tag)
                        .commit();
            }
            else
            {
                currentFragmentTag = tag;
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_activity_oferta, fragment, currentFragmentTag)
                        .commit();
            }

        }
    }

    private class ClientesAsynctask extends AsyncTask<String, Void, ArrayList<Cliente>>
    {
        public ClientesAsynctask()
        {}

        @Override
        protected ArrayList<Cliente> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(context).getAllClientes();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute( ArrayList<Cliente> result )
        {
            if(result!=null)
            {
                clientes = result;
            }
        }
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
