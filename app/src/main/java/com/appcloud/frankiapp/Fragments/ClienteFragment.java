package com.appcloud.frankiapp.Fragments;


import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.appcloud.frankiapp.Activitys.MainActivity;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.R;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnTerminalInteractionListener}
 * interface.
 */
public class ClienteFragment extends Fragment {

    Context context;
    EditText etNombre,etApellidos, etnif, etTelefono, etMail, etDireccion, etCpoostal;
    Spinner spinnerProvincia, spinnerPoblacion;
    FloatingActionButton fab;
    Button btGuardar;
    String[] provincias, poblaciones;
    Cliente cliente;
    int codCliente = -1;
    DatabaseHelper db;
    String transtionName;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ClienteFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null)
        {
            codCliente = args.getInt("cliente",-1);
        }
        provincias = getResources().getStringArray(R.array.provincias);
        db = DatabaseHelper.getInstance(getActivity());

        try {
            if(codCliente!=-1)
            {
                cliente = db.getCliente(codCliente);
            }
            else
            {
                cliente = new Cliente();
            }
        }
        catch (Exception e)
        {
            cliente = new Cliente();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cliente, container, false);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line, provincias);
        etNombre = (EditText)view.findViewById(R.id.et_cliente_nombre);
        etApellidos = (EditText)view.findViewById(R.id.et_cliente_apellidos);
        etnif = (EditText)view.findViewById(R.id.et_cliente_cif);
        etTelefono = (EditText)view.findViewById(R.id.et_cliente_telefono);
        etMail = (EditText)view.findViewById(R.id.et_cliente_mail);
        etDireccion = (EditText) view.findViewById(R.id.et_cliente_direccion);
        etCpoostal = (EditText)view.findViewById(R.id.et_cliente_cpostal);
        spinnerProvincia = (Spinner)view.findViewById(R.id.sp_provincia);
        spinnerPoblacion = (Spinner)view.findViewById(R.id.sp_poblacion);

        spinnerProvincia.setAdapter(adapter);
        spinnerProvincia.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                int positionInArray = getPositionByString(provincias,spinnerProvincia.getSelectedItem().toString());
                if(positionInArray!=-1)
                {
                    poblaciones =getArrayPoblaciones();
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(),android.R.layout.simple_dropdown_item_1line, poblaciones);
                    spinnerPoblacion.setAdapter(adapter);
                    spinnerPoblacion.setEnabled(true);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        if(codCliente!=-1)
        {
            //Inicializar valores cliente
            etNombre.setText(cliente.getNombre());
            etApellidos.setText(cliente.getApellidos());
            etnif.setText(cliente.getNif());
            etTelefono.setText(cliente.getTelefono());
            etMail.setText(cliente.getEmail());
            etDireccion.setText(cliente.getDireccion());
            etCpoostal.setText(cliente.getCpostal());
            spinnerProvincia.setSelection(getPositionByString(provincias,cliente.getProvincia()));
            int positionPoblacion = getPositionByString(getArrayPoblaciones(),cliente.getPoblacion());
            spinnerPoblacion.setSelection(positionPoblacion);
        }
        else
        {
            spinnerProvincia.setSelection(21);// Huelva por defecto
        }


        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Cliente guardado con exito", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                cliente.setNombre(etNombre.getText().toString());
                cliente.setApellidos(etApellidos.getText().toString());
                cliente.setNif(etnif.getText().toString());
                cliente.setTelefono(etTelefono.getText().toString());
                cliente.setEmail(etMail.getText().toString());
                cliente.setDireccion(etDireccion.getText().toString());
                cliente.setCpostal(etCpoostal.getText().toString());
                cliente.setProvincia(spinnerProvincia.getSelectedItem().toString());
                cliente.setPoblacion(spinnerPoblacion.getSelectedItem().toString());
                if(codCliente!=-1)
                {
                    DatabaseHelper.getInstance(getActivity()).updateCliente(cliente);
                }
                else
                {
                    DatabaseHelper.getInstance(getActivity()).createCliente(cliente);
                }

                ((MainActivity)context).getSupportFragmentManager().popBackStack();
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            etNombre.setTransitionName(transtionName);
        }
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        getActivity().setTitle("Clientes");

    }

    @Override
    public void onStop() {
        super.onStop();
        fab.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
    }




    private int getPositionByString(String[] array,String nombre)
    {
        int position=-1;
        for (int i=0; i<array.length;i++) {
            if(array[i].equals(nombre))
            {
                position=i;
                break;
            }
        }
        return position;
    }

    private String[] getArrayPoblaciones ()
    {
        String[] poblaciones = null;
        String[] arrayLocalidadesProvincia = getResources().getStringArray(R.array.array_provincia_a_localidades);
        int positionInArray = getPositionByString(provincias,spinnerProvincia.getSelectedItem().toString());
        if(positionInArray!=-1)
        {
            int idResource = getResources().getIdentifier(arrayLocalidadesProvincia[positionInArray],"string",getActivity().getPackageName());
            poblaciones = getResources().getStringArray(idResource);

        }
        return poblaciones;
    }

}
