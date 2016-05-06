package com.appcloud.frankiapp.Activitys;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;

import com.appcloud.frankiapp.Adapters.TarifasAutocompletadoAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.R;

import java.util.ArrayList;

public class ActivityLinea extends AppCompatActivity {

    Context context = this;
    AutoCompleteTextView autocompletado;
    TextView tvPlan, tvDescripcion;
    LinearLayout lnPrincipal, lnPorta;
    RadioButton rbAlta, rbPorta;
    EditText etTelefono;
    Spinner spOperador;
    Toolbar toolbar;
    TextWatcher twSearch;
    ArrayList<Tarifa> tarifas;
    Tarifa tarifaSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_linea);

        autocompletado = (AutoCompleteTextView) findViewById(R.id.autocompletado);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        tvPlan = (TextView)findViewById(R.id.tv_linea_plan);
        tvDescripcion = (TextView)findViewById(R.id.tv_linea_desc);
        lnPrincipal = (LinearLayout) findViewById(R.id.ln_linea_prnicipal);
        lnPorta = (LinearLayout) findViewById(R.id.ln_linea_porta);
        etTelefono = (EditText) findViewById(R.id.et_linea_telefono);
        rbAlta = (RadioButton) findViewById(R.id.rb_linea_alta);
        rbPorta = (RadioButton) findViewById(R.id.rb_linea_porta);
        spOperador = (Spinner) findViewById(R.id.sp_linea_operador);

        // adapter con el listado de operadoras donantes
        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                this,
                android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.array_operadores));
        spOperador.setAdapter(adapter);

        prepararOnClicks();
        prepararActionbar("Añadir linea");
        prepararAutocompletado();
        TarifasAsyncTask tarifasAsyncTask = new TarifasAsyncTask(this);
        tarifasAsyncTask.execute();
    }

    public void prepararOnClicks()
    {
        rbPorta.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked)
                    lnPorta.setVisibility(View.VISIBLE);
                else
                    lnPorta.setVisibility(View.GONE);
            }
        });
    }


    public void prepararActionbar(String title) {
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(title);
    }

    public void prepararAutocompletado() {
        autocompletado.setThreshold(1);
        autocompletado.setDropDownAnchor(R.id.autocompletado);

        autocompletado.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                for (Tarifa tarifa:tarifas)
                {
                    if(String.valueOf(tarifa.getCodTarifa()).equals(autocompletado.getText().toString()))
                    {
                        lnPrincipal.setVisibility(View.VISIBLE);
                        autocompletado.setText("");
                        tarifaSeleccionada=tarifa;
                        tvPlan.setText(tarifaSeleccionada.getPlanPrecios());
                        tvDescripcion.setText(tarifaSeleccionada.getDesCorta());
                        break;
                    }
                }
            }
        });
        twSearch = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!autocompletado.getText().toString().equals("")) {
                    autocompletado.setCompoundDrawablesWithIntrinsicBounds(0, 0, android.support.design.R.drawable.abc_ic_clear_mtrl_alpha, 0);
                } else
                    autocompletado.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0, 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        autocompletado.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                final int DRAWABLE_RIGHT = 2;

                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (autocompletado.getCompoundDrawables()[DRAWABLE_RIGHT] != null && event.getRawX() >= (autocompletado.getRight() - autocompletado.getCompoundDrawables()[DRAWABLE_RIGHT].getBounds().width())) {
                        autocompletado.getText().clear();
                        // tvDescrip.setText("");
                        // etCantidad.getText().clear();
                        autocompletado.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_search_black_24dp, 0, 0, 0);
                        InputMethodManager mgr = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);

                        autocompletado.requestFocus();
                        mgr.showSoftInput(autocompletado, InputMethodManager.SHOW_IMPLICIT);
                        return true;
                    }
                }

                return false;
            }
        });
        autocompletado.addTextChangedListener(twSearch);
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
                TarifasAutocompletadoAdapter tarifasAutocompletadoAdapter = new TarifasAutocompletadoAdapter(context, autocompletado.getId(), tarifas);
                autocompletado.setAdapter(tarifasAutocompletadoAdapter);

            }
        }
    }
}