package edu.temp.udc.proyectotempe.Vista.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.temp.udc.proyectotempe.ApiRest.adapter.Endpoints;
import edu.temp.udc.proyectotempe.ApiRest.adapter.Rest_adapter;
import edu.temp.udc.proyectotempe.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 17/11/2017.
 */

public class CreateDevice extends DialogFragment {
    EditText create;
    private View v;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();
         v = inflater.inflate(R.layout.dialog_create_device, null);
        create=(EditText) v.findViewById(R.id.textDevice) ;
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setMessage("Digite nombre dispocitivo.");
        builder.setView(v)
                // Add action buttons
                .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                       get(getArguments().getString("id"),create.getText().toString());
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        CreateDevice.this.getDialog().cancel();
                    }
                });
        return builder.create();
    }
    public void get(String id,String name){
        Rest_adapter rest_adapter=new Rest_adapter();
        Endpoints endpoints=rest_adapter.establecerConexionRest();
        Call<String> device= endpoints.createDevice(name,id);
        device.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Toast.makeText(v.getContext(),String.valueOf(response.code()),Toast.LENGTH_SHORT).show();
                Toast.makeText(v.getContext(),String.valueOf(response.body()),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });

    }
}
