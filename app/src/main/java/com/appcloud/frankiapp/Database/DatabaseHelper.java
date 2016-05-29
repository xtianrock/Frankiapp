package com.appcloud.frankiapp.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.POJO.Lineaoferta;
import com.appcloud.frankiapp.POJO.Oferta;
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.Utils.Configuration;

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
    public static final String PRECIOCONTERMINAL = "PRECIOCONTERMINAL";
    public static final String PRECIOCONVMOVIL = "PRECIOCONVMOVIL";
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
    public static final String PLANPRECIOTERMINAL = "PLANPRECIOTERMINAL";
    private static final String CREATE_TABLE_TARIFAS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_TARIFAS + "("
            + CODTARIFA + " INTEGER PRIMARY KEY,"
            + PLANPRECIOS + " TEXT,"
            + TIPOPLAN + " TEXT,"
            + PLANPRECIOTERMINAL + " TEXT,"
            + DESCORTA + " TEXT,"
            + DESLARGA + " TEXT,"
            + PRECIOCONTERMINAL + " NUMERIC,"
            + PRECIOCONVMOVIL + " NUMERIC,"
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
    public static final String PAGO_INICIAL_TARIFA = "PAGO_INICIAL_TARIFA";
    public static final String CUOTA_TARIFA = "CUOTA_TARIFA";
    public static final String PAGO_INICIAL_TERMINAL = "PAGO_INICIAL_TERMINAL";
    public static final String CUOTA_TERMINAL = "CUOTA_TERMINAL";
    public static final String COMISION_BASE_TOTAL = "COMISION_BASE_TOTAL";
    public static final String PUNTOS_TOTAL = "PUNTOS_TOTAL";
    public static final String PUNTOS_LINEAS = "PUNTOS_LINEAS";
    public static final String COMISION_EMPRESA = "COMISION_EMPRESA";
    public static final String LINEAS_MOVILES = "LINEAS_MOVILES";



    private static final String CREATE_TABLE_CABECERA_OFERTAS = "CREATE TABLE IF NOT EXISTS "
            + TABLE_CABECERAS_OFERTA + "("
            + CODOFERTA + " INTEGER PRIMARY KEY,"
            + CODCLIENTE + " INTEGER,"
            + ESTADO + " TEXT,"
            + FECHA_OFERTA + " INTEGER,"
            + FECHA_FIRMA + " INTEGER,"
            + FECHA_KO + " INTEGER,"
            + FECHA_OK + " INTEGER,"
            + PAGO_INICIAL_TARIFA + " NUMERIC,"
            + PAGO_INICIAL_TERMINAL + " NUMERIC,"
            + CUOTA_TARIFA + " NUMERIC,"
            + CUOTA_TERMINAL + " NUMERIC,"
            + COMISION_BASE_TOTAL + " NUMERIC,"
            + PUNTOS_TOTAL + " NUMERIC,"
            + PUNTOS_LINEAS + " NUMERIC,"
            + COMISION_EMPRESA + " NUMERIC,"
            + LINEAS_MOVILES + " NUMERIC,"
            + NOMBRE + " TEXT,"
            + APELLIDOS + " TEXT,"
            + POBLACION + " TEXT,"
            + " FOREIGN KEY ("+ CODCLIENTE +") REFERENCES "+ TABLE_CLIENTES +"("+ CODCLIENTE +"))";

    //===============================================================================================================================


    // TABLA LINEAS_OFERTA ===============================================================================================================

    public static final String CODLINEA = "CODLINEA";
    public static final String PRECIOTARIFAINICIAL = "PRECIOTARIFAINICIAL";
    public static final String PRECIOTARIFACUOTA = "PRECIOTARIFACUOTA";
    public static final String PRECIOTERMINALINICIAL = "PRECIOTERMINALINICIAL";
    public static final String PRECIOTERMINALCUOTA = "PRECIOTERMINALCUOTA";
    public static final String NUMEROTELEFONO = "NUMEROTELEFONO";
    public static final String OPERADORDONANTE = "OPERADORDONANTE";
    public static final String TIPOCONVERGENCIA = "TIPOCONVERGENCIA";
    public static final String CONVERGENCIAMOVIL = "CONVERGENCIAMOVIL";


    private static final String CREATE_TABLE_lINEAS_OFERTA = "CREATE TABLE IF NOT EXISTS "
            + TABLE_LINEAS_OFERTA + "("
            + CODLINEA + " INTEGER PRIMARY KEY,"
            + CODOFERTA + " INTEGER,"
            + CODTARIFA + " INTEGER,"
            + CODTERMINAL + " INTEGER,"
            + NUMEROTELEFONO + " TEXT,"
            + PLANPRECIOS + "  TEXT,"
            + TIPOPLAN + "  TEXT,"
            + OPERADORDONANTE + " TEXT,"
            + TIPOCONVERGENCIA + " TEXT,"
            + CONVERGENCIAMOVIL + " TEXT,"
            + PRECIOTARIFAINICIAL + " NUMERIC,"
            + PRECIOTARIFACUOTA + " NUMERIC,"
            + PRECIOTERMINALINICIAL + " NUMERIC,"
            + PRECIOTERMINALCUOTA + " NUMERIC,"
            + COMISIONBASE + " NUMERIC,"
            + COMISIONEXTRA + " NUMERIC,"
            + " FOREIGN KEY ("+ CODOFERTA +") REFERENCES "+ TABLE_CABECERAS_OFERTA +"("+ CODOFERTA +") ON DELETE CASCADE,"
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


    @Override
    public void onConfigure(SQLiteDatabase db){
        db.setForeignKeyConstraintsEnabled(true);
    }


    public static void importTarifas(Context context) {
        String filename = "tarifas.csv";
        String tableName = TABLE_TARIFAS;
        SQLiteDatabase db = DatabaseHelper.getInstance(context).getReadableDatabase();
        // db.execSQL("delete from " + tableName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));
            db.beginTransaction();
            String mLine;
            while ((mLine = reader.readLine()) != null) {
                String[] str = mLine.split(",",-1);
                ContentValues contentValues = new ContentValues();
                contentValues.put(CODTARIFA, str[0]);
                contentValues.put(PLANPRECIOS, str[1]);
                contentValues.put(TIPOPLAN, str[2]);
                contentValues.put(DESCORTA, str[3]);
                contentValues.put(DESLARGA, str[4]);
                contentValues.put(PRECIOCONTERMINAL, str[5]);
                contentValues.put(PRECIOCONVMOVIL, str[6]);
                contentValues.put(PRECIOSINCONV, str[7]);
                contentValues.put(PRECIOCONVXXL, str[8]);
                contentValues.put(PRECIOCONVXL, str[9]);
                contentValues.put(PRECIOCONVL, str[10]);
                contentValues.put(PRECIOCONVM, str[11]);
                contentValues.put(PRECIOCONVS, str[12]);
                contentValues.put(PRECIOCONVMINIS, str[13]);
                contentValues.put(COSTEALTA, str[14]);
                contentValues.put(COMISIONBASE, str[15]);
                contentValues.put(COMISIONEXTRA, str[16]);
                contentValues.put(PLANPRECIOTERMINAL, str[17]);

                //db.insert(tableName, null, contentValues);
                // CARLOS
                db.insertWithOnConflict(tableName, null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
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

    public static void importExtracomision(Context context)
    {
        String filename = "extracomision.csv";
        String tableName = TABLE_EXTRACOMISION;
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
                contentValues.put(MINIMOPUNTOS, str[0]);
                contentValues.put(EXTRACOMISION,str[1] );
                db.insert(tableName, null, contentValues);
            }
            db.setTransactionSuccessful();
            db.endTransaction();
            Log.i("import", "puntos extracomision importados con exito");
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
     //   db.execSQL("delete from " + tableName);
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(
                    new InputStreamReader(context.getAssets().open(filename)));
            db.beginTransaction();

            String mLine;
            while ((mLine = reader.readLine()) != null) {


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

                db.insertWithOnConflict(tableName, null, contentValues,SQLiteDatabase.CONFLICT_REPLACE);
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

    public Terminal getTerminalByCod(int codTerminal) {
        String selectQuery = "SELECT  * FROM " + TABLE_TERMINALES_SMART +
                " WHERE " + CODTERMINAL + " = "+ codTerminal;
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Terminal terminal = null;
        if (c!=null && c.moveToFirst()) {
            terminal = new Terminal();
            terminal.setCodTerminal(c.getInt(c.getColumnIndex(CODTERMINAL)));
            terminal.setDescripcion((c.getString(c.getColumnIndex(NOMBRETERMINAL))));
            terminal.setColor((c.getString(c.getColumnIndex(COLOR))));
            terminal.setSim((c.getString(c.getColumnIndex(SIM))));
            terminal.setLte((c.getString(c.getColumnIndex(LTE))));
            terminal.setVodCasaInicial(c.getFloat(c.getColumnIndex(VODCASAINICIAL)));
            terminal.setVodCasaCuota(c.getFloat(c.getColumnIndex(VODCASACUOTA)));
            terminal.setVodCasaPvp(c.getFloat(c.getColumnIndex(VODCASAPVP)));
            terminal.setXsInicial(c.getFloat(c.getColumnIndex(XSINICIAL)));
            terminal.setXsCouta(c.getFloat(c.getColumnIndex(XSCUOTA)));
            terminal.setXsPvp(c.getFloat(c.getColumnIndex(XSPVP)));
            terminal.setMiniInicial(c.getFloat(c.getColumnIndex(MINIINICIAL)));
            terminal.setMiniCouta(c.getFloat(c.getColumnIndex(MINICUOTA)));
            terminal.setMiniPvp(c.getFloat(c.getColumnIndex(MINIPVP)));
            terminal.setsInicial(c.getFloat(c.getColumnIndex(SINICIAL)));
            terminal.setsCouta(c.getFloat(c.getColumnIndex(SCUOTA)));
            terminal.setSpvp(c.getFloat(c.getColumnIndex(SPVP)));
            terminal.setmInicial(c.getFloat(c.getColumnIndex(MINICIAL)));
            terminal.setmCuota(c.getFloat(c.getColumnIndex(MCUOTA)));
            terminal.setmPvp(c.getFloat(c.getColumnIndex(MPVP)));
            terminal.setlInicial(c.getFloat(c.getColumnIndex(LINICIAL)));
            terminal.setlCuota(c.getFloat(c.getColumnIndex(LCUOTA)));
            terminal.setlPvp(c.getFloat(c.getColumnIndex(LPVP)));
            terminal.setXlInicial(c.getFloat(c.getColumnIndex(XLINICIAL)));
            terminal.setXlCuota(c.getFloat(c.getColumnIndex(XLCUOTA)));
            terminal.setXlPvp(c.getFloat(c.getColumnIndex(XLPVP)));
        }
        c.close();
        return terminal;
    }


    public ArrayList<Terminal> getAllTerminales() {
        ArrayList<Terminal> terminales = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TERMINALES_SMART;
        Log.d(LOG, selectQuery);

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
                terminal.setVodCasaInicial(c.getFloat(c.getColumnIndex(VODCASAINICIAL)));
                terminal.setVodCasaCuota(c.getFloat(c.getColumnIndex(VODCASACUOTA)));
                terminal.setVodCasaPvp(c.getFloat(c.getColumnIndex(VODCASAPVP)));
                terminal.setXsInicial(c.getFloat(c.getColumnIndex(XSINICIAL)));
                terminal.setXsCouta(c.getFloat(c.getColumnIndex(XSCUOTA)));
                terminal.setXsPvp(c.getFloat(c.getColumnIndex(XSPVP)));
                terminal.setMiniInicial(c.getFloat(c.getColumnIndex(MINIINICIAL)));
                terminal.setMiniCouta(c.getFloat(c.getColumnIndex(MINICUOTA)));
                terminal.setMiniPvp(c.getFloat(c.getColumnIndex(MINIPVP)));
                terminal.setsInicial(c.getFloat(c.getColumnIndex(SINICIAL)));
                terminal.setsCouta(c.getFloat(c.getColumnIndex(SCUOTA)));
                terminal.setSpvp(c.getFloat(c.getColumnIndex(SPVP)));
                terminal.setmInicial(c.getFloat(c.getColumnIndex(MINICIAL)));
                terminal.setmCuota(c.getFloat(c.getColumnIndex(MCUOTA)));
                terminal.setmPvp(c.getFloat(c.getColumnIndex(MPVP)));
                terminal.setlInicial(c.getFloat(c.getColumnIndex(LINICIAL)));
                terminal.setlCuota(c.getFloat(c.getColumnIndex(LCUOTA)));
                terminal.setlPvp(c.getFloat(c.getColumnIndex(LPVP)));
                terminal.setXlInicial(c.getFloat(c.getColumnIndex(XLINICIAL)));
                terminal.setXlCuota(c.getFloat(c.getColumnIndex(XLCUOTA)));
                terminal.setXlPvp(c.getFloat(c.getColumnIndex(XLPVP)));
                terminales.add(terminal);
            } while (c.moveToNext());
        }
        c.close();
        return terminales;
    }


    public ArrayList<Terminal> getAllTerminalesByPlan(String planPrecios) {
        ArrayList<Terminal> terminales = new ArrayList<>();
        String whereField= "";
        switch (planPrecios)
        {
            case Configuration.XS: whereField = XSINICIAL;
                break;
            case Configuration.MINI: whereField = MINICIAL;
                break;
            case Configuration.S: whereField = SINICIAL;
                break;
            case Configuration.M: whereField = MINICIAL;
                break;
            case Configuration.L: whereField = LINICIAL;
                break;
            case Configuration.XL: whereField = XLINICIAL;
                break;
        }
        String selectQuery = "SELECT  * FROM " + TABLE_TERMINALES_SMART
                + " WHERE "+ whereField + " IS NOT NULL"
                + " AND "+ whereField + "!= ''";
        Log.d(LOG, selectQuery);

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
                terminal.setVodCasaInicial(c.getFloat(c.getColumnIndex(VODCASAINICIAL)));
                terminal.setVodCasaCuota(c.getFloat(c.getColumnIndex(VODCASACUOTA)));
                terminal.setVodCasaPvp(c.getFloat(c.getColumnIndex(VODCASAPVP)));
                terminal.setXsInicial(c.getFloat(c.getColumnIndex(XSINICIAL)));
                terminal.setXsCouta(c.getFloat(c.getColumnIndex(XSCUOTA)));
                terminal.setXsPvp(c.getFloat(c.getColumnIndex(XSPVP)));
                terminal.setMiniInicial(c.getFloat(c.getColumnIndex(MINIINICIAL)));
                terminal.setMiniCouta(c.getFloat(c.getColumnIndex(MINICUOTA)));
                terminal.setMiniPvp(c.getFloat(c.getColumnIndex(MINIPVP)));
                terminal.setsInicial(c.getFloat(c.getColumnIndex(SINICIAL)));
                terminal.setsCouta(c.getFloat(c.getColumnIndex(SCUOTA)));
                terminal.setSpvp(c.getFloat(c.getColumnIndex(SPVP)));
                terminal.setmInicial(c.getFloat(c.getColumnIndex(MINICIAL)));
                terminal.setmCuota(c.getFloat(c.getColumnIndex(MCUOTA)));
                terminal.setmPvp(c.getFloat(c.getColumnIndex(MPVP)));
                terminal.setlInicial(c.getFloat(c.getColumnIndex(LINICIAL)));
                terminal.setlCuota(c.getFloat(c.getColumnIndex(LCUOTA)));
                terminal.setlPvp(c.getFloat(c.getColumnIndex(LPVP)));
                terminal.setXlInicial(c.getFloat(c.getColumnIndex(XLINICIAL)));
                terminal.setXlCuota(c.getFloat(c.getColumnIndex(XLCUOTA)));
                terminal.setXlPvp(c.getFloat(c.getColumnIndex(XLPVP)));
                terminales.add(terminal);
            } while (c.moveToNext());
        }
        c.close();
        return terminales;
    }

    public ArrayList<Tarifa> getAllTarifas() {
        ArrayList<Tarifa> tarifas = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_TARIFAS;
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Tarifa tarifa = new Tarifa();
                tarifa.setCodTarifa(c.getInt(c.getColumnIndex(CODTARIFA)));
                tarifa.setPlanPrecios((c.getString(c.getColumnIndex(PLANPRECIOS))));
                tarifa.setTipoPlan((c.getString(c.getColumnIndex(TIPOPLAN))));
                tarifa.setPlanPrecioTerminal(c.getString(c.getColumnIndex(PLANPRECIOTERMINAL)));
                tarifa.setDesCorta((c.getString(c.getColumnIndex(DESCORTA))));
                tarifa.setDesLarga((c.getString(c.getColumnIndex(DESLARGA))));
                tarifa.setPrecioConTerminal((c.getFloat(c.getColumnIndex(PRECIOCONTERMINAL))));
                tarifa.setPrecioConvergenciaMovil((c.getFloat(c.getColumnIndex(PRECIOCONVMOVIL))));
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

    public Tarifa getTarifaByCod(int codTarifa) {
        String selectQuery = "SELECT  * FROM " + TABLE_TARIFAS +
                " WHERE " + CODTARIFA + " = " + codTarifa;
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Tarifa tarifa = null;
        if (c!=null && c.moveToFirst()) {
            tarifa = new Tarifa();
            tarifa.setCodTarifa(c.getInt(c.getColumnIndex(CODTARIFA)));
            tarifa.setPlanPrecios((c.getString(c.getColumnIndex(PLANPRECIOS))));
            tarifa.setTipoPlan((c.getString(c.getColumnIndex(TIPOPLAN))));
            tarifa.setPlanPrecioTerminal(c.getString(c.getColumnIndex(PLANPRECIOTERMINAL)));
            tarifa.setDesCorta((c.getString(c.getColumnIndex(DESCORTA))));
            tarifa.setDesLarga((c.getString(c.getColumnIndex(DESLARGA))));
            tarifa.setPrecioConTerminal((c.getFloat(c.getColumnIndex(PRECIOCONTERMINAL))));
            tarifa.setPrecioConvergenciaMovil((c.getFloat(c.getColumnIndex(PRECIOCONVMOVIL))));
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
        }
        c.close();
        return tarifa;
    }

    public ArrayList<Cliente> getAllClientes() {
        ArrayList<Cliente> clientes = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CLIENTES;
        Log.d(LOG, selectQuery);

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
        Log.d(LOG, selectQuery);

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

    public Cliente getLastIdCliente() {
        String selectQuery = "SELECT  max (" + CODCLIENTE + ") FROM " + TABLE_CLIENTES;
        Log.d(LOG, selectQuery);
        int codCliente = -1;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst())
        {
            codCliente = c.getInt(0);

        }
        c.close();


        return getCliente(codCliente);
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

        return db.update(TABLE_CLIENTES, values,whereClause,null);

    }

    public ArrayList<Oferta> getAllOfertas(String estado) {
        ArrayList<Oferta> ofertas = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CABECERAS_OFERTA +
                " WHERE " + ESTADO + " = '" + estado +"'";
        if(estado.equals(Configuration.FIRMADA))
            selectQuery+=" OR " + ESTADO + " = 'OK' OR " + ESTADO + " = 'KO'";
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Oferta oferta = new Oferta();
                oferta.setCodOferta(c.getInt(c.getColumnIndex(CODOFERTA)));
                oferta.setCodCliente(c.getInt(c.getColumnIndex(CODCLIENTE)));
                oferta.setEstado((c.getString(c.getColumnIndex(ESTADO))));
                oferta.setFechaOferta((c.getLong(c.getColumnIndex(FECHA_OFERTA))));
                oferta.setFechaFirma((c.getLong(c.getColumnIndex(FECHA_FIRMA))));
                oferta.setFechaKO((c.getLong(c.getColumnIndex(FECHA_KO))));
                oferta.setFechaOK((c.getLong(c.getColumnIndex(FECHA_OK))));
                oferta.setPagoInicialTarifa((c.getFloat(c.getColumnIndex(PAGO_INICIAL_TARIFA))));
                oferta.setCuotaTarifa((c.getFloat(c.getColumnIndex(CUOTA_TARIFA))));
                oferta.setPagoInicialTerminal((c.getFloat(c.getColumnIndex(PAGO_INICIAL_TERMINAL))));
                oferta.setCuotaTerminal((c.getFloat(c.getColumnIndex(CUOTA_TERMINAL))));
                oferta.setComisionBaseTotal((c.getFloat(c.getColumnIndex(COMISION_BASE_TOTAL))));
                oferta.setPuntosTotal((c.getFloat(c.getColumnIndex(PUNTOS_TOTAL))));
                oferta.setCommisionEmpresa((c.getFloat(c.getColumnIndex(COMISION_EMPRESA))));
                oferta.setNombre(c.getString(c.getColumnIndex(NOMBRE)));
                oferta.setApellidos((c.getString(c.getColumnIndex(APELLIDOS))));
                oferta.setPoblacion(c.getString(c.getColumnIndex(POBLACION)));
                ofertas.add(oferta);
            } while (c.moveToNext());
        }
        c.close();
        return ofertas;
    }

    public Oferta getOferta(int codOferta) {
        Oferta oferta = new Oferta();
        String selectQuery = "SELECT  * FROM " + TABLE_CABECERAS_OFERTA
                + " WHERE "+ CODOFERTA + " = " + codOferta;
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst())
        {
            oferta.setCodOferta(c.getInt(c.getColumnIndex(CODOFERTA)));
            oferta.setCodCliente(c.getInt(c.getColumnIndex(CODCLIENTE)));
            oferta.setEstado((c.getString(c.getColumnIndex(ESTADO))));
            oferta.setFechaOferta((c.getLong(c.getColumnIndex(FECHA_OFERTA))));
            oferta.setFechaFirma((c.getLong(c.getColumnIndex(FECHA_FIRMA))));
            oferta.setFechaKO((c.getLong(c.getColumnIndex(FECHA_KO))));
            oferta.setFechaOK((c.getLong(c.getColumnIndex(FECHA_OK))));
            oferta.setPagoInicialTarifa((c.getFloat(c.getColumnIndex(PAGO_INICIAL_TARIFA))));
            oferta.setCuotaTarifa((c.getFloat(c.getColumnIndex(CUOTA_TARIFA))));
            oferta.setPagoInicialTerminal((c.getFloat(c.getColumnIndex(PAGO_INICIAL_TERMINAL))));
            oferta.setCuotaTerminal((c.getFloat(c.getColumnIndex(CUOTA_TERMINAL))));
            oferta.setComisionBaseTotal((c.getFloat(c.getColumnIndex(COMISION_BASE_TOTAL))));
            oferta.setPuntosTotal((c.getFloat(c.getColumnIndex(PUNTOS_TOTAL))));
            oferta.setPuntosLineas((c.getFloat(c.getColumnIndex(PUNTOS_LINEAS))));
            oferta.setCommisionEmpresa((c.getFloat(c.getColumnIndex(COMISION_EMPRESA))));
            oferta.setLineasMoviles((c.getInt(c.getColumnIndex(LINEAS_MOVILES))));
            oferta.setNombre(c.getString(c.getColumnIndex(NOMBRE)));
            oferta.setApellidos((c.getString(c.getColumnIndex(APELLIDOS))));
            oferta.setPoblacion(c.getString(c.getColumnIndex(POBLACION)));

        }
        c.close();
        return oferta;
    }

    public long createCabecceraOferta(Oferta oferta) {
        SQLiteDatabase db = this.getWritableDatabase();
        float cero = 0.0f;
        ContentValues values = new ContentValues();

        values.put(FECHA_OFERTA, oferta.getFechaOferta());
        values.put(ESTADO, Configuration.BORRADOR);
        values.put(PAGO_INICIAL_TARIFA,cero);
        values.put(CUOTA_TARIFA,cero);
        values.put(PAGO_INICIAL_TERMINAL,cero);
        values.put(CUOTA_TERMINAL,cero);
        values.put(COMISION_BASE_TOTAL,cero);
        values.put(PUNTOS_TOTAL,cero);
        values.put(PUNTOS_LINEAS,cero);
        values.put(COMISION_EMPRESA,cero);

        long value=  db.insert(TABLE_CABECERAS_OFERTA, null, values);
        return value;
    }

    public void updateEstadoCabeceraOferta(Oferta oferta){

        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause= " WHERE " +CODOFERTA +" = " + oferta.getCodOferta();

         String sql = "UPDATE " + TABLE_CABECERAS_OFERTA + " SET "+
                 CODCLIENTE + "=" + "'" +oferta.getCodCliente() + "'" + ","+
                 ESTADO + "=" + "'" +oferta.getEstado() + "'" +
                /*ESTADO + "=" + "'" +oferta.getEstado() + "'" + ","+
                FECHA_OFERTA + "=" +  oferta.getFechaOferta() + ","+
                FECHA_FIRMA + "=" +  oferta.getFechaFirma() + ","+
                FECHA_KO + "=" +  oferta.getFechaKO() +" " +*/
                whereClause;
        Log.i("update   ----->",sql);
        db.execSQL(sql);
    }

    public long updateCabeceraOferta(Oferta oferta) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause= CODOFERTA +" = " + oferta.getCodOferta();

        ContentValues values = new ContentValues();
        values.put(CODCLIENTE, oferta.getCodCliente()!=0 ? oferta.getCodCliente(): null);
        values.put(ESTADO, oferta.getEstado());
        values.put(FECHA_OFERTA,oferta.getFechaOferta());
        values.put(FECHA_FIRMA,oferta.getFechaFirma());
        values.put(FECHA_KO,oferta.getFechaKO());
        values.put(FECHA_OK,oferta.getFechaOK());
        values.put(PAGO_INICIAL_TARIFA,oferta.getPagoInicialTarifa());
        values.put(CUOTA_TARIFA,oferta.getCuotaTarifa());
        values.put(PAGO_INICIAL_TERMINAL,oferta.getPagoInicialTerminal());
        values.put(CUOTA_TERMINAL,oferta.getCuotaTerminal());
        values.put(COMISION_BASE_TOTAL,oferta.getComisionBaseTotal());
        values.put(PUNTOS_TOTAL,oferta.getPuntosTotal());
        values.put(PUNTOS_LINEAS,oferta.getPuntosLineas());
        values.put(COMISION_EMPRESA,oferta.getCommisionEmpresa());
        values.put(LINEAS_MOVILES,oferta.getLineasMoviles());
        values.put(NOMBRE,oferta.getNombre());
        values.put(APELLIDOS,oferta.getApellidos());
        values.put(POBLACION,oferta.getPoblacion());


        return db.update(TABLE_CABECERAS_OFERTA, values,whereClause,null);
    }

    public long deleteOferta(Oferta oferta) {

        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause= CODOFERTA +" = " + oferta.getCodOferta();


        long lineaEliminada = db.delete(TABLE_CABECERAS_OFERTA, whereClause,null);



        return lineaEliminada;
    }


    public ArrayList<Lineaoferta> getAllLineasOferta(int codigoOferta) {
        ArrayList<Lineaoferta> lineas = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_LINEAS_OFERTA +" WHERE "+CODOFERTA+ " = "+codigoOferta;
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);


        if (c.moveToFirst()) {
            do {
                Lineaoferta linea = new Lineaoferta(codigoOferta);
                linea.setCodLinea(c.getInt(c.getColumnIndex(CODLINEA)));
                linea.setCodOferta(c.getInt(c.getColumnIndex(CODOFERTA)));
                linea.setCodTarifa(c.getInt(c.getColumnIndex(CODTARIFA)));
                linea.setCodTerminal((c.getInt(c.getColumnIndex(CODTERMINAL))));
                linea.setNumeroTelefono((c.getString(c.getColumnIndex(NUMEROTELEFONO))));
                linea.setPlanPrecios((c.getString(c.getColumnIndex(PLANPRECIOS))));
                linea.setTipoPlan((c.getString(c.getColumnIndex(TIPOPLAN))));
                linea.setOperadorDonante((c.getString(c.getColumnIndex(OPERADORDONANTE))));
                linea.setTipoConvergencia((c.getString(c.getColumnIndex(TIPOCONVERGENCIA))));
                linea.setConvergenciaMovil((c.getString(c.getColumnIndex(CONVERGENCIAMOVIL))));
                linea.setPrecioTarifaInicial((c.getFloat(c.getColumnIndex(PRECIOTARIFAINICIAL))));
                linea.setPrecioTarifaCuota((c.getFloat(c.getColumnIndex(PRECIOTARIFACUOTA))));
                linea.setPrecioTerminalInicial((c.getFloat(c.getColumnIndex(PRECIOTERMINALINICIAL))));
                linea.setPrecioTErminalCuota((c.getFloat(c.getColumnIndex(PRECIOTERMINALCUOTA))));
                linea.setComisionBase((c.getFloat(c.getColumnIndex(COMISIONBASE))));
                linea.setComisionExtra((c.getFloat(c.getColumnIndex(COMISIONEXTRA))));
                lineas.add(linea);
            } while (c.moveToNext());
        }
        c.close();
        return lineas;
    }

    public Lineaoferta getLineaOferta(int codLinea) {
        String selectQuery = "SELECT * FROM " + TABLE_LINEAS_OFERTA +" WHERE "+CODLINEA+ " = "+codLinea;
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        Lineaoferta linea = null;
        if (c!=null && c.moveToFirst()) {
            linea = new Lineaoferta(c.getInt(c.getColumnIndex(CODOFERTA)));
            linea.setCodLinea(c.getInt(c.getColumnIndex(CODLINEA)));
            linea.setCodTarifa(c.getInt(c.getColumnIndex(CODTARIFA)));
            linea.setCodTerminal((c.getInt(c.getColumnIndex(CODTERMINAL))));
            linea.setNumeroTelefono((c.getString(c.getColumnIndex(NUMEROTELEFONO))));
            linea.setPlanPrecios((c.getString(c.getColumnIndex(PLANPRECIOS))));
            linea.setTipoPlan((c.getString(c.getColumnIndex(TIPOPLAN))));
            linea.setOperadorDonante((c.getString(c.getColumnIndex(OPERADORDONANTE))));
            linea.setTipoConvergencia((c.getString(c.getColumnIndex(TIPOCONVERGENCIA))));
            linea.setConvergenciaMovil((c.getString(c.getColumnIndex(CONVERGENCIAMOVIL))));
            linea.setPrecioTarifaInicial((c.getFloat(c.getColumnIndex(PRECIOTARIFAINICIAL))));
            linea.setPrecioTarifaCuota((c.getFloat(c.getColumnIndex(PRECIOTARIFACUOTA))));
            linea.setPrecioTerminalInicial((c.getFloat(c.getColumnIndex(PRECIOTERMINALINICIAL))));
            linea.setPrecioTErminalCuota((c.getFloat(c.getColumnIndex(PRECIOTERMINALCUOTA))));
            linea.setComisionBase((c.getFloat(c.getColumnIndex(COMISIONBASE))));
            linea.setComisionExtra((c.getFloat(c.getColumnIndex(COMISIONEXTRA))));
        }
        c.close();
        return linea;
    }

    public long createLineaOferta(Lineaoferta linea) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(CODOFERTA, linea.getCodOferta());
        values.put(CODTARIFA, linea.getCodTarifa());
        if(linea.getCodTerminal()!=0)
            values.put(CODTERMINAL, linea.getCodTerminal());
        values.put(NUMEROTELEFONO, linea.getNumeroTelefono());
        values.put(PLANPRECIOS, linea.getPlanPrecios());
        values.put(TIPOPLAN, linea.getTipoPlan());
        values.put(OPERADORDONANTE, linea.getOperadorDonante());
        values.put(TIPOCONVERGENCIA, linea.getTipoConvergencia());
        values.put(CONVERGENCIAMOVIL, linea.getConvergenciaMovil());
        values.put(PRECIOTARIFAINICIAL, linea.getPrecioTarifaInicial());
        values.put(PRECIOTARIFACUOTA, linea.getPrecioTarifaCuota());
        values.put(PRECIOTERMINALINICIAL, linea.getPrecioTerminalInicial());
        values.put(PRECIOTERMINALCUOTA, linea.getPrecioTErminalCuota());
        values.put(COMISIONBASE, linea.getComisionBase());
        values.put(COMISIONEXTRA, linea.getComisionExtra());

        db.beginTransaction();

        long result = db.insert(TABLE_LINEAS_OFERTA, null, values);



       String whereClause= " WHERE " + CODOFERTA +" = " + linea.getCodOferta();
        String sql = "UPDATE " + TABLE_CABECERAS_OFERTA + " SET "+
                PAGO_INICIAL_TARIFA + "=" + PAGO_INICIAL_TARIFA + "+" + linea.getPrecioTarifaInicial() + ","+
                CUOTA_TARIFA + "=" + CUOTA_TARIFA + "+" + linea.getPrecioTarifaCuota() + ","+
                PAGO_INICIAL_TERMINAL + "=" + PAGO_INICIAL_TERMINAL + "+" + linea.getPrecioTerminalInicial() + ","+
                CUOTA_TERMINAL + "=" + CUOTA_TERMINAL + "+" + linea.getPrecioTErminalCuota() +
                whereClause;
        Log.i("update   ----->",sql);
        db.execSQL(sql);


        db.setTransactionSuccessful();
        db.endTransaction();

        return result;
    }

    public long updateLineaOferta(Lineaoferta lineaActual, Lineaoferta lineaPreciosNuevos) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause= CODLINEA +" = " + lineaActual.getCodLinea();

        ContentValues values = new ContentValues();
        values.put(CODOFERTA, lineaActual.getCodOferta());
        values.put(CODTARIFA, lineaActual.getCodTarifa());
        values.put(CODTERMINAL, lineaActual.getCodTerminal()!=0 ? lineaActual.getCodTerminal(): null);
        values.put(NUMEROTELEFONO, lineaActual.getNumeroTelefono());
        values.put(PLANPRECIOS, lineaActual.getPlanPrecios());
        values.put(TIPOPLAN, lineaActual.getTipoPlan());
        values.put(OPERADORDONANTE, lineaActual.getOperadorDonante());
        values.put(TIPOCONVERGENCIA, lineaActual.getTipoConvergencia());
        values.put(CONVERGENCIAMOVIL, lineaActual.getConvergenciaMovil());
        values.put(PRECIOTARIFAINICIAL, lineaPreciosNuevos.getPrecioTarifaInicial());
        values.put(PRECIOTARIFACUOTA, lineaPreciosNuevos.getPrecioTarifaCuota());
        values.put(PRECIOTERMINALINICIAL, lineaPreciosNuevos.getPrecioTerminalInicial());
        values.put(PRECIOTERMINALCUOTA, lineaPreciosNuevos.getPrecioTErminalCuota());
        values.put(COMISIONBASE, lineaActual.getComisionBase());
        values.put(COMISIONEXTRA, lineaActual.getComisionExtra());

        db.beginTransaction();

        long lineaActualizada = db.update(TABLE_LINEAS_OFERTA, values,whereClause,null);

        float diferenciaTarifaInicial= lineaPreciosNuevos.getPrecioTarifaInicial()-lineaActual.getPrecioTarifaInicial();
        float diferenciaTarifaCuota= lineaPreciosNuevos.getPrecioTarifaCuota()-lineaActual.getPrecioTarifaCuota();
        float diferenciaTerminalInicial= lineaPreciosNuevos.getPrecioTerminalInicial()-lineaActual.getPrecioTerminalInicial();
        float diferenciaterminalCuota= lineaPreciosNuevos.getPrecioTErminalCuota()-lineaActual.getPrecioTErminalCuota();

        String ofertaWhereClause = " WHERE " + CODOFERTA +" = " + lineaActual.getCodOferta();
        String sql = "UPDATE " + TABLE_CABECERAS_OFERTA + " SET "+
                PAGO_INICIAL_TARIFA + "=" + PAGO_INICIAL_TARIFA + "+" + diferenciaTarifaInicial + ","+
                CUOTA_TARIFA + "=" + CUOTA_TARIFA + "+" + diferenciaTarifaCuota + ","+
                PAGO_INICIAL_TERMINAL + "=" + PAGO_INICIAL_TERMINAL + "+" + diferenciaTerminalInicial + ","+
                CUOTA_TERMINAL + "=" + CUOTA_TERMINAL + "+" + diferenciaterminalCuota +
                ofertaWhereClause;
        Log.i("update   ----->",sql);
        db.execSQL(sql);

        db.setTransactionSuccessful();
        db.endTransaction();

        return lineaActualizada;
    }

    public long deleteLineaOferta(Lineaoferta linea) {

        SQLiteDatabase db = this.getReadableDatabase();
        String whereClause= CODLINEA +" = " + linea.getCodLinea();

        db.beginTransaction();

        long lineaEliminada = db.delete(TABLE_LINEAS_OFERTA, whereClause,null);

        String ofertaWhereClause = " WHERE " + CODOFERTA +" = " + linea.getCodOferta();
        String sql = "UPDATE " + TABLE_CABECERAS_OFERTA + " SET "+
                PAGO_INICIAL_TARIFA + "=" + PAGO_INICIAL_TARIFA + "-" + linea.getPrecioTarifaInicial() + ","+
                CUOTA_TARIFA + "=" + CUOTA_TARIFA + "-" + linea.getPrecioTarifaCuota() + ","+
                PAGO_INICIAL_TERMINAL + "=" + PAGO_INICIAL_TERMINAL + "-" + linea.getPrecioTerminalInicial() + ","+
                CUOTA_TERMINAL + "=" + CUOTA_TERMINAL + "-" + linea.getPrecioTErminalCuota() +
                ofertaWhereClause;
        Log.i("delete   ----->",sql);
        db.execSQL(sql);

        db.setTransactionSuccessful();
        db.endTransaction();

        return lineaEliminada;
    }

    public float getPuntosByLineasMoviles(int lineasMoviles) {
        String selectQuery = "SELECT " + PUNTOS + " FROM " + TABLE_PUNTOS +" WHERE "+ NUMLINEAS + " = " + lineasMoviles;
        Log.d(LOG, selectQuery);

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        float puntos = 0;
        if (c!=null && c.moveToFirst()) {
            puntos = (c.getFloat(0));
        }
        c.close();
        return puntos;
    }

    }