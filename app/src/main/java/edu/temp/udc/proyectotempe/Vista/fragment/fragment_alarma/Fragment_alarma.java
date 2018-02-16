package edu.temp.udc.proyectotempe.Vista.fragment.fragment_alarma;

import android.app.Fragment;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Timer;
import java.util.TimerTask;

import edu.temp.udc.proyectotempe.R;
/**
 * Created by Usuario on 5/12/2017.
 */

public class Fragment_alarma extends Fragment {
View v;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
       v=inflater.inflate(R.layout.fragment_alerta,container,false);
        Ringtone defaultRingtone = RingtoneManager.getRingtone(getActivity(),
                Settings.System.DEFAULT_RINGTONE_URI);
        //fetch current Ringtone
        Uri currentRintoneUri = RingtoneManager.getActualDefaultRingtoneUri(getActivity()
                .getApplicationContext(), RingtoneManager.TYPE_ALARM);
        Ringtone currentRingtone = RingtoneManager.getRingtone(getActivity(), currentRintoneUri);
        //display Ringtone title
        //play current Ringtone
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Vibrator vib = (Vibrator) getActivity().getSystemService(v.getContext().getApplicationContext().VIBRATOR_SERVICE);
                vib.vibrate(1000);
            }
        }, 0, 2000);

        currentRingtone.play();
        currentRingtone.stop();
        return v;

    }
}
