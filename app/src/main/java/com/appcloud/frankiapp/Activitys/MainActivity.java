package com.appcloud.frankiapp.Activitys;


import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;


import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.View;
import android.support.design.widget.NavigationView;

import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.appcloud.frankiapp.Database.DatabaseHelper;
import com.appcloud.frankiapp.Fragments.ListaClientesFragment;
import com.appcloud.frankiapp.Fragments.ClienteFragment;
import com.appcloud.frankiapp.Fragments.TabFragment;
import com.appcloud.frankiapp.Fragments.ListaTarifasFragment;
import com.appcloud.frankiapp.Fragments.ListaTerminalesFragment;
import com.appcloud.frankiapp.Interfaces.OnClienteInteractionListener;
import com.appcloud.frankiapp.Interfaces.OnTarifaInteractionListener;
import com.appcloud.frankiapp.Interfaces.OnTerminalInteractionListener;
import com.appcloud.frankiapp.POJO.Cliente;
import com.appcloud.frankiapp.POJO.Tarifa;
import com.appcloud.frankiapp.POJO.Terminal;
import com.appcloud.frankiapp.R;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        OnTerminalInteractionListener,
        OnTarifaInteractionListener,
        OnClienteInteractionListener{

    DatabaseHelper database = DatabaseHelper.getInstance(this);
    DrawerLayout drawer;
    AppBarLayout appBarLayout;
    TabLayout tabLayout;
    FloatingActionButton fab;
    String currentFragmentTag;
    Snackbar snackbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appBarLayout = (AppBarLayout)findViewById(R.id.appbarlayout);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        tabLayout = (TabLayout)findViewById(R.id.tabs);
        fab = (FloatingActionButton)findViewById(R.id.fab);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        switchToFragment(new TabFragment(),"Ofertas",true);

        database.getWritableDatabase();
        DatabaseHelper.importTarifas(this);
        DatabaseHelper.importPuntos(this);
        DatabaseHelper.importTerminalesSmart(this);

    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            //super.onBackPressed();
            if(snackbar!=null && snackbar.isShown())
                finish();
            else
            {
                snackbar = Snackbar.make(fab,"Pulse de nuevo para salir", Snackbar.LENGTH_SHORT);
                snackbar.show();
            }


        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        Fragment fragment = null;
        if (id == R.id.nav_clientes) {
            fragment = new ListaClientesFragment();
           tabLayout.setVisibility(View.GONE);
            fab.setVisibility(View.VISIBLE);
        } else if (id == R.id.nav_telefonos) {
            fragment = new ListaTerminalesFragment();
            tabLayout.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_tarifas) {
            fragment = new ListaTarifasFragment();
            tabLayout.setVisibility(View.GONE);
            fab.setVisibility(View.GONE);
        } else if (id == R.id.nav_preofertas) {
            fragment = new TabFragment();
            tabLayout.setVisibility(View.VISIBLE);
            fab.setVisibility(View.VISIBLE);
        }

        // Insert the fragment by replacing any existing fragment
        if(fragment!=null)
        {
           switchToFragment(fragment,item.getTitle().toString(),false);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void switchToFragment(Fragment fragment, String title, boolean backStack) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        String tag = fragment.getClass().getCanonicalName();
        Fragment currentFragment = fragmentManager.findFragmentByTag(currentFragmentTag);

        if (currentFragment == null || !TextUtils.equals(tag, currentFragmentTag)) {
            if(backStack)
            {
                currentFragmentTag = tag;
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment, currentFragmentTag)
                        .addToBackStack(tag)
                        .commit();
            }
            else
            {
                currentFragmentTag = tag;
                fragmentManager
                        .beginTransaction()
                        .replace(R.id.content_main, fragment, currentFragmentTag)
                        .commit();
            }
            setTitle(title);
            appBarLayout.setExpanded(true, true);
        }
    }

    @Override
    public void onListFragmentInteraction(Terminal item) {
       // Toast.makeText(getApplicationContext(),"pulsado: "+item.getDescripcion(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(Tarifa item) {
       //Toast.makeText(getApplicationContext(),"pulsado: "+item.getPlanPrecios(),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onListFragmentInteraction(Cliente cliente) {

        Fragment fragment = new ClienteFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("cliente",cliente.getCodCliente());
        fragment.setArguments(bundle);
        switchToFragment(fragment,cliente.getNombre(),true);


    }

}
