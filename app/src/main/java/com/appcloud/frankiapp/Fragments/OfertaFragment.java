package com.appcloud.frankiapp.Fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnTerminalInteractionListener}
 * interface.
 */
public class OfertaFragment extends Fragment {

    Context context;
    FloatingActionButton fab;
    DatabaseHelper db;
    int codOferta;
    Oferta oferta;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public OfertaFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle args = getArguments();
        if (args != null)
        {
            codOferta = args.getInt("oferta",-1);
        }
        db = DatabaseHelper.getInstance(getActivity());

        try {
            if(codOferta!=-1)
            {
              //  oferta = db.getOferta(codOferta);
            }
            else
            {
                oferta = new Oferta();
            }
        }
        catch (Exception e)
        {
            oferta = new Oferta();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oferta, container, false);

        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_save_black_24dp));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "pulsado en guardar oferta", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context=context;
        getActivity().setTitle("Oferta");

    }

    @Override
    public void onDetach() {
        super.onDetach();
        fab.setImageDrawable(context.getResources().getDrawable(R.drawable.ic_add));
    }




}
