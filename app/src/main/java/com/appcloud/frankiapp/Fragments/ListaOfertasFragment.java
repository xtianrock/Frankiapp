package com.appcloud.frankiapp.Fragments;


import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appcloud.frankiapp.Activitys.ActivityOferta;
import com.appcloud.frankiapp.Adapters.ClienteRecyclerViewAdapter;
import com.appcloud.frankiapp.Adapters.OfertasRecyclerViewAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Configuration;

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
    OfertaAsyncTask ofertaAsyncTask;
    OfertasRecyclerViewAdapter.OfertaClickListener listener;
    String tab;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaOfertasFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tab= getArguments().getString(Configuration.TAB);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_oferta_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        listener = new OfertasRecyclerViewAdapter.OfertaClickListener() {
            @Override
            public void onItemClick(View vista, Oferta oferta) {
                Intent intent = new Intent(context, ActivityOferta.class);
                intent.putExtra("oferta",oferta.getCodOferta());
                LinearLayout lnColor = (LinearLayout)vista.findViewById(R.id.ln_color);
                TextView tvNombre = (TextView)vista.findViewById(R.id.tv_oferta_nombre);
                Pair<View, String> p1 = Pair.create((View)lnColor, "color");
                Pair<View, String> p2 = Pair.create((View)tvNombre, "nombre");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), p1, p2);
                startActivity(intent,options.toBundle());
            }


        };
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityOferta.class);
                startActivity(intent);
            }
        });

        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        ofertaAsyncTask= new OfertaAsyncTask();
        ofertaAsyncTask.execute();
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
                ArrayList<Oferta> ofertas = new ArrayList<>();
                switch (tab)
                {
                    case Configuration.BORRADOR:
                        ofertas = DatabaseHelper.getInstance(getActivity()).getAllOfertas(Configuration.BORRADOR);
                        break;
                    case Configuration.PRESENTADA:
                        ofertas = DatabaseHelper.getInstance(getActivity()).getAllOfertas(Configuration.PRESENTADA);
                        break;
                    case Configuration.FIRMADA:
                        ofertas = DatabaseHelper.getInstance(getActivity()).getAllOfertas(Configuration.FIRMADA);
                        break;
                }
                return ofertas;
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
