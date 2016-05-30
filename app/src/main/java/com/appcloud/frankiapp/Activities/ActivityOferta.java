package com.appcloud.frankiapp.Activities;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.util.Pair;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.AppCompatDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.appcloud.frankiapp.Adapters.ClientesBottomSheetAdapter;
import com.appcloud.frankiapp.Adapters.LineasRecyclerViewAdapter;
import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Fragments.ClienteFragment;
import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.POJO.Lineaoferta;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Commons;
import com.appcloud.frankiapp.Utils.Configuration;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class ActivityOferta extends AppCompatActivity {

    Context context = this;
    LinearLayout lnResumen, lnPdfTerminal;
    RelativeLayout rlComisionColor;
    FloatingActionButton fab;
    Button altaCliente;
    TextView tvTitulo, tvInicialTarifa, tvCuotaTarifa, tvInicialTerminal, tvCuotaterminal, tvComision, tvExtraComision, tvPuntos;
    RecyclerView recyclerView;
    LineasRecyclerViewAdapter.LineaClickListener lineaClickListener;
    BottomSheetBehavior behavior;
    MenuItem itemFirmar, itemPresentar, itemThumbok, itemThumbko, itemActionEdit, itemActionDelete;
    private BottomSheetDialog mBottomSheetDialog;
    ClientesBottomSheetAdapter clientesBSheetAdapter;
    View bottomSheet;
    ArrayList<Cliente> clientes;
    Cliente clienteSeleccionado;
    Oferta oferta;
    public int codigoOferta = -1;
    boolean firstTime = true;
    boolean edicion = false;
    String currentFragmentTag;
    ArrayList<Lineaoferta> lineasOferta;

    ImageView pdfPorta, pdfSimOnly;
    LinearLayout lnPortaTlf, lnPdfPorta, lnPdfTotalOferta;
    TextView pdfPlan, planDescripcion, numTelefono, numTelefonoLabel, pdfFechaOferta, pdfTerminal,
            pdfNombreCliente, pdfOfertaId, pdfTerminalPagoInicial, pdfTerminalCuota, pdfSubtotalLinea, getPdfSubtotalPlan, pdfTotal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inicializarOferta();
        setTheme(Commons.getTema(oferta.getEstado()));
        setContentView(R.layout.activity_oferta);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        bottomSheet = findViewById(R.id.ln_bottomSheet_clientes);
        lnResumen = (LinearLayout) findViewById(R.id.ln_resumen);
        fab = (FloatingActionButton) findViewById(R.id.fab);
        recyclerView = (RecyclerView) findViewById(R.id.list);
        tvTitulo = (TextView) findViewById(R.id.tv_toolbar_titulo);
        tvInicialTarifa = (TextView) findViewById(R.id.tv_inicial_tarifa);
        tvCuotaTarifa = (TextView) findViewById(R.id.tv_cuota_tarifa);
        tvInicialTerminal = (TextView) findViewById(R.id.tv_inicial_terminal);
        tvCuotaterminal = (TextView) findViewById(R.id.tv_cuota_terminal);


        behavior = BottomSheetBehavior.from(bottomSheet);


        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        lineaClickListener = new LineasRecyclerViewAdapter.LineaClickListener() {
            @Override
            public void onItemClick(View vista, Lineaoferta linea) {
                Intent intent = new Intent(context, ActivityLinea.class);
                intent.putExtra("oferta", codigoOferta);
                intent.putExtra("estado", oferta.getEstado());
                intent.putExtra("lineaActual", linea.getCodLinea());
                //  startActivity(intent);

                LinearLayout lnColor = (LinearLayout) vista.findViewById(R.id.ln_color);
                TextView tvLinea = (TextView) vista.findViewById(R.id.tv_linea_tarifa);

                Pair<View, String> p1 = Pair.create((View) lnColor, "color");
                Pair<View, String> p2 = Pair.create((View) tvLinea, "nombre");

                ActivityOptionsCompat options = ActivityOptionsCompat.
                        makeSceneTransitionAnimation((Activity) context, p1, p2);
                startActivity(intent, options.toBundle());
            }
        };

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ActivityLinea.class);
                intent.putExtra("oferta", codigoOferta);
                startActivity(intent);
            }
        });

        if (!oferta.getEstado().equalsIgnoreCase("borrador"))
            fab.setVisibility(View.GONE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        inicializarOferta();
        setActionBar();

        if (!firstTime)
            inicializarOferta();
        firstTime = false;
    }

    // nuevo cliente dado de alta en el bottomsheet
    public void informacionFragmentCliente(Cliente nuevoCliente) {
        clienteAsignadoBorrador(nuevoCliente);
    }

    private void actualizaEstadoOferta(boolean enviar) {

        Intent intent = new Intent(context, ActivityOferta.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        intent.putExtra("oferta", ((ActivityOferta) context).codigoOferta);
        if (enviar)
            intent.putExtra("enviar",true);

        startActivity(intent);
        overridePendingTransition(0, 0); //0 for no animation
        finish();
    }

    public void mostrarBottomSeet() {
        if (behavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }


        mBottomSheetDialog = new BottomSheetDialog(context);
        mBottomSheetDialog.setCancelable(false);
        View view = getLayoutInflater().inflate(R.layout.bottom_sheet_clientes, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycler_Sheet_Clientes);
        recyclerView.setHasFixedSize(true);
        clientesBSheetAdapter = new ClientesBottomSheetAdapter(clientes, new ClientesBottomSheetAdapter.ClienteBSheetClickListener() {
            @Override
            public void onItemClick(Cliente cliente) {
                if (mBottomSheetDialog != null) {
                    mBottomSheetDialog.dismiss();
                    clienteAsignadoBorrador(cliente);
                    actualizaEstadoOferta(true);

                    tvTitulo.setText(clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos());

                }
            }
        });
        recyclerView.setAdapter(clientesBSheetAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                mBottomSheetDialog = null;
            }
        });

        altaCliente = (Button) view.findViewById(R.id.btnsheet_alta_cliente);

        altaCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switchToFragment(new ClienteFragment(), "alta_cliente", false);
                mBottomSheetDialog.dismiss();

            }
        });
        mBottomSheetDialog.show();

    }

    private void clienteAsignadoBorrador(Cliente cliente) {

        clienteSeleccionado = cliente;
        oferta.setEstado(Configuration.PRESENTADA);
        oferta.setCodCliente(cliente.getCodCliente());
        oferta.setNombre(clienteSeleccionado.getNombre());
        oferta.setApellidos(clienteSeleccionado.getApellidos());
        oferta.setPoblacion(clienteSeleccionado.getPoblacion());

        DatabaseHelper.getInstance(context).updateCabeceraOferta(oferta);

        //DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
        /*if (enviar)
            enviaOferta();*/

        tvTitulo.setText(clienteSeleccionado.getNombre() + " " + clienteSeleccionado.getApellidos());
    }

    public void enviaOferta() {

        Uri contentUri = null;
        View vistaPDF = LayoutInflater.from(getBaseContext()).inflate(R.layout.oferta_pdf, null);


        vistaPDF.layout(0, 0, 1240, 1754);

        pdfNombreCliente = (TextView) vistaPDF.findViewById(R.id.pdf_nombreCliente);
        pdfFechaOferta = (TextView) vistaPDF.findViewById(R.id.pdf_fechaOferta);
        pdfOfertaId = (TextView) vistaPDF.findViewById(R.id.pdf_ofertaId);
        DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
        Date fec = new Date(oferta.getFechaOferta());
        pdfNombreCliente.setText(oferta.getNombre() + " " + oferta.getApellidos());
        pdfFechaOferta.setText(df.format(fec));
        pdfOfertaId.setText(String.valueOf(oferta.getCodOferta()));

        PdfDocument document = null;
        float precioTotal = 0;
        int contador = 0;
        int pagina = 1;
        int lineasPosibles = 6;

        Cliente cliente = DatabaseHelper.getInstance(context).getCliente(oferta.getCodCliente());

        for (int i = 0; i < lineasOferta.size(); i++) {

            if (lineasOferta.get(i).getCodTerminal() == 0)
                contador++;
            else
                contador = contador + 2;

            precioTotal = precioTotal + insertaLineaOfertaPDF(vistaPDF, lineasOferta.get(i));
            if (i == lineasOferta.size() - 1)
                insertaPieOfertaPDF(vistaPDF, precioTotal);

            if (contador > lineasPosibles) {
                Commons.getViewBitmap(vistaPDF, 1240, 1754);
                document = Commons.createPDFDocument(vistaPDF, context, document, pagina);
                lineasPosibles = 6;
                pagina++;
                contador = 0;
                vistaPDF = LayoutInflater.from(getBaseContext()).inflate(R.layout.oferta_pdf, null);
                vistaPDF.findViewById(R.id.pdf_cabecera).setVisibility(View.GONE);

                vistaPDF.layout(0, 0, 1240, 1754);
            }

            if (i == lineasOferta.size() - 1 && document == null) {
                Commons.getViewBitmap(vistaPDF, 1240, 1754);
                document = Commons.createPDFDocument(vistaPDF, context, document, pagina);
            }
        }

        Commons.savePDFDocument(document, context);

        File imagePath = new File(getApplicationContext().getCacheDir(), "images");
        // File newFile = new File(imagePath, "image.png");
        File newFile = new File(imagePath, "documento.pdf");
        contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.appcloud.frankiapp", newFile);


        // Bitmap result = Commons.getViewBitmap(vistaPDF, 1240, 1754);


        /*if (Commons.createPDFDocument(vistaPDF, context, pagina)) {

            File imagePath = new File(getApplicationContext().getCacheDir(), "images");
            // File newFile = new File(imagePath, "image.png");
            File newFile = new File(imagePath, "documento.pdf");
            contentUri = FileProvider.getUriForFile(getApplicationContext(), "com.appcloud.frankiapp", newFile);
        } else {
            //TODO ENVIAR BITMAP
        }*/

        if (contentUri != null) {

            Intent shareIntent = new Intent();
            shareIntent.setAction(Intent.ACTION_SEND);
            shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); // temp permission for receiving app to read this file
            //shareIntent.setDataAndType(contentUri, getContentResolver().getType(contentUri));

            //shareIntent.setData(Uri.parse("mailto:arteaga.dev@gmail.com"));
            shareIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{cliente.getEmail()});
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "FrankiAPP - oferta " + oferta.getCodOferta());
            shareIntent.setType(getContentResolver().getType(contentUri));
            shareIntent.putExtra(Intent.EXTRA_STREAM, contentUri);
            startActivity(Intent.createChooser(shareIntent, "Elige una aplicación"));

        }

        //clienteAsignadoBorrador(cliente);
        //actualizaEstadoOferta();
    }

    private float insertaLineaOfertaPDF(View vista, Lineaoferta linea) {

        View lineaOfertaPDF = LayoutInflater.from(getBaseContext()).inflate(R.layout.oferta_linea_pdf, null);
        LinearLayout contenido = (LinearLayout) vista.findViewById(R.id.linea_oferta);
        Terminal terminal;
        Tarifa tarifa;

        lnPortaTlf = (LinearLayout) lineaOfertaPDF.findViewById(R.id.ln_pdf_portabilidadTlf);
        lnPdfPorta = (LinearLayout) lineaOfertaPDF.findViewById(R.id.ln_portabilidad);
        lnPdfTerminal = (LinearLayout) lineaOfertaPDF.findViewById(R.id.ln_pdfTerminal);

        pdfSimOnly = (ImageView) lineaOfertaPDF.findViewById(R.id.pdf_sim_only);
        pdfPorta = (ImageView) lineaOfertaPDF.findViewById(R.id.pdf_portabilidad);
        pdfPlan = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_titulo_plan);
        planDescripcion = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_descripcion_plan);
        numTelefono = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_num_telefono);
        numTelefonoLabel = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_num_telefono_label);
        pdfTerminal = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_terminal);
        pdfTerminalPagoInicial = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_terminal_pinicial);
        pdfTerminalCuota = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_terminal_cuota);
        getPdfSubtotalPlan = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_subtotal_plan);
        pdfSubtotalLinea = (TextView) lineaOfertaPDF.findViewById(R.id.pdf_subtotal_linea);

        tarifa = DatabaseHelper.getInstance(context).getTarifaByCod(linea.getCodTarifa());


        pdfPlan.setText(tarifa.getPlanPrecios());
        planDescripcion.setText(tarifa.getDesCorta());
        //planDescripcion.setText(linea.get);
        if (linea.getNumeroTelefono() == null) {
            numTelefonoLabel.setVisibility(View.GONE);
            numTelefono.setVisibility(View.GONE);
            lnPdfPorta.setVisibility(View.GONE);
            lnPortaTlf.setVisibility(View.GONE);
        } else {
            numTelefono.setText(linea.getNumeroTelefono());
        }

        if (linea.getCodTerminal() != 0) {
            terminal = DatabaseHelper.getInstance(context).getTerminalByCod(linea.getCodTerminal());
            pdfTerminal.setText(terminal.getDescripcion());
            pdfTerminalPagoInicial.setText(String.format("%.2f", linea.getPrecioTerminalInicial()) + " €");
            pdfTerminalCuota.setText(String.format("%.2f", linea.getPrecioTErminalCuota()) + " €");
        } else {
            lnPdfTerminal.setVisibility(View.GONE);
        }

        getPdfSubtotalPlan.setText(String.valueOf(linea.getPrecioTarifaCuota()) + " €");
        pdfSubtotalLinea.setText(String.format("%.2f", linea.getPrecioTErminalCuota() + linea.getPrecioTarifaCuota()) + " €");

        contenido.addView(lineaOfertaPDF);

        int height = lineaOfertaPDF.getMeasuredHeight();

        vista.layout(0, 20, 1240, 1754);

        return linea.getPrecioTarifaCuota() + linea.getPrecioTErminalCuota();

    }

    private void insertaPieOfertaPDF(View vista, float cuotaTotal) {
        View piePDF = LayoutInflater.from(getBaseContext()).inflate(R.layout.pie_oferta_pdf, null);
        LinearLayout contenido = (LinearLayout) vista.findViewById(R.id.linea_oferta);

        lnPdfTotalOferta = (LinearLayout) piePDF.findViewById(R.id.ln_pdf_pie_totalprecio);
        pdfTotal = (TextView) piePDF.findViewById(R.id.pdfTotal);

        pdfTotal.setText(String.format("%.2f", cuotaTotal) + " €");

        contenido.addView(piePDF);
        vista.layout(0, 20, 1240, 1754);
    }


    private void setActionBar() {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowHomeEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);

        switch (oferta.getEstado()) {
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
        if (codigoOferta != -1) {
            if (oferta.getCodCliente() != 0) {
                Cliente cliente = DatabaseHelper.getInstance(context).getCliente(oferta.getCodCliente());
                tvTitulo.setText(cliente.getNombre() + " " + cliente.getApellidos());
            } else

                tvTitulo.setText("Borrador #" + codigoOferta);
        } else {
            oferta = new Oferta();
            codigoOferta = (int) DatabaseHelper.getInstance(this).createCabecceraOferta(oferta);
            tvTitulo.setText("Nueva Oferta");

        }
        tvInicialTarifa.setText(String.valueOf(oferta.getPagoInicialTarifa()));
        tvCuotaTarifa.setText(String.valueOf(oferta.getCuotaTarifa()));
        tvInicialTerminal.setText(String.valueOf(oferta.getPagoInicialTerminal()));
        tvCuotaterminal.setText(String.valueOf(oferta.getCuotaTerminal()));

    }

    private void inicializarOferta() {
        if (codigoOferta == -1)
            codigoOferta = getIntent().getIntExtra("oferta", -1);

        if (codigoOferta != -1) {
            oferta = DatabaseHelper.getInstance(context).getOferta(codigoOferta);
        } else {
            oferta = new Oferta();
            oferta.setFechaOferta((System.currentTimeMillis() / 1000L));
            codigoOferta = (int) DatabaseHelper.getInstance(this).createCabecceraOferta(oferta);
        }

        LineasAsynctask lineasAsynctask = new LineasAsynctask(codigoOferta);
        lineasAsynctask.execute();

        ClientesAsynctask terminalesAsynctask = new ClientesAsynctask();
        terminalesAsynctask.execute();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_oferta, menu);

        // MenuInflater inflater = getMenuInflater();
        // inflater.inflate(R.menu.your_menu, menu);
        itemFirmar = menu.findItem(R.id.action_firmar);
        itemPresentar = menu.findItem(R.id.action_presentar);
        itemThumbko = menu.findItem(R.id.action_estado_ko);
        itemThumbok = menu.findItem(R.id.action_estado_ok);

        itemActionEdit = menu.findItem((R.id.action_editar));
        itemActionDelete = menu.findItem(R.id.action_delete);

        switch (oferta.getEstado()) {
            case Configuration.PRESENTADA:
                itemFirmar.setVisible(true);
                itemPresentar.setVisible(false);
                itemThumbok.setVisible(false);
                itemThumbko.setVisible(false);
                break;
            case Configuration.BORRADOR:
                itemFirmar.setVisible(false);
                itemPresentar.setVisible(true);
                itemThumbok.setVisible(false);
                itemThumbko.setVisible(false);
                itemActionEdit.setVisible(false);

                break;
            case Configuration.FIRMADA:
                itemFirmar.setVisible(false);
                itemPresentar.setVisible(false);
                itemActionEdit.setVisible(false);
                itemActionDelete.setVisible(false);

                break;

            default:
                itemPresentar.setVisible(false);
                itemFirmar.setVisible(false);
                itemThumbok.setVisible(false);
                itemThumbko.setVisible(false);

                itemActionEdit.setVisible(false);
                itemActionDelete.setVisible(false);
        }

        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            super.onBackPressed();
            return true;
        }
        if (id == R.id.action_presentar) {

            if (recyclerView.getAdapter().getItemCount() > 0) {
                muestraDialogoConfirmacion(Configuration.PRESENTAR);
            } else {
                Snackbar.make(fab, "No se puede presentar una oferta vacía", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
            return true;
        }

        if (id == R.id.action_firmar) {
            muestraDialogoConfirmacion(Configuration.FIRMAR);
            return true;
        }

        if (id == R.id.action_estado_ok) {
            muestraDialogoConfirmacion(Configuration.OK);
            return true;
        }

        if (id == R.id.action_estado_ko) {
            muestraDialogoConfirmacion(Configuration.KO);
            return true;
        }


        if (id == R.id.action_delete) {
            muestraDialogoConfirmacion(Configuration.ELIMINAR);
            return true;
        }

        if (id == R.id.action_editar) {
            muestraDialogoConfirmacion(Configuration.EDITAR);
            return true;
        }

        if (id == R.id.action_comisiones) {
            muestraDialogoComisiones();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void muestraDialogoComisiones() {
        // Create custom dialog object

        // final Dialog dialog = new Dialog(this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar);
        final AlertDialog dialog = new AlertDialog.Builder(this, android.R.style.Theme_DeviceDefault_Dialog_NoActionBar).create();
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.comisiones, null);
        dialog.setView(dialogView);

        //dialog.setContentView(R.layout.comisiones);

        rlComisionColor = (RelativeLayout) dialogView.findViewById(R.id.rlComisionColor);
        tvComision = (TextView) dialogView.findViewById(R.id.txComisionCantidad);
        // tvExtraComision = (TextView)dialogView.findViewById(R.id.txExtraComisionCantidad);
        tvPuntos = (TextView) dialogView.findViewById(R.id.txPuntosCantidad);
        final Button btnAceptar = (Button) dialogView.findViewById(R.id.btn_ok_comisiones);

        tvComision.setText(String.valueOf(oferta.getComisionBaseTotal()));
        float puntosTotal = oferta.getPuntosTotal() > 0 ? oferta.getPuntosTotal() : 0;
        float puntosLineas = oferta.getPuntosLineas() > 0 ? oferta.getPuntosLineas() : 0;

        tvPuntos.setText(String.valueOf(puntosTotal + puntosLineas));

        switch (oferta.getEstado()) {
            case Configuration.BORRADOR:
                rlComisionColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBorrador));
                break;
            case Configuration.PRESENTADA:
                rlComisionColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorPresentada));
                break;
            case Configuration.FIRMADA:
                rlComisionColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorFirmada));
                break;
            case Configuration.KO:
                rlComisionColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorKO));
                break;
            case Configuration.OK:
                rlComisionColor.setBackgroundColor(ContextCompat.getColor(context, R.color.colorOK));
                break;
        }

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        dialog.setCancelable(true);
        dialog.show();
    }


    private void muestraDialogoConfirmacion(final String accion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ActivityOferta.this);
        builder.setCancelable(true);
        builder.setTitle(getString(R.string.confirmation_action));
        builder.setMessage(setMensajeDialogo(accion));
        builder.setPositiveButton("Confirmar",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        switch (accion) {
                            case Configuration.KO:
                                oferta.setEstado(Configuration.KO);
                                oferta.setFechaKO((System.currentTimeMillis() / 1000L));
                                DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
                                actualizaEstadoOferta(false);
                                break;
                            case Configuration.OK:
                                oferta.setEstado(Configuration.OK);
                                oferta.setFechaOK((System.currentTimeMillis() / 1000L));
                                DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
                                actualizaEstadoOferta(false);
                                break;
                            case Configuration.FIRMAR:
                                oferta.setEstado(Configuration.FIRMADA);
                                oferta.setFechaFirma((System.currentTimeMillis() / 1000L));
                                DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
                                actualizaEstadoOferta(false);
                                break;
                            case Configuration.EDITAR:
                                oferta.setEstado(Configuration.BORRADOR);
                                DatabaseHelper.getInstance(context).updateEstadoCabeceraOferta(oferta);
                                actualizaEstadoOferta(false);
                                break;
                            case Configuration.ELIMINAR:
                                DatabaseHelper.getInstance(context).deleteOferta(oferta);
                                finish();
                                break;
                            case Configuration.PRESENTAR:
                                if (oferta.getCodCliente() == 0)
                                    mostrarBottomSeet();
                                else
                                    enviaOferta();
                                break;
                        }

                    }
                });
        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private String setMensajeDialogo(final String accion) {
        switch (accion) {
            case Configuration.KO:
                return getString(R.string.action_ko);

            case Configuration.OK:
                return getString(R.string.action_ok);

            case Configuration.FIRMAR:
                return getString(R.string.action_firma);

            case Configuration.EDITAR:
                return getString(R.string.action_edit_oferta);

            case Configuration.ELIMINAR:
                return getString(R.string.action_delete);

            case Configuration.PRESENTAR:
                return getString(R.string.action_presentar);

        }

        return "";
    }

    public void switchToFragment(Fragment fragment, String title, boolean backStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getCanonicalName();
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);


        if (currentFragment == null || !TextUtils.equals(tag, currentFragmentTag)) {
            if (backStack) {
                currentFragmentTag = tag;
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_activity_oferta, fragment, currentFragmentTag)
                        .addToBackStack(tag)
                        .commit();
            } else {
                currentFragmentTag = tag;
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_activity_oferta, fragment, currentFragmentTag)
                        .commit();
            }
        }
    }

    private class ClientesAsynctask extends AsyncTask<String, Void, ArrayList<Cliente>> {
        public ClientesAsynctask() {
        }

        @Override
        protected ArrayList<Cliente> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(context).getAllClientes();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute(ArrayList<Cliente> result) {
            if (result != null) {
                clientes = result;
            }
        }
    }

    private class LineasAsynctask extends AsyncTask<String, Void, ArrayList<Lineaoferta>> {
        int codOferta;

        public LineasAsynctask(int codOferta) {
            this.codOferta = codOferta;
        }

        @Override
        protected ArrayList<Lineaoferta> doInBackground(String... params) {
            try {
                return DatabaseHelper.getInstance(context).getAllLineasOferta(codOferta);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute(ArrayList<Lineaoferta> result) {
            if (result != null) {
                lineasOferta = result;
                recyclerView.setAdapter(new LineasRecyclerViewAdapter(result, lineaClickListener, oferta.getEstado(), getApplicationContext()));

                if (getIntent().getBooleanExtra("enviar", false)) {
                    getIntent().removeExtra("enviar");
                    enviaOferta();
                }
            }
        }
    }

    private class generarPDF extends AsyncTask<String, Void, Boolean> {
        int codOferta;

        public void generarPDF(int codOferta) {

            this.codOferta = codOferta;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            try {
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        @Override
        protected void onPreExecute() {
        }

        protected void onPostExecute(boolean result) {

        }
    }
}
