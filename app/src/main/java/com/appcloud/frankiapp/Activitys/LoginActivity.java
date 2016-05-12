package com.appcloud.frankiapp.Activitys;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import android.os.AsyncTask;

import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.EditorInfo;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Sync.APIRest;
import com.appcloud.frankiapp.Sync.VolleySingleton;
import com.appcloud.frankiapp.Utils.Commons;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends AppCompatActivity  {


    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };
    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;
    private VolleySingleton volleyInstance;
    private RequestQueue volleyQueue;
    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    // UI references.
    private LinearLayout lnlogin;
    private AutoCompleteTextView mUserView;
    private EditText mPasswordView;
    private CheckBox cbRecordar;
    private TextView tvRestablecer;
    private View mProgressView;
    private View mLoginFormView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.

        lnlogin = (LinearLayout)findViewById(R.id.ln_login_form);
        mUserView = (AutoCompleteTextView) findViewById(R.id.username);
        tvRestablecer = (TextView)findViewById(R.id.tv_restablecer);
        cbRecordar = (CheckBox)findViewById(R.id.cb_recordar);
        mProgressView = findViewById(R.id.login_progress);
        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });
        tvRestablecer.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                restablecer();
            }
        });
        Button btLogin = (Button) findViewById(R.id.bt_login);
        btLogin.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });




        volleyInstance = VolleySingleton.getInstance(getApplicationContext());
        volleyQueue = volleyInstance.getRequestQueue();

        prefs = getSharedPreferences("preferecias",MODE_PRIVATE);
        mUserView.setText(prefs.getString("username",""));
        mPasswordView.setText(prefs.getString("password",""));
        if(!prefs.getString("password","").equals(""))
        {
            cbRecordar.setChecked(true);
        }

        lnlogin.postDelayed(new Runnable() {
            @Override
            public void run() {
                lnlogin.setVisibility(View.VISIBLE);
            }
        },600);

    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mUserView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String username = mUserView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid username.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_field_required));
            focusView = mPasswordView;
            cancel = true;
        }
        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_field_required));
            focusView = mUserView;
            cancel = true;
        }


        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            if(cbRecordar.isChecked())
            {
                editor = prefs.edit();
                editor.putString("username",username);
                editor.putString("password", password);
                editor.apply();
            }
            Commons.mostrarTeclado(this,mPasswordView,false);
            showProgress(true);
            login();
        }
    }

    private void login()
    {
        Map<String, String> params = new HashMap();
        params.put(APIRest.KEY_USERNAME, mUserView.getText().toString());
        params.put(APIRest.KEY_PASSWORD, mPasswordView.getText().toString());
        params.put(APIRest.KEY_CLIENT_ID, APIRest.CLIENT_ID);
        params.put(APIRest.KEY_SCOPE,APIRest.SCOPE);

        JSONObject parameters = new JSONObject(params);
        JsonObjectRequest jsObjRequest = new JsonObjectRequest
                (Request.Method.POST, APIRest.autentificacion(), parameters, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        showProgress(false);
                        Log.i("response",response.toString());
                        try {
                            String accesToken = response.getString("access_token");
                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            try {
                                String error = response.getString("error");
                                Log.i("error",error);
                                switch (error)
                                {
                                    case APIRest.INVALID_USER:
                                        mUserView.setError(getString(R.string.error_invalid_username));
                                        mUserView.requestFocus();
                                        break;
                                    case APIRest.INVALID_CREDENTIALS:
                                        mPasswordView.setError(getString(R.string.error_incorrect_password));
                                        mPasswordView.requestFocus();
                                        break;
                                }

                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        showProgress(false);
                        Log.i("error", "Error: "+error.getMessage());
                    }

                });

        volleyQueue.add(jsObjRequest);
    }


    private void restablecer()
    {
        mUserView.setError(null);
        String username = mUserView.getText().toString();


        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mUserView.setError(getString(R.string.error_name_required));
            mUserView.requestFocus();
            return;
        }
        showProgress(true);
        StringRequest stringRequest = new StringRequest(Request.Method.POST,APIRest.restablecerPassword(), new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.i("response",response);
                if(response.equals("[true]"))
                {
                    Snackbar.make(mUserView,"Las instrucciones para restablecer la contraseña han sido enviadas a su correo electronico",Snackbar.LENGTH_LONG).show();
                }
                showProgress(false);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.i("response","Error: "+error.toString());
                mUserView.setError(getString(R.string.error_invalid_username));
                mUserView.requestFocus();
                showProgress(false);
            }
        }){
            @Override
            protected Map<String,String> getParams(){
                Map<String,String> params = new HashMap<String, String>();
                params.put(APIRest.KEY_NAME,mUserView.getText().toString());
                return params;
            }

        };
        volleyQueue.add(stringRequest);
    }



    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        }
    }








    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;

        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {
                // Simulate network access.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                return false;
            }


            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mAuthTask = null;
            showProgress(false);

            if (success) {
                Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            } else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

