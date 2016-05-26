package com.appcloud.frankiapp.Fragments;


import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appcloud.frankiapp.Activities.MainActivity;
import com.appcloud.frankiapp.Adapters.ClienteRecyclerViewAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Interfaces.OnClienteInteractionListener;
import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.R;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnTerminalInteractionListener}
 * interface.
 */
public class ListaClientesFragment extends Fragment {


    private OnClienteInteractionListener mListener;
    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaClientesFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cliente_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "pulsado en nuevo cliente", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

                ((MainActivity)getActivity()).switchToFragment(new ClienteFragment(),"Nuevo cliente",true);
            }
        });

        ClientesAsynctask clientesAsynctask= new ClientesAsynctask();
        clientesAsynctask.execute();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnClienteInteractionListener) {
            mListener = (OnClienteInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnTerminalInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    private class ClientesAsynctask extends AsyncTask<String, Void, ArrayList<Cliente>>
    {
        public ClientesAsynctask()
        {}

        @Override
        protected ArrayList<Cliente> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(getActivity()).getAllClientes();
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
               recyclerView.setAdapter(new ClienteRecyclerViewAdapter(result,mListener));
            }
        }
    }

}
