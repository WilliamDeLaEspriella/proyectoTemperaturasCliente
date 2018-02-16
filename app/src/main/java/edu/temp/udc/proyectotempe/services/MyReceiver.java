package edu.temp.udc.proyectotempe.services;

import android.widget.Toast;

/**
 * Created by Usuario on 5/12/2017.
 */

public class MyReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(android.content.Context context, android.content.Intent intent) {
        Toast.makeText(context, "Tu lógica de negocio irá aquí. En caso de requerir más de unos milisegundos, debería de la tarea a un servicio", Toast.LENGTH_LONG).show();
    }
}