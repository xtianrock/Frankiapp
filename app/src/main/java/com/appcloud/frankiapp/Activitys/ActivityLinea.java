package com.appcloud.frankiapp.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.appcloud.frankiapp.Adapters.SpinnerConvergenciaAdapter;
import com.appcloud.frankiapp.Adapters.SpinnerTarifaAdapter;
import com.appcloud.frankiapp.Adapters.TerminalesBottomSheetAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.POJO.Lineaoferta;
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Commons;
import com.appcloud.frankiapp.Utils.Configuration;

import java.util.ArrayList;

public class ActivityLinea extends AppCompatActivity {

    Context context = this;
    Lineaoferta lineaActual;
    int codigoOferta, codigoLinea;
    TextView tvInicialTarifa, tvCuotaTarifa, tvInicialTerminal, tvCuotaterminal, tvDescripcion, tvTerminal;
    LinearLayout lnResumen, lnPrincipal, lnDescripcion, lnAlta, lnPorta, lnSimonly, lnTerminal, lnConvergenciaMovil, lnConvergenciaFibra;
    RadioButton rbAlta, rbPorta;
    CheckBox cbSimonly, cbTerminal, cbConvergenciaMovil, cbConvergenciaFibra;
    EditText etTelefono;
    Spinner spTarifa, spOperador, spConvergencia;
    Toolbar toolbar;
    View bottomSheet;
    String estado = Configuration.BORRADOR;
    ArrayList<Tarifa> tarifas;
    ArrayList<Terminal> terminales;
    Tarifa tarifaSeleccionada;
    Terminal terminalSeleccionado;
    BottomSheetBehavior behavior;
    SpinnerConvergenciaAdapter adapterConvergencia;
    private BottomSheetDialog mBottomSheetDialog;
    TerminalesBottomSheetAdapter terminalesAdapter;
    FloatingActionButton fab;
    boolean touchByUser = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //añadimos el tema
        if(getIntent().getStringExtra("estado")!=null)
            estado=getIntent().getStringExtra("estado");
        setTheme(Commons.getTema(estado));
        setContentView(R.layout.activity_linea);
        lnResumen = (LinearLayout) findViewById(R.id.ln_resumen);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        bottomSheet = findViewById(R.id.bottom_sheet);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvInicialTarifa = (TextView)findViewById(R.id.tv_inicial_tarifa);
        tvCuotaTarifa = (TextView)findViewById(R.id.tv_cuota_tarifa);
        tvInicialTerminal = (TextView)findViewById(R.id.tv_inicial_terminal);
        tvCuotaterminal = (TextView)findViewById(R.id.tv_cuota_terminal);
        tvDescripcion = (TextView)findViewById(R.id.tv_linea_desc);
        tvTerminal = (TextView)findViewById(R.id.tv_linea_terminal);
        lnPrincipal = (LinearLayout) findViewById(R.id.ln_linea_principal);
        lnDescripcion = (LinearLayout) findViewById(R.id.ln_linea_descripcion);
        lnAlta = (LinearLayout) findViewById(R.id.ln_linea_alta);
        lnPorta = (LinearLayout) findViewById(R.id.ln_linea_porta);
        lnSimonly = (LinearLayout) findViewById(R.id.ln_linea_simonly);
        lnTerminal = (LinearLayout) findViewById(R.id.ln_linea_terminal);
        lnConvergenciaMovil = (LinearLayout) findViewById(R.id.ln_linea_convergencia_movil);
        lnConvergenciaFibra = (LinearLayout) findViewById(R.id.ln_linea_convergencia_fibra);
        etTelefono = (EditText) findViewById(R.id.et_linea_telefono);
        rbAlta = (RadioButton) findViewById(R.id.rb_linea_alta);
        rbPorta = (RadioButton) findViewById(R.id.rb_linea_porta);
        cbSimonly = (CheckBox)findViewById(R.id.cb_linea_simonly);
        cbTerminal = (CheckBox)findViewById(R.id.cb_linea_terminal);
        cbConvergenciaMovil = (CheckBox)findViewById(R.id.cb_linea_convergencia_movil);
        cbConvergenciaFibra = (CheckBox)findViewById(R.id.cb_linea_convergencia_fibra);
        spTarifa = (Spinner)findViewById(R.id.sp_linea_tarifa);
        spOperador = (Spinner) findViewById(R.id.sp_linea_operador);
        spConvergencia = (Spinner) findViewById(R.id.sp_linea_converngencia);

        codigoOferta = getIntent().getIntExtra("oferta",-1);
        codigoLinea = getIntent().getIntExtra("lineaActual",-1);
        if(codigoLinea!=-1)
        {
            lineaActual = DatabaseHelper.getInstance(context).getLineaOferta(codigoLinea);
        }
        else
            lineaActual = new Lineaoferta(codigoOferta);


        // adapter con el listado de operadoras donantes
        ArrayAdapter<String> adapterOperadoras = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.array_operadores));
        spOperador.setAdapter(adapterOperadoras);

        // adapter con el listado de tipos de convergencia
        adapterConvergencia = new SpinnerConvergenciaAdapter(
                context,
                R.layout.linea_spinner_convergencia,
                Configuration.TIPOSCONVERGENCIA, tarifaSeleccionada);
        spConvergencia.setAdapter(adapterConvergencia);

        behavior = BottomSheetBehavior.from(bottomSheet);
        /*behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });*/

        if(codigoLinea!=-1)
            prepararControles();
        prepararOnClicks();
        prepararActionbar();
        TarifasAsyncTask tarifasAsyncTask = new TarifasAsyncTask(this);
        tarifasAsyncTask.execute();

    }


    public void prepararControles()    {
        Tarifa tarifa = DatabaseHelper.getInstance(context).getTarifaByCod(lineaActual.getCodTarifa());
        tarifaSeleccionada = tarifa;
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        tarifas.add(tarifa);
        spTarifa.setAdapter(new SpinnerTarifaAdapter(context,android.R.layout.simple_spinner_item,tarifas));
        spTarifa.setSelection(tarifas.indexOf(tarifaSeleccionada));
        tvInicialTarifa.setText(String.valueOf(lineaActual.getPrecioTarifaInicial()));
        tvCuotaTarifa.setText(String.valueOf(lineaActual.getPrecioTarifaCuota()));
        tvInicialTerminal.setText(String.valueOf(lineaActual.getPrecioTerminalInicial()));
        tvCuotaterminal.setText(String.valueOf(lineaActual.getPrecioTErminalCuota()));
        if(tarifaSeleccionada.getTipoPlan().equals(Configuration.MOVIL))
        {
            lnDescripcion.setVisibility(View.VISIBLE);
            lnAlta.setVisibility(View.VISIBLE);
            lnSimonly.setVisibility(View.VISIBLE);
            lnConvergenciaFibra.setVisibility(View.VISIBLE);
            lnConvergenciaMovil.setVisibility(View.GONE);
            TerminalesAsynctask terminalesAsynctask = new TerminalesAsynctask();
            terminalesAsynctask.execute();
        }
        else if (tarifaSeleccionada.getTipoPlan().equals(Configuration.ADSL) || tarifaSeleccionada.getTipoPlan().equals(Configuration.FIBRA))
        {
            lnAlta.setVisibility(View.VISIBLE);
            lnConvergenciaMovil.setVisibility(View.VISIBLE);
            lnDescripcion.setVisibility(View.GONE);
            lnSimonly.setVisibility(View.GONE);
            lnConvergenciaFibra.setVisibility(View.GONE);
            lnTerminal.setVisibility(View.GONE);
            tvInicialTarifa.setText(String.valueOf(lineaActual.getPrecioTarifaInicial()));
            tvCuotaTarifa.setText(String.valueOf(lineaActual.getPrecioTarifaCuota()));
        }
        if(lineaActual.getOperadorDonante()!=null) // si no es nulo, se trata de una portabilidad
        {
            rbPorta.setChecked(true);
            etTelefono.setText(lineaActual.getNumeroTelefono());
            spOperador.setSelection(getOperador(lineaActual.getOperadorDonante()));
            lnPorta.setVisibility(View.VISIBLE);
        }
        if(lineaActual.getCodTerminal()!=0)
        {
            Terminal terminal = DatabaseHelper.getInstance(context).getTerminalByCod(lineaActual.getCodTerminal());
            tvTerminal.setText(terminal.getDescripcion());
            cbTerminal.setChecked(true);
            cbSimonly.setChecked(false);
            lnTerminal.setVisibility(View.VISIBLE);
        }
        if(lineaActual.getTipoConvergencia()!=null)
        {
            cbConvergenciaMovil.setChecked(true);
            // adapter con el listado de tipos de convergencia
            final SpinnerConvergenciaAdapter adapterConvergencia = new SpinnerConvergenciaAdapter(
                    context,
                    R.layout.linea_spinner_convergencia,
                    Configuration.TIPOSCONVERGENCIA, tarifaSeleccionada);
            spConvergencia.setAdapter(adapterConvergencia);
            spConvergencia.setSelection(getTipoConvergencia(lineaActual.getTipoConvergencia()));
            spConvergencia.setVisibility(View.VISIBLE);
        }
        if(lineaActual.getConvergenciaMovil()!=null && lineaActual.getConvergenciaMovil().equals("si"))
        {
            cbConvergenciaFibra.setChecked(true);
        }


    }

    public void prepararOnClicks()    {
        spTarifa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if(touchByUser)
                {tarifaSeleccionada = tarifas.get(position);
                    terminalSeleccionado = null;
                    tvDescripcion.setText(tarifaSeleccionada.getDesCorta());
                    lnPorta.setVisibility(View.GONE);
                    resetResumenTarifa(); //pone a 0 el resumen de precios
                    resetResumenTerminal();
                    rbAlta.setChecked(true);
                    cbSimonly.setChecked(true);
                    if(tarifaSeleccionada.getTipoPlan().equals(Configuration.MOVIL))
                    {
                        lnDescripcion.setVisibility(View.VISIBLE);
                        lnAlta.setVisibility(View.VISIBLE);
                        lnSimonly.setVisibility(View.VISIBLE);
                        if(tarifaSeleccionada.getPrecioConvergenciaMovil()!=0)
                            lnConvergenciaFibra.setVisibility(View.VISIBLE);
                        lnConvergenciaMovil.setVisibility(View.GONE);
                        double precio = Math.round(tarifaSeleccionada.getPrecioConTerminal()*0.85 * 100.0) / 100.0;
                        tvCuotaTarifa.setText(String.valueOf(precio));
                        TerminalesAsynctask terminalesAsynctask = new TerminalesAsynctask();
                        terminalesAsynctask.execute();
                    }
                    else if (tarifaSeleccionada.getTipoPlan().equals(Configuration.ADSL) || tarifaSeleccionada.getTipoPlan().equals(Configuration.FIBRA))
                    {
                        lnAlta.setVisibility(View.VISIBLE);
                        lnConvergenciaMovil.setVisibility(View.VISIBLE);
                        lnDescripcion.setVisibility(View.GONE);
                        lnSimonly.setVisibility(View.GONE);
                        lnConvergenciaFibra.setVisibility(View.GONE);
                        lnTerminal.setVisibility(View.GONE);
                        tvInicialTarifa.setText(String.valueOf(tarifaSeleccionada.getCosteAlta()));
                        tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioSinConv()));
                    }

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        rbPorta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    lnPorta.setVisibility(View.VISIBLE);
                    tvInicialTarifa.setText("0.0");
                }
                else
                {
                    lnPorta.setVisibility(View.GONE);
                    tvInicialTarifa.setText(String.valueOf(tarifaSeleccionada.getCosteAlta()));
                }
            }
        });

        cbTerminal.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    if(cbConvergenciaFibra.isChecked())
                        tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioConvergenciaMovil()));
                    else
                        tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioConTerminal()));

                    cbSimonly.setChecked(false);
                }
                else
                {
                    lnTerminal.setVisibility(View.GONE);
                    resetResumenTerminal();
                    lineaActual.setCodTerminal(0);
                }
            }
        });
        cbTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(cbTerminal.isChecked())
                    mostrarBottomSeet();
            }
        });

        tvTerminal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarBottomSeet();
            }
        });

        cbSimonly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    cbTerminal.setChecked(false);
                    double precio;
                    if(cbConvergenciaFibra.isChecked())
                        precio = Math.round(tarifaSeleccionada.getPrecioConvergenciaMovil()*0.85 * 100.0) / 100.0;
                    else
                        precio = Math.round(tarifaSeleccionada.getPrecioConTerminal()*0.85 * 100.0) / 100.0;

                    tvCuotaTarifa.setText(String.valueOf(precio));
                }
                else {
                    if(cbConvergenciaFibra.isChecked())
                        tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioConvergenciaMovil()));
                    else
                        tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioConTerminal()));
                }
            }
        });

        cbConvergenciaMovil.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    // adapter con el listado de tipos de convergencia
                    final SpinnerConvergenciaAdapter adapterConvergencia = new SpinnerConvergenciaAdapter(
                            context,
                            R.layout.linea_spinner_convergencia,
                            Configuration.TIPOSCONVERGENCIA, tarifaSeleccionada);

                    spConvergencia.setAdapter(adapterConvergencia);
                    spConvergencia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            tvCuotaTarifa.setText(String.valueOf(adapterConvergencia.getPrecio(position)));
                            lineaActual.setTipoConvergencia(Configuration.TIPOSCONVERGENCIA[position]);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
                    spConvergencia.setVisibility(View.VISIBLE);
                }
                else
                {
                    spConvergencia.setVisibility(View.GONE);
                }
            }
        });

        cbConvergenciaFibra.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked)
                {
                    lineaActual.setConvergenciaMovil("si");
                    if (cbSimonly.isChecked())
                    {
                        double precio = Math.round(tarifaSeleccionada.getPrecioConvergenciaMovil()*0.85 * 100.0) / 100.0;
                        tvCuotaTarifa.setText(String.valueOf(precio));
                    }
                    else
                        tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioConvergenciaMovil()));
                }
                else
                {
                    if (cbSimonly.isChecked())
                    {
                        double precio = Math.round(tarifaSeleccionada.getPrecioConTerminal()*0.85 * 100.0) / 100.0;
                        tvCuotaTarifa.setText(String.valueOf(precio));
                    }
                    else
                        tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioConTerminal()));
                }

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lineaActual.setCodTarifa(tarifaSeleccionada.getCodTarifa());
                lineaActual.setPlanPrecios(tarifaSeleccionada.getPlanPrecios());

                if(terminalSeleccionado!=null)
                    lineaActual.setCodTerminal(terminalSeleccionado.getCodTerminal());

                //lineaActual.setTipoConvergencia();
                if(rbPorta.isChecked())
                {
                    lineaActual.setNumeroTelefono(etTelefono.getText().toString());
                    lineaActual.setOperadorDonante(spOperador.getSelectedItem().toString());
                }
                //lineaActual.setComisionBase();
                //lineaActual.setComisionExtra();

                if(codigoLinea!=-1) // si esta editando una linea
                {
                    Lineaoferta lineaPreciosNuevos = new Lineaoferta(-1);// En esta nueva linea almacena los precios nuevos que se hayan podido generar
                    lineaPreciosNuevos.setPrecioTarifaInicial(Float.parseFloat(tvInicialTarifa.getText().toString()));
                    lineaPreciosNuevos.setPrecioTarifaCuota(Float.parseFloat(tvCuotaTarifa.getText().toString()));
                    lineaPreciosNuevos.setPrecioTerminalInicial(Float.parseFloat(tvInicialTerminal.getText().toString()));
                    lineaPreciosNuevos.setPrecioTErminalCuota(Float.parseFloat(tvCuotaterminal.getText().toString()));
                    DatabaseHelper.getInstance(context).updateLineaOferta(lineaActual,lineaPreciosNuevos);

                }
                else // linea nueva
                {
                    lineaActual.setPrecioTarifaInicial(Float.parseFloat(tvInicialTarifa.getText().toString()));
                    lineaActual.setPrecioTarifaCuota(Float.parseFloat(tvCuotaTarifa.getText().toString()));
                    lineaActual.setPrecioTerminalInicial(Float.parseFloat(tvInicialTerminal.getText().toString()));
                    lineaActual.setPrecioTErminalCuota(Float.parseFloat(tvCuotaterminal.getText().toString()));
                    DatabaseHelper.getInstance(context).createLineaOferta(lineaActual);
                }

                finish();
            }
        });

    }

    public void mostrarBottomSeet()    {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setTitle("Selecciones un terminal");
        mBottomSheetDialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        terminalesAdapter = new TerminalesBottomSheetAdapter(terminales,tarifaSeleccionada.getPlanPrecioTerminal(),new TerminalesBottomSheetAdapter.TerminalClickListener() {
            @Override
            public void onItemClick(Terminal terminal) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                    terminalSeleccionado = terminal;
                    tvTerminal.setText(terminal.getDescripcion());
                    lnTerminal.setVisibility(View.VISIBLE);

                    switch (tarifaSeleccionada.getPlanPrecioTerminal())
                    {
                        case Configuration.XS:
                            tvInicialTerminal.setText(String.valueOf(terminal.getXsInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getXsCouta()));
                            break;

                        case Configuration.MINI:
                            tvInicialTerminal.setText(String.valueOf(terminal.getMiniInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getMiniCouta()));
                            break;

                        case Configuration.S:
                            tvInicialTerminal.setText(String.valueOf(terminal.getsInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getsCouta()));
                            break;

                        case Configuration.M:
                            tvInicialTerminal.setText(String.valueOf(terminal.getmInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getmCuota()));
                            break;

                        case Configuration.L:
                            tvInicialTerminal.setText(String.valueOf(terminal.getlInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getlCuota()));
                            break;

                        case Configuration.XL:
                           tvInicialTerminal.setText(String.valueOf(terminal.getXlInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getXlCuota()));
                            break;


                    }
                }
            }
        });
        recyclerView.setAdapter(terminalesAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
                if (lnTerminal.getVisibility()!=View.VISIBLE)
                {
                    lnTerminal.setVisibility(View.GONE);
                    cbSimonly.setChecked(true);
                }
            }
        });
        mBottomSheetDialog.show();
    }

    public void prepararActionbar() {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(lineaActual.getPlanPrecios());
        switch (estado)
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
    }

    public void resetResumenTarifa()    {
        tvInicialTarifa.setText("0.0");
        tvCuotaTarifa.setText("0.0");
    }

    public void resetResumenTerminal()    {
        tvInicialTerminal.setText("0.0");
        tvCuotaterminal.setText("0.0");
    }

    public int getOperador(String nombreOperador)    {
        String[] operadores = getResources().getStringArray(R.array.array_operadores);
        for (int i=0;i<operadores.length;i++)
            if(operadores[i].equals(nombreOperador))
                return i;
        return -1;
    }

    public int getTipoConvergencia(String tipoConvergencia)    {
        for (int i=0;i<Configuration.TIPOSCONVERGENCIA.length;i++)
            if(Configuration.TIPOSCONVERGENCIA[i].equals(tipoConvergencia))
                return i;
        return -1;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_linea, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case android.R.id.home:
                finish();
                return true;
            case R.id.action_delete:
                DatabaseHelper.getInstance(context).deleteLineaOferta(lineaActual);
                finish();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private class TarifasAsyncTask extends AsyncTask<String, Void, ArrayList<Tarifa>> {
        public Activity activity;

        public TarifasAsyncTask(ActivityLinea activity) {
            this.activity = activity;
        }

        @Override
        protected ArrayList<Tarifa> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(context).getAllTarifas();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        protected void onPostExecute(ArrayList<Tarifa> result) {
            if (result != null) {
                tarifas = result;
                Tarifa hint = new Tarifa();
                hint.setPlanPrecios("Seleccione aqui una tarifa...");
                hint.setTipoPlan("hint");
                tarifas.add(0,hint);
                SpinnerTarifaAdapter adapter = new SpinnerTarifaAdapter(context,
                        android.R.layout.simple_spinner_item,
                        tarifas);

                touchByUser = false;
                spTarifa.setAdapter(adapter);
                if(codigoLinea!=-1)
                    spTarifa.setSelection(tarifaSeleccionada.getCodTarifa());
                spTarifa.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        touchByUser = true;
                    }
                },200);

            }

        }
    }
    private class TerminalesAsynctask extends AsyncTask<String, Void, ArrayList<Terminal>>{
        public TerminalesAsynctask()
        {}

        @Override
        protected ArrayList<Terminal> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(context).getAllTerminalesByPlan(tarifaSeleccionada.getPlanPrecioTerminal());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute( ArrayList<Terminal> result )
        {
            if(result!=null)
            {
                terminales=result;
            }
        }
    }


}