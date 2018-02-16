package edu.temp.udc.proyectotempe.Vista.fragment.fragment_acerca_de;
import android.app.AlarmManager;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.Vista.FullscreenActivity;
import edu.temp.udc.proyectotempe.Vista.fragment.fragment_alarma.Fragment_alarma;
import edu.temp.udc.proyectotempe.services.MyReceiver;

/**
 * Created by Usuario on 28/11/2017.
 */

public class Fragment_acerca_de extends Fragment {
    private View v;
    FloatingActionButton compa;
    private static final int ALARM_REQUEST_CODE = 1;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        v=inflater.inflate(R.layout.acerca_de,container,false);
       compa=(FloatingActionButton) v.findViewById(R.id.fab_about_share);
        compa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i=new Intent(v.getContext(), FullscreenActivity.class);
                startActivity(i);
            }
        });
        return v;
    }
    private void establecerAlarmaClick(){
        AlarmManager manager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

//         (Modo no acoplado con un componente, ver AndroidManifest.xml)
//         Intent        intent  = new Intent("es.carlos_garcia.tutoriales.android.alarmmanager");

        Intent        intent  = new Intent(v.getContext(), MyReceiver.class);
        PendingIntent pIntent = PendingIntent.getBroadcast(v.getContext(), ALARM_REQUEST_CODE, intent,  PendingIntent.FLAG_CANCEL_CURRENT);

        manager.set(AlarmManager.RTC_WAKEUP, System.currentTimeMillis() + 1 * 1000, pIntent);
    }
}
