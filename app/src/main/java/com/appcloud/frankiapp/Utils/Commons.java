package com.appcloud.frankiapp.Utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfDocument;
import android.os.Build;
import android.os.Handler;
import android.provider.DocumentsContract;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.appcloud.frankiapp.R;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by cristian on 28/04/2016.
 */
public class Commons {



    public static void mostrarTeclado(final Context context, final EditText editText, final boolean visible) {
        Handler mHandler;
        Runnable mShowImeRunnable;
        mShowImeRunnable = new Runnable() {
            public void run() {
                InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);

                if (imm != null) {
                    imm.showSoftInput(editText, 0);
                }
            }
        };
        mHandler= new Handler();
        if (visible) {
            mHandler.post(mShowImeRunnable);
        } else {
            mHandler.removeCallbacks(mShowImeRunnable);
            InputMethodManager imm = (InputMethodManager)context.getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
            }
        }
    }

    public static int getTema(String estado)
    {
        switch (estado)
        {
            case Configuration.BORRADOR:
                 return R.style.AppTheme_NoActionBar_borrador;
            case Configuration.PRESENTADA:
                return R.style.AppTheme_NoActionBar_presentada;
            case Configuration.FIRMADA:
                return R.style.AppTheme_NoActionBar_firmada;
            case Configuration.KO:
                return R.style.AppTheme_NoActionBar_ko;
            case Configuration.OK:
                return R.style.AppTheme_NoActionBar_ok;
            default:
                return R.style.AppTheme_NoActionBar_presentada;
        }
    }

    public static Bitmap getViewBitmap(View v, int maxWidth, int maxHeight) {
        //Get the dimensions of the view so we can re-layout the view at its current size
        //and create a bitmap of the same size
        int width = v.getWidth();
        int height = v.getHeight();

        int measuredWidth = View.MeasureSpec.makeMeasureSpec(width, View.MeasureSpec.EXACTLY);
        int measuredHeight = View.MeasureSpec.makeMeasureSpec(height, View.MeasureSpec.EXACTLY);

        //Cause the view to re-layout
        v.measure(measuredWidth, measuredHeight);
        v.layout(0, 0, v.getMeasuredWidth(), v.getMeasuredHeight());

        //Create a bitmap backed Canvas to draw the view into
        Bitmap b = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(b);

        //Now that the view is laid out and we have a canvas, ask the view to draw itself into the canvas
        v.draw(c);

        return b;
    }

    public static PdfDocument createPDFDocument(View view, Context context, PdfDocument document){
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT) {

            // create a new document
            if (document == null)
                document = new PdfDocument();

            // crate a page description
            PdfDocument.PageInfo pageInfo =  new PdfDocument.PageInfo.Builder(1240,1754, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);

            // draw something on the page
            //View content = getContentView();
            view.draw(page.getCanvas());

            // finish the page
            document.finishPage(page);

           /* try {

                File cachePath = new File(context.getCacheDir(), "images");
                cachePath.mkdirs(); // don't forget to make the directory
                FileOutputStream stream = new FileOutputStream(cachePath + "/documento.pdf"); // overwrites this image every time
                document.writeTo(stream);

                stream.close();

                // close the document
                document.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }

        return document;
    }

    public static boolean savePDFDocument(PdfDocument ofertaPDF, Context context){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {

                File cachePath = new File(context.getCacheDir(), "images");
                cachePath.mkdirs(); // don't forget to make the directory
                FileOutputStream stream = new FileOutputStream(cachePath + "/documento.pdf"); // overwrites this image every time

                ofertaPDF.writeTo(stream);


                stream.close();

                // close the document
                ofertaPDF.close();

            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return  false;
            } catch (IOException e) {
                e.printStackTrace();
                return  false;
            }

            return true;
        }
        return  false;
    }
}
