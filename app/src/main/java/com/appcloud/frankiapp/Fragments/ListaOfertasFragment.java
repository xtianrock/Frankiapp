package com.appcloud.frankiapp.Fragments;


import android.Manifest;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.appcloud.frankiapp.Activities.ActivityOferta;
import com.appcloud.frankiapp.Adapters.OfertasRecyclerViewAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Commons;
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


    private static final int REQUEST_CALL_PHONE = 1;
    private static final int REQUEST_CONTACTS = 2;
    Context context;
    RecyclerView recyclerView;
    List<Oferta> ofertas;
    OfertaAsyncTask ofertaAsyncTask;
    OfertasRecyclerViewAdapter.OfertaClickListener listener;
    OfertasRecyclerViewAdapter.OfertaMemenuClickListener tblistener;
    String tab;
    FloatingActionButton fab;
    Cliente clienteMenuItem;
    MenuItem itemLlamar, itemEmail;
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
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        recyclerView = (RecyclerView)view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        tblistener = new OfertasRecyclerViewAdapter.OfertaMemenuClickListener() {
            @Override
            public void onMenuItemClick(MenuItem menuItem, Oferta oferta) {
                clienteMenuItem = DatabaseHelper.getInstance(getActivity()).getCliente(oferta.getCodCliente());

                switch (menuItem.getItemId()) {
                    case R.id.action_llamar_cliente:
                        if (ContextCompat.checkSelfPermission(getActivity(),
                                Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            requestPhonePermission();
                        }
                        else
                        {
                            llamarCliente(clienteMenuItem);
                        }

                        break;
                    case R.id.action_email_cliente:
                        enviarMail();
                        break;
                    case R.id.action_whatsapp_cliente:

                        int readContacts = ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_CONTACTS);
                        int writeContacts = ActivityCompat.checkSelfPermission(context, Manifest.permission.WRITE_CONTACTS);
                        if (writeContacts!=PackageManager.PERMISSION_GRANTED ||
                                readContacts!=PackageManager.PERMISSION_GRANTED) {
                            requestContactsPermission();
                        }

                        else
                        {
                            openWhatsappChat(clienteMenuItem);
                        }
                        break;
                }
            }
        };
        listener = new OfertasRecyclerViewAdapter.OfertaClickListener() {
            @Override
            public void onItemClick(View vista, final Oferta oferta, Toolbar tb) {

                Intent intent = new Intent(context, ActivityOferta.class);
                intent.putExtra("oferta",oferta.getCodOferta());
                LinearLayout lnColor = (LinearLayout)vista.findViewById(R.id.ln_color);
                TextView tvNombre = (TextView)vista.findViewById(R.id.tv_oferta_nombre);
                Pair<View, String> p1 = Pair.create((View)lnColor, "color_toolbar");
                Pair<View, String> p2 = Pair.create((View)tvNombre, "titulo_toolbar");
                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation(getActivity(), p1, p2);
                startActivity(intent,options.toBundle());

            }


        };
        //fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityOferta.class);
                startActivity(intent);
            }
        });

        return view;
    }


    private void enviarMail() {
        if (clienteMenuItem.getEmail() != null && !clienteMenuItem.getEmail().equals("")) {
            Intent emailIntent;
            emailIntent = new Intent(Intent.ACTION_SENDTO);
            emailIntent.setData(Uri.parse("mailto:"+clienteMenuItem.getEmail()));

            if (emailIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                startActivity(emailIntent);
            } else {
                Snackbar.make(fab, "No se ha encontrado ningún cliente de email en este dispositivo",
                        Snackbar.LENGTH_LONG)
                        .setAction(null,null).show();
            }

        }else {
            Snackbar.make(fab, "Este cliente no tiene ningún email registrado, introduzca uno",
                    Snackbar.LENGTH_LONG)
                    .setAction("Action",null).show();
        }
    }

    private void llamarCliente(Cliente cliente) {
        if (cliente.getTelefono() != null) {
            Intent intentLlamada = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + cliente.getTelefono()));
            startActivity(intentLlamada);
        }else {
            Snackbar.make(fab, "Este cliente no tiene un teléfono registrado, introduzca uno",
                    Snackbar.LENGTH_LONG)
                    .show();
        }
    }

    private void openWhatsappContactPicker() {
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.setFlags(0x14000000);
        i.setComponent(new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker"));
        startActivity(i);
    }

    public void openWhatsappChat(Cliente cliente)   {
        String whatsappid = "34"+cliente.getTelefono()+"@s.whatsapp.net";
        Intent whatsapp;
        Cursor c = getActivity().getContentResolver().query(ContactsContract.Data.CONTENT_URI,
                new String[] { ContactsContract.Contacts.Data._ID }, ContactsContract.Data.DATA1 + "=?",
                new String[] { whatsappid }, null);
        c.moveToFirst();
        String id;
        if(c.getCount()==0)
        {
            id = Commons.crearContacto(getActivity(),cliente);
        }
        else
        {
            id = c.getString(0);
        }
        whatsapp = new Intent(Intent.ACTION_VIEW, Uri.parse("content://com.android.contacts/data/" +id ));
        c.close();

        if(id!=null)
        {
            startActivity(whatsapp);
        }
        else
        {
            Toast.makeText(context, "El contacto " + cliente.getNombre() + " " + cliente.getApellidos() + "aún no aparece en los contactos de whatsapp," +
                    "seleccione la opcion actualizar y busquelo maualmente.", Toast.LENGTH_LONG).show();
            openWhatsappContactPicker();
        }



    }

    private void requestPhonePermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CALL_PHONE)) {

            Snackbar.make(fab, "Este permiso es necesario para realizar llamadas",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPermissions(
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    REQUEST_CALL_PHONE);
                        }
                    })
                    .show();
        } else {

            // CALL Phone permission has not been granted yet. Request it directly.
            requestPermissions( new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
        }
    }

    private void requestContactsPermission() {

        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.WRITE_CONTACTS)) {

            Snackbar.make(fab, "La app necesita acceder a los contactos para poder abrir el chat de Whatsapp",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            requestPermissions(
                                    new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},
                                    REQUEST_CONTACTS);
                        }
                    })
                    .show();
        } else {
            requestPermissions(
                    new String[]{Manifest.permission.READ_CONTACTS,Manifest.permission.WRITE_CONTACTS},
                    REQUEST_CONTACTS);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_CALL_PHONE:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    llamarCliente(clienteMenuItem);

                }
                break;
            case REQUEST_CONTACTS:
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    openWhatsappChat(clienteMenuItem);

                }
                break;
        }
    }


    private void requestPhonePermissionAntiguo() {

        // BEGIN_INCLUDE(camera_permission_request)
        if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                Manifest.permission.CALL_PHONE)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // For example if the user has previously denied the permission.

            Snackbar.make(fab, "Este permiso es necesario para realizar llamadas",
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("OK", new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            ActivityCompat.requestPermissions(getActivity(),
                                    new String[]{Manifest.permission.CALL_PHONE},
                                    REQUEST_CALL_PHONE);
                        }
                    })
                    .show();
        } else {

            // CALL Phone permission has not been granted yet. Request it directly.
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.CALL_PHONE},
                    REQUEST_CALL_PHONE);
        }
        // END_INCLUDE(camera_permission_request)
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
                recyclerView.setAdapter(new OfertasRecyclerViewAdapter(context,ofertas,listener,tblistener));
            }
        }
    }

}
