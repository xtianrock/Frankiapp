package com.appcloud.frankiapp.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.appcloud.frankiapp.R;
import com.appcloud.frankiapp.Utils.Configuration;

/**
 * Created by cristian on 03/05/2016.
 */
public class TabFragment extends Fragment {

    public static TabLayout tabLayout;
    public static ViewPager viewPager;
    public static int int_items = 3 ;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        /**
         *Inflate tab_layout and setup Views.
         */
        View view =  inflater.inflate(R.layout.tablayout,null);
        tabLayout = (TabLayout) getActivity().findViewById(R.id.tabs);
        viewPager = (ViewPager) view.findViewById(R.id.viewpager);

        /**
         *Set an Apater for the View Pager
         */
        viewPager.setAdapter(new MyAdapter(getChildFragmentManager()));

        /**
         * Now , this is a workaround ,
         * The setupWithViewPager dose't works without the runnable .
         * Maybe a Support Library Bug .
         */

        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        return view;

    }

    class MyAdapter extends FragmentPagerAdapter {

        public MyAdapter(FragmentManager fm) {
            super(fm);
        }

        /**
         * Return fragment with respect to Position .
         */

        @Override
        public Fragment getItem(int position)
        {
            ListaOfertasFragment listaOferta;
            Bundle bundle;
            switch (position){
                case 0 :
                    bundle =new Bundle();
                    bundle.putString(Configuration.TAB,Configuration.BORRADOR);
                    listaOferta = new ListaOfertasFragment();
                    listaOferta.setArguments(bundle);
                    return listaOferta;
                case 1 :
                    bundle =new Bundle();
                    bundle.putString(Configuration.TAB,Configuration.PRESENTADA);
                    listaOferta = new ListaOfertasFragment();
                    listaOferta.setArguments(bundle);
                    return listaOferta;
                case 2 :
                    bundle =new Bundle();
                    bundle.putString(Configuration.TAB,Configuration.FIRMADA);
                    listaOferta = new ListaOfertasFragment();
                    listaOferta.setArguments(bundle);
                    return listaOferta;
            }
            return null;
        }

        @Override
        public int getCount() {

            return int_items;

        }

        /**
         * This method returns the title of the tab according to the position.
         */

        @Override
        public CharSequence getPageTitle(int position) {

            switch (position){
                case 0 :
                    return "Borrador";
                case 1 :
                    return "Presentadas";
                case 2 :
                    return "Firmadas";
            }
            return null;
        }
    }

}