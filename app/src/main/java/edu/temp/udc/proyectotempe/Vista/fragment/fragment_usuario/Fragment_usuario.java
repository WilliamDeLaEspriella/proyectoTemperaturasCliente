package edu.temp.udc.proyectotempe.Vista.fragment.fragment_usuario;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import edu.temp.udc.proyectotempe.ApiRest.adapter.Endpoints;
import edu.temp.udc.proyectotempe.ApiRest.adapter.Rest_adapter;
import edu.temp.udc.proyectotempe.ApiRest.model.UserDevice;
import edu.temp.udc.proyectotempe.Vista.Dialog.AddUserDevice;
import edu.temp.udc.proyectotempe.Vista.MainActivity;
import edu.temp.udc.proyectotempe.tempo.Token;
import edu.temp.udc.proyectotempe.util.ItemTouchHelperCallback;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import edu.temp.udc.proyectotempe.R;

/**
 * Created by Usuario on 16/11/2017.
 */

//public class Fragment_usuario extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Observer {
public class Fragment_usuario extends Fragment  {
    private View view;
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_person, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.viewpager);
        viewPager.setAdapter(new MiFragmentPagerAdapter(((FragmentActivity) getActivity()).getSupportFragmentManager()));
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.appbartabs);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }


/*

*/


}
