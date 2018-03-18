package edu.temp.udc.proyectotempe.Vista.fragment.fragment_usuario;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import edu.temp.udc.proyectotempe.Vista.fragment.fragment_acerca_de.Fragment_acerca_de;
import edu.temp.udc.proyectotempe.Vista.fragment.fragment_usuario.sub_fragment.Fragment_user;

/**
 * Created by haskhell on 3/9/18.
 */

public class MiFragmentPagerAdapter extends FragmentPagerAdapter {
    final int PAGE_COUNT = 3;
    private String tabTitles[] =
            new String[] { "Usuarios", "Temperatura", "Historial"};




    public MiFragmentPagerAdapter(FragmentManager childFragmentManager) {
        super(childFragmentManager);
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public Fragment getItem(int position) {

        Fragment_user f2 = new Fragment_user();


        switch(position) {
                case 0:
                f2 = new Fragment_user();
                return f2;
                case 2:
                f2 = new Fragment_user();
                return f2;
                case 4:
                f2 = new Fragment_user();
                return f2;
        }

        return f2;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        // Generate title based on item position
        return tabTitles[position];
    }
}
