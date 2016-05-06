package com.appcloud.frankiapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.test.SingleLaunchActivityTestCase;
import android.util.Log;

import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.POJO.Terminal;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Created by cristian on 27/04/2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {

    private static DatabaseHelper instance;
    // Logcat tag
    private static final String LOG = DatabaseHelper.class.getName();

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "frankiapp.db";

    // Table Names
    public static final String TABLE_CABECERAS_OFERTA = "TABLE_CABECERA_OFERTAS";
    public static final String TABLE_LINEAS_OFERTA = "TABLE_LINEA_OFERTAS";
    public static final String TABLE_TARIFAS = "TABLE_TARIFAS";
    public static final String TABLE_TERMINALES_SMART = "TABLE_TERMINALES_SMART";
    public static final String TABLE_CLIENTES = "TABLE_CLIENTES";
    public static final String TABLE_PUNTOS = "TABLE_PUNTOS";
    public static final String TABLE_EXTRACOMISION = "TABLE_EXTRACOMISION";



    // TABLA EXTRACOMISION ===============================================================================================================

    public static final String MINIMOPUNTOS = "MINIMOPUNTOS";
    public static final String EXTRACOMISION = "EXTRACOMISION";

    private static final String CREATE_TABLE_EXTRACOMISION = "CREATE TABLE IF NOT EXISTS "
            + TABLE_EXTRACOMISION + "("
            + MINIMOPUNTOS + " NUMERIC PRIMARY KEY,"
            + EXTRACOMISION + " NUMERIC)";

    //===============================================================================================================================



    // TABLA PUNTOS ===============================================================================================================

    public static final String NUMLINEAS = "NUMLINEAS";
    public static final String PUNTOS = "PUNTOS";

    private static final String CREATE_TABLE_PUNTOS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_PUNTOS + "("
            + NUMLINEAS + " INTEGER PRIMARY KEY,"
            + PUNTOS + " NUMERIC)";

    //===============================================================================================================================



// TABLA CLIENTES ===================================================================================================================

    public static final String CODCLIENTE = "CODCLIENTE";
    public static final String NOMBRE = "NOMBRE";
    public static final String APELLIDOS = "APELLIDOS";
    public static final String NIF = "NIF";
    public static final String TELEFONO = "TELEFONO";
    public static final String EMAIL = "EMAIL";
    public static final String DIRECCION = "DIRECCION";
    public static final String CPOSTAL = "CPOSTAL";
    public static final String POBLACION = "POBLACION";
    public static final String PROVINCIA = "PROVINCIA";
    private static final String CREATE_TABLE_CLIENTES = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CLIENTES + "("
            + CODCLIENTE + " INTEGER PRIMARY KEY,"
            + NOMBRE + " TEXT,"
            + APELLIDOS + " TEXT,"
            + NIF + " TEXT,"
            + TELEFONO + " TEXT,"
            + EMAIL + " TEXT,"
            + DIRECCION + " TEXT,"
            + CPOSTAL + " TEXT,"
            + POBLACION + " TEXT,"
            + PROVINCIA + " TEXT)";
    //===============================================================================================================================

    // TABLA TERMINALES ===============================================================================================================

    public static final String CODTERMINAL = "CODTERMINAL";
    public static final String NOMBRETERMINAL = "NOMBRETERMINAL";
    public static final String COLOR = "COLOR";
    public static final String LTE = "LTE";
    public static final String SIM = "SIM";
    public static final String VODCASAINICIAL = "VODCASAINICIAL";
    public static final String VODCASACUOTA = "VODCASACUOTA";
    public static final String VODCASAPVP = "VODCASAPVP";
    public static final String XSINICIAL = "XSINICIAL";
    public static final String XSCUOTA = "XSCUOTA";
    public static final String XSPVP = "XSPVP";
    public static final String MINIINICIAL = "MINIINICIAL";
    public static final String MINICUOTA = "MINICUOTA";
    public static final String MINIPVP = "MINIPVP";
    public static final String SINICIAL = "SINICIAL";
    public static final String SCUOTA = "SCUOTA";
    public static final String SPVP = "SPVP";
    public static final String MINICIAL = "MINICIAL";
    public static final String MCUOTA = "MCUOTA";
    public static final String MPVP = "MPVP";
    public static final String LINICIAL = "LINICIAL";
    public static final String LCUOTA = "LCUOTA";
    public static final String LPVP = "LPVP";
    public static final String XLINICIAL = "XLINICIAL";
    public static final String XLCUOTA = "XLCUOTA";
    public static final String XLPVP = "XLPVP";


    private static final String CREATE_TABLE_TERMINALES_SMART = "CREATE TABLE IF NOT EXISTS "
            + TABLE_TERMINALES_SMART + "("
            + CODTERMINAL + " INTEGER PRIMARY KEY,"
            + NOMBRETERMINAL + " TEXT,"
            + COLOR + " TEXT,"
            + LTE + " TEXT,"
            + SIM + " TEXT,"
            + VODCASAINICIAL + " NUMERIC,"
            + VODCASACUOTA + " NUMERIC,"
            + VODCASAPVP + " NUMERIC,"
            + XSINICIAL + " NUMERIC,"
            + XSCUOTA + " NUMERIC,"
            + XSPVP + " NUMERIC,"
            + MINIINICIAL + " NUMERIC,"
            + MINICUOTA + " NUMERIC,"
            + MINIPVP + " NUMERIC,"
            + SINICIAL + " NUMERIC,"
            + SCUOTA + " NUMERIC,"
            + SPVP + " NUMERIC,"
            + MINICIAL + " NUMERIC,"
            + MCUOTA + " NUMERIC,"
            + MPVP + " NUMERIC,"
            + LINICIAL + " NUMERIC,"
            + LCUOTA + " NUMERIC,"
            + LPVP + " NUMERIC,"
            + XLINICIAL + " NUMERIC,"
            + XLCUOTA + " NUMERIC,"
            + XLPVP + " NUMERIC)";

    //===============================================================================================================================

// TABLA TARIFAS ===================================================================================================================

    public static final String CODTARIFA = "CODTARIFA";
    public static final String PLANPRECIOS = "PLANPRECIOS";
    public static final String TIPOPLAN = "TIPOPLAN";
    public static final String DESCORTA = "DESCORTA";
    public static final String DESLARGA = "DESLARGA";
    public static final String NUMTELEFONO = "NUMTELEFONO";
    public static final String PRECIOCONTERMINAL = "PRECIOCONTERMINAL";
    public static final String PRECIOSINTERMINAL = "PRECIOSINTERMINAL";
    public static final String PRECIOSINCONV = "PRECIOSINCONV";
    public static final String PRECIOCONVXXL = "PRECIONCONVXXL";
    public static final String PRECIOCONVXL = "PRECIONCONVXL";
    public static final String PRECIOCONVL = "PRECIONCONVL";
    public static final String PRECIOCONVM = "PRECIOCONVM";
    public static final String PRECIOCONVS = "PRECIOCONVS";
    public static final String PRECIOCONVMINIS = "PRECIOCONVMINIS";
    public static final String COSTEALTA = "COSTEALTA";
    public static final String PRECIOFIJOENMOVIL = "PRECIOFIJOENMOVIL";
    public static final String COMISIONBASE = "COMISIONBASE";
    public static final String COMISIONEXTRA = "COMISIONEXTRA";
    public static final String COMISIONPORTA = "COMISIONPORTA";
    private static final String CREATE_TABLE_TARIFAS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_TARIFAS + "("
            + CODTARIFA + " INTEGER PRIMARY KEY,"
            + PLANPRECIOS + " TEXT,"
            + TIPOPLAN + " TEXT,"
            + DESCORTA + " TEXT,"
            + DESLARGA + " TEXT,"
            + PRECIOCONTERMINAL + " NUMERIC,"
            + PRECIOSINTERMINAL + " NUMERIC,"
            + PRECIOSINCONV + " NUMERIC,"
            + PRECIOCONVXXL + " NUMERIC,"
            + PRECIOCONVXL + " NUMERIC,"
            + PRECIOCONVL + " NUMERIC,"
            + PRECIOCONVM + " NUMERIC,"
            + PRECIOCONVS + " NUMERIC,"
            + PRECIOCONVMINIS + " NUMERIC,"
            + COSTEALTA + " NUMERIC,"
            + COMISIONBASE + " NUMERIC,"
            + COMISIONEXTRA + " NUMERIC,"
            + COMISIONPORTA + " NUMERIC)";
    //===============================================================================================================================



    // TABLA CABECERA_OFERTAS ===============================================================================================================

    public static final String CODOFERTA = "CODOFERTA";
    public static final String ESTADO = "ESTADO";
    public static final String FECHA_OFERTA = "FECHA_OFERTA";
    public static final String FECHA_FIRMA = "FECHA_FIRMA";
    public static final String FECHA_KO = "FECHA_KO";
    public static final String FECHA_OK = "FECHA_OK";
    public static final String PAGO_INICIAL = "PAGO_INICIAL";
    public static final String CUOTA_MENSUAL = "CUOTA_MENSUAL";
    public static final String COMISION_BASE_TOTAL = "COMISION_BASE_TOTAL";
    public static final String PUNTOS_TOTAL = "PUNTOS_TOTAL";
    public static final String COMISION_EMPRESA = "COMISION_EMPRESA";


    //TODO AÑADIR EL RESTOS DE CAMPOS A MOSTRAR EN EL RECYCLER

    private static final String CREATE_TABLE_CABECERA_OFERTAS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CABECERAS_OFERTA + "("
            + CODOFERTA + " INTEGER,"
            + CODCLIENTE + " INTEGER,"
            + ESTADO + " TEXT,"
            + FECHA_OFERTA + " INTEGER,"
            + FECHA_FIRMA + " INTEGER,"
            + FECHA_KO + " INTEGER,"
            + FECHA_OK + " INTEGER,"
            + PAGO_INICIAL + " NUMERIC,"
            + CUOTA_MENSUAL + " NUMERIC,"
            + COMISION_BASE_TOTAL + " NUMERIC,"
            + PUNTOS_TOTAL + " NUMERIC,"
            + COMISION_EMPRESA + " NUMERIC,"
            + " FOREIGN KEY ("+ CODCLIENTE +") REFERENCES "+ TABLE_CLIENTES +"("+ CODCLIENTE +"))";

    //===============================================================================================================================


    // TABLA LINEAS_OFERTA ===============================================================================================================

    //TODO AÑADIR EL RESTOS DE CAMPOS A MOSTRAR EN EL RECYCLER


    private static final String CREATE_TABLE_lINEAS_OFERTA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_LINEAS_OFERTA + "("
            + CODOFERTA + " INTEGER,"
            + CODTARIFA + " INTEGER,"
            + CODTERMINAL + " INTEGER,"
            + NUMTELEFONO + " TEXT,"
            + PRECIOFIJOENMOVIL + " NUMERIC,"
            + " FOREIGN KEY ("+ CODOFERTA +") REFERENCES "+ TABLE_CABECERAS_OFERTA +"("+ CODOFERTA +"),"
            + " FOREIGN KEY ("+ CODTERMINAL +") REFERENCES "+ TABLE_TERMINALES_SMART +"("+ CODTERMINAL +"),"
            + " FOREIGN KEY ("+ CODTARIFA +") REFERENCES "+ TABLE_TARIFAS +"("+ CODTARIFA +"))";

    //===============================================================================================================================


    public DatabaseHelper(final Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }
    public static synchronized DatabaseHelper getInstance(Context context)
    {
        if (instance == null)
            instance = new DatabaseHelper(context);

        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_TERMINALES_SMART);
        db.execSQL(CREATE_TABLE_CLIENTES);
        db.execSQL(CREATE_TABLE_PUNTOS);
        db.execSQL(CREATE_TABLE_EXTRACOMISION);
        db.execSQL(CREATE_TABLE_TARIFAS);
        db.execSQL(CREATE_TABLE_CABECERA_OFERTAS);
        db.execSQL(CREATE_TABLE_lINEAS_OFERTA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LINEAS_OFERTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CABECERAS_OFERTA);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TARIFAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PUNTOS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_EXTRACOMISION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CLIENTES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMINALES_SMART);

        // create new tables
        onCreate(db);
    }



    public static void importTarifas(Context context) {
        String filename = "tarifas.csv";
        String tableName = TABLE_TARIFAS;
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        db.execSQL("delete from " + tableName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));
            db.beginTransaction();
            int counter=0;
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                if(counter>0)
                {  Log.i("terminales"," - "+counter);

                    String[] str = mLine.split(",",-1);

                    ContentValues contentValues = new ContentValues();
                    contentValues.put(PLANPRECIOS, str[0]);
                    contentValues.put(TIPOPLAN, str[1]);
                    contentValues.put(DESCORTA, str[2]);
                    contentValues.put(DESLARGA, str[3]);
                    contentValues.put(PRECIOCONTERMINAL, str[4]);
                    contentValues.put(PRECIOSINTERMINAL, str[5]);
                    contentValues.put(PRECIOSINCONV, str[6]);
                    contentValues.put(PRECIOCONVXXL, str[7]);
                    contentValues.put(PRECIOCONVXL, str[8]);
                    contentValues.put(PRECIOCONVL, str[9]);
                    contentValues.put(PRECIOCONVM, str[10]);
                    contentValues.put(PRECIOCONVS, str[11]);
                    contentValues.put(PRECIOCONVMINIS, str[12]);
                    contentValues.put(COSTEALTA, str[13]);
                    contentValues.put(COMISIONBASE, str[14]);
                    contentValues.put(COMISIONEXTRA, str[15]);
                    //contentValues.put(COMISION_EMPRESA, str[16]);

                    db.insert(tableName, null, contentValues);
                }
               counter++;
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            Log.i("import", "tarifas importadas con exito");
        } catch (Exception ex) {
            ex.printStackTrace();
            if (db.inTransaction())
                db.endTransaction();

        }

    }


    public static void importPuntos(Context context)
    {
        String filename = "puntos.csv";
        String tableName = TABLE_PUNTOS;
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        db.execSQL("delete from " + tableName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));
            db.beginTransaction();

            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] str = mLine.split(",",-1);
                ContentValues contentValues = new ContentValues();
                contentValues.put(NUMLINEAS, str[0]);
                contentValues.put(PUNTOS,str[1] );
                db.insert(tableName, null, contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            Log.i("import", "puntos importados con exito");
        } catch (Exception ex) {
            ex.printStackTrace();
            if (db.inTransaction())
                db.endTransaction();

        }

    }

    public static void importTerminalesSmart(Context context)
    {
        String filename = "terminales_smart.csv";
        String tableName = TABLE_TERMINALES_SMART;
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        db.execSQL("delete from " + tableName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));
            db.beginTransaction();

            String mLine;
            int contador=0;
            while ((mLine = reader.readLine()) != null) {

                Log.i("terminales"," - "+contador);
                if(contador==76)
                    Log.i("terminales"," - "+contador);
                String[] str = mLine.split(",",-1);
                ContentValues contentValues = new ContentValues();
                contentValues.put(NOMBRETERMINAL, str[0]);
                contentValues.put(CODTERMINAL, str[1]);
                contentValues.put(COLOR, str[3]);
                contentValues.put(LTE, str[4]);
                contentValues.put(SIM, str[5]);
                contentValues.put(VODCASAINICIAL, str[6]);
                contentValues.put(VODCASACUOTA, str[7]);
                contentValues.put(VODCASAPVP, str[8]);
                contentValues.put(XSINICIAL, str[9]);
                contentValues.put(XSCUOTA, str[10]);
                contentValues.put(XSPVP, str[11]);
                contentValues.put(MINIINICIAL, str[12]);
                contentValues.put(MINICUOTA, str[13]);
                contentValues.put(MINIPVP, str[14]);
                contentValues.put(SINICIAL, str[15]);
                contentValues.put(SCUOTA, str[16]);
                contentValues.put(SPVP, str[17]);
                contentValues.put(MINICIAL, str[18]);
                contentValues.put(MCUOTA, str[19]);
                contentValues.put(MPVP, str[20]);
                contentValues.put(LINICIAL, str[21]);
                contentValues.put(LCUOTA, str[22]);
                contentValues.put(LPVP, str[23]);
                contentValues.put(XLINICIAL, str[24]);
                contentValues.put(XLCUOTA, str[25]);
                contentValues.put(XLPVP, str[26]);

                db.insert(tableName, null, contentValues);
                contador++;
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            Log.i("import", "puntos importados con exito");
        } catch (Exception ex) {
            ex.printStackTrace();
            if (db.inTransaction())
                db.endTransaction();

        }

    }


    public ArrayList<Terminal> getAllTErminales() {
        ArrayList<Terminal> terminales = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TERMINALES_SMART;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Terminal terminal = new Terminal();
                terminal.setCodTerminal(c.getInt(c.getColumnIndex(CODTERMINAL)));
                terminal.setDescripcion((c.getString(c.getColumnIndex(NOMBRETERMINAL))));
                terminal.setColor((c.getString(c.getColumnIndex(COLOR))));
                terminal.setSim((c.getString(c.getColumnIndex(SIM))));
                terminal.setLte((c.getString(c.getColumnIndex(LTE))));
                terminales.add(terminal);
            } while (c.moveToNext());
        }
        c.close();
        return terminales;
    }

    public ArrayList<Tarifa> getAllTarifas() {
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TARIFAS;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Tarifa tarifa = new Tarifa();
                tarifa.setCodTarifa(c.getInt(c.getColumnIndex(CODTARIFA)));
                tarifa.setPlanPrecios((c.getString(c.getColumnIndex(PLANPRECIOS))));
                tarifa.setTipoPlan((c.getString(c.getColumnIndex(TIPOPLAN))));
                tarifa.setDesCorta((c.getString(c.getColumnIndex(DESCORTA))));
                tarifa.setDesLarga((c.getString(c.getColumnIndex(DESLARGA))));
                tarifa.setPrecioConTerminal((c.getFloat(c.getColumnIndex(PRECIOCONTERMINAL))));
                tarifa.setPrecioSinTerminal((c.getFloat(c.getColumnIndex(PRECIOSINTERMINAL))));
                tarifa.setPrecioSinConv((c.getFloat(c.getColumnIndex(PRECIOSINCONV))));
                tarifa.setPrecioConvXXL((c.getFloat(c.getColumnIndex(PRECIOCONVXXL))));
                tarifa.setPrecioConvXL((c.getFloat(c.getColumnIndex(PRECIOCONVXL))));
                tarifa.setPrecioConvL((c.getFloat(c.getColumnIndex(PRECIOCONVL))));
                tarifa.setPrecioConvM((c.getFloat(c.getColumnIndex(PRECIOCONVM))));
                tarifa.setPrecioConvS((c.getFloat(c.getColumnIndex(PRECIOCONVS))));
                tarifa.setPrecioConvMINIS(c.getFloat(c.getColumnIndex(PRECIOCONVMINIS)));
                tarifa.setCosteAlta((c.getFloat(c.getColumnIndex(COSTEALTA))));
                tarifa.setComisionBase((c.getFloat(c.getColumnIndex(COMISIONBASE))));
                tarifa.setComisionExtra((c.getFloat(c.getColumnIndex(COMISIONEXTRA))));
                tarifa.setComisionPorta((c.getFloat(c.getColumnIndex(COMISIONPORTA))));


                tarifas.add(tarifa);
            } while (c.moveToNext());
        }
        c.close();
        return tarifas;
    }

    public ArrayList<Cliente> getAllClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTES;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Cliente cliente = new Cliente();
                cliente.setCodCliente(c.getInt(c.getColumnIndex(CODCLIENTE)));
                cliente.setNombre(c.getString(c.getColumnIndex(NOMBRE)));
                cliente.setApellidos((c.getString(c.getColumnIndex(APELLIDOS))));
                cliente.setNif((c.getString(c.getColumnIndex(NIF))));
                cliente.setTelefono((c.getString(c.getColumnIndex(TELEFONO))));
                cliente.setEmail((c.getString(c.getColumnIndex(EMAIL))));
                cliente.setDireccion((c.getString(c.getColumnIndex(DIRECCION))));
                cliente.setCpostal((c.getString(c.getColumnIndex(CPOSTAL))));
                cliente.setProvincia((c.getString(c.getColumnIndex(PROVINCIA))));
                cliente.setPoblacion((c.getString(c.getColumnIndex(POBLACION))));
                clientes.add(cliente);
            } while (c.moveToNext());
        }
        c.close();
        return clientes;
    }

    public Cliente getCliente(int codCliente) {
        Cliente cliente = new Cliente();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTES
                + " WHERE "+ CODCLIENTE + " = " + codCliente;
        Log.e(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst())
        {
            cliente.setCodCliente(c.getInt(c.getColumnIndex(CODCLIENTE)));
            cliente.setNombre(c.getString(c.getColumnIndex(NOMBRE)));
            cliente.setApellidos((c.getString(c.getColumnIndex(APELLIDOS))));
            cliente.setNif((c.getString(c.getColumnIndex(NIF))));
            cliente.setTelefono((c.getString(c.getColumnIndex(TELEFONO))));
            cliente.setEmail((c.getString(c.getColumnIndex(EMAIL))));
            cliente.setDireccion((c.getString(c.getColumnIndex(DIRECCION))));
            cliente.setCpostal((c.getString(c.getColumnIndex(CPOSTAL))));
            cliente.setProvincia((c.getString(c.getColumnIndex(PROVINCIA))));
            cliente.setPoblacion((c.getString(c.getColumnIndex(POBLACION))));
        }
        c.close();
        return cliente;
    }


    public long createCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(NOMBRE, cliente.getNombre());
        values.put(APELLIDOS, cliente.getApellidos());
        values.put(NIF,cliente.getNif());
        values.put(TELEFONO,cliente.getTelefono());
        values.put(EMAIL,cliente.getEmail());
        values.put(DIRECCION,cliente.getDireccion());
        values.put(CPOSTAL,cliente.getCpostal());
        values.put(PROVINCIA,cliente.getProvincia());
        values.put(POBLACION,cliente.getPoblacion());

        // insert row
        return db.insert(TABLE_CLIENTES, null, values);

    }

    public long updateCliente(Cliente cliente) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause= CODCLIENTE +" = " +cliente.getCodCliente();

        ContentValues values = new ContentValues();
        values.put(NOMBRE, cliente.getNombre());
        values.put(APELLIDOS, cliente.getApellidos());
        values.put(NIF,cliente.getNif());
        values.put(TELEFONO,cliente.getTelefono());
        values.put(EMAIL,cliente.getEmail());
        values.put(DIRECCION,cliente.getDireccion());
        values.put(CPOSTAL,cliente.getCpostal());
        values.put(PROVINCIA,cliente.getProvincia());
        values.put(POBLACION,cliente.getPoblacion());

        // insert row
        return db.update(TABLE_CLIENTES, values,whereClause,null);

    }


}