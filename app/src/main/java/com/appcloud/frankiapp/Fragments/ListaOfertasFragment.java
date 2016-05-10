package com.appcloud.frankiapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appcloud.frankiapp.Activitys.ActivityOferta;
import com.appcloud.frankiapp.Adapters.ClienteRecyclerViewAdapter;
import com.appcloud.frankiapp.Adapters.OfertasRecyclerViewAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnTerminalInteractionListener}
 * interface.
 */
public class ListaOfertasFragment extends Fragment {

    Context context;
    RecyclerView recyclerView;
    List<Oferta> ofertas;
    OfertasRecyclerViewAdapter.OfertaClickListener listener;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaOfertasFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oferta_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listener = new OfertasRecyclerViewAdapter.OfertaClickListener() {
            @Override
            public void onItemClick(Oferta oferta) {
                goToActivityOferta(oferta.getCodOferta());
            }
        };
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToActivityOferta(-1); //nueva oferta
            }
        });

        OfertaAsyncTask ofertaAsyncTask= new OfertaAsyncTask();
        ofertaAsyncTask.execute();
        return view;
    }

    public void goToActivityOferta(int codOferta)
    {
        Intent intent = new Intent(context, ActivityOferta.class);
        intent.putExtra("oferta",codOferta);
        startActivity(intent);
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    private class OfertaAsyncTask extends AsyncTask<String, Void, ArrayList<Oferta>>
    {
        public OfertaAsyncTask()
        {}

        @Override
        protected ArrayList<Oferta> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(getActivity()).getAllOfertas();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute( ArrayList<Oferta> result )
        {
            if(result!=null)
            {
              ofertas=result;
                recyclerView.setAdapter(new OfertasRecyclerViewAdapter(ofertas,listener));
            }
        }
    }

}
