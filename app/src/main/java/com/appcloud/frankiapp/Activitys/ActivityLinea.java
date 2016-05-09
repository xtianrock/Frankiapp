package com.appcloud.frankiapp.Activitys;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
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
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;

import java.util.ArrayList;

public class ActivityLinea extends AppCompatActivity {

    Context context = this;
    TextView tvInicialTarifa, tvCuotaTarifa, tvInicialTerminal, tvCuotaterminal, tvDescripcion, tvTerminal;
    LinearLayout lnPrincipal, lnDescripcion, lnAlta, lnPorta, lnSimonly, lnConvergencia;
    RadioButton rbAlta, rbPorta;
    CheckBox cbSimonly, cbTerminal,cbConvergencia;
    EditText etTelefono;
    Spinner spTarifa, spOperador, spConvergencia;
    Toolbar toolbar;
    View bottomSheet;
    ArrayList<Tarifa> tarifas;
    ArrayList<Terminal> terminales;
    Tarifa tarifaSeleccionada;
    Terminal terminalSeleccionado;
    BottomSheetBehavior behavior;
    private BottomSheetDialog mBottomSheetDialog;
    TerminalesBottomSheetAdapter terminalesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea);

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
        lnConvergencia = (LinearLayout) findViewById(R.id.ln_linea_convergencia);
        etTelefono = (EditText) findViewById(R.id.et_linea_telefono);
        rbAlta = (RadioButton) findViewById(R.id.rb_linea_alta);
        rbPorta = (RadioButton) findViewById(R.id.rb_linea_porta);
        cbSimonly = (CheckBox)findViewById(R.id.cb_linea_simonly);
        cbTerminal = (CheckBox)findViewById(R.id.cb_linea_terminal);
        cbConvergencia = (CheckBox)findViewById(R.id.cb_linea_convergencia);
        spTarifa = (Spinner)findViewById(R.id.sp_linea_tarifa);
        spOperador = (Spinner) findViewById(R.id.sp_linea_operador);
        spConvergencia = (Spinner) findViewById(R.id.sp_linea_converngencia);


        // adapter con el listado de operadoras donantes
        ArrayAdapter<String> adapterOperadoras = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.array_operadores));
        spOperador.setAdapter(adapterOperadoras);


        behavior = BottomSheetBehavior.from(bottomSheet);
        behavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                // React to state change
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {
                // React to dragging events
            }
        });
        prepararOnClicks();
        prepararActionbar("Añadir linea");

        TarifasAsyncTask tarifasAsyncTask = new TarifasAsyncTask(this);
        tarifasAsyncTask.execute();

    }

    public void prepararOnClicks()
    {
        spTarifa.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                tarifaSeleccionada = tarifas.get(position);
                tvDescripcion.setText(tarifaSeleccionada.getDesCorta());
                lnPorta.setVisibility(View.GONE);
                resetResumenTarifa(); //pone a 0 el resumen de precios
                resetResumenTerminal();
                TerminalesAsynctask terminalesAsynctask = new TerminalesAsynctask();
                if(tarifaSeleccionada.getTipoPlan().equals(Tarifa.MOVIL))
                {
                    lnAlta.setVisibility(View.VISIBLE);
                    lnSimonly.setVisibility(View.VISIBLE);
                    lnConvergencia.setVisibility(View.GONE);
                    lnDescripcion.setVisibility(View.VISIBLE);
                    tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioSinTerminal()));
                    terminalesAsynctask.execute();
                }
                else if (tarifaSeleccionada.getTipoPlan().equals(Tarifa.ADSL) || tarifaSeleccionada.getTipoPlan().equals(Tarifa.FIBRA))
                {
                    lnAlta.setVisibility(View.VISIBLE);
                    lnConvergencia.setVisibility(View.VISIBLE);
                    lnSimonly.setVisibility(View.GONE);
                    lnDescripcion.setVisibility(View.GONE);
                    tvInicialTarifa.setText(String.valueOf(tarifaSeleccionada.getCosteAlta()));
                    tvCuotaTarifa.setText(String.valueOf(tarifaSeleccionada.getPrecioSinConv()));
                    terminalesAsynctask.execute();
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
                    cbSimonly.setChecked(false);
                    mostrarBottomSeet();
                }
                else
                {
                    tvTerminal.setVisibility(View.GONE);
                }
            }
        });

        cbSimonly.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    cbTerminal.setChecked(false);
            }
        });

        cbConvergencia.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                {
                    // adapter con el listado de tipos de convergencia
                    SpinnerConvergenciaAdapter adapterConvergencia = new SpinnerConvergenciaAdapter(
                            context,
                            R.layout.linea_spinner_convergencia,
                            Tarifa.TIPOSCONVERGENCIA, tarifaSeleccionada, new SpinnerConvergenciaAdapter.ConvergenciaClickListener() {
                        @Override
                        public void onItemClick(String convergencia) {
                            tvCuotaTarifa.setText(convergencia);
                        }
                    });
                    spConvergencia.setAdapter(adapterConvergencia);
                    spConvergencia.setVisibility(View.VISIBLE);
                }
                else
                {
                    spConvergencia.setVisibility(View.GONE);
                }
            }
        });

    }

    public void mostrarBottomSeet()
    {
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
                    tvTerminal.setVisibility(View.VISIBLE);

                    switch (tarifaSeleccionada.getPlanPrecioTerminal())
                    {
                        case Terminal.XS:
                            tvInicialTerminal.setText(String.valueOf(terminal.getXsInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getXsCouta()));
                            break;

                        case Terminal.MINI:
                            tvInicialTerminal.setText(String.valueOf(terminal.getMiniInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getMiniCouta()));
                            break;

                        case Terminal.S:
                            tvInicialTerminal.setText(String.valueOf(terminal.getsInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getsCouta()));
                            break;

                        case Terminal.M:
                            tvInicialTerminal.setText(String.valueOf(terminal.getmInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getmCuota()));
                            break;

                        case Terminal.L:
                            tvInicialTerminal.setText(String.valueOf(terminal.getlInicial()));
                            tvCuotaterminal.setText(String.valueOf(terminal.getlCuota()));
                            break;

                        case Terminal.XL:
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
                if (tvTerminal.getVisibility()!=View.VISIBLE)
                {
                    tvTerminal.setVisibility(View.GONE);
                    cbSimonly.setChecked(true);
                }
            }
        });
        mBottomSheetDialog.show();
    }

    public void prepararActionbar(String title) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    public void resetResumenTarifa()
    {
        tvInicialTarifa.setText("0.0");
        tvCuotaTarifa.setText("0.0");
    }
    public void resetResumenTerminal()
    {
        tvInicialTerminal.setText("0.0");
        tvCuotaterminal.setText("0.0");
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

                spTarifa.setAdapter(adapter);

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