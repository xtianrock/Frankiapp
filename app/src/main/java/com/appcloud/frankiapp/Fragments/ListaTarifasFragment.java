package com.appcloud.frankiapp.Fragments;

/**
 * Created by cristian on 29/04/2016.
 */
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appcloud.frankiapp.Adapters.TarifaRecyclerAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Interfaces.OnTarifaInteractionListener;
import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.R;

import java.util.ArrayList;

/**
 * A fragment representing a list of Items.
 * <p>
 * Activities containing this fragment MUST implement the {@link OnTerminalInteractionListener}
 * interface.
 */
public class ListaTarifasFragment extends Fragment {

    // TODO: Customize parameter argument names
    private static final String ARG_COLUMN_COUNT = "column-count";
    // TODO: Customize parameters
    private int mColumnCount = 1;
    private OnTarifaInteractionListener mListener;
    RecyclerView recyclerView;
    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ListaTarifasFragment() {
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mColumnCount = getArguments().getInt(ARG_COLUMN_COUNT);
        }

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_terminal_list, container, false);
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        TarifasAsyncTask tarifasAsyncTask = new TarifasAsyncTask();
        tarifasAsyncTask.execute();
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnTarifaInteractionListener) {
            mListener = (OnTarifaInteractionListener) context;
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

    private class TarifasAsyncTask extends AsyncTask<String, Void, ArrayList<Tarifa>>
    {
        public TarifasAsyncTask()
        {}

        @Override
        protected ArrayList<Tarifa> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(getActivity()).getAllTarifas();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute( ArrayList<Tarifa> result )
        {
            if(result!=null)
            {
                recyclerView.setAdapter(new TarifaRecyclerAdapter(result,mListener));
            }
        }
    }

}
