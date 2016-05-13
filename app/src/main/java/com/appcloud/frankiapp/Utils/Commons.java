package com.appcloud.frankiapp.Utils;

import android.content.Context;
import android.os.Handler;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.appcloud.frankiapp.R;

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
}
