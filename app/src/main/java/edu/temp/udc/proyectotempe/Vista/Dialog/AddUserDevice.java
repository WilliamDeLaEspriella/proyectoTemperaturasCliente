package edu.temp.udc.proyectotempe.Vista.Dialog;


import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.app.AlertDialog;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import edu.temp.udc.proyectotempe.ApiRest.adapter.Endpoints;
import edu.temp.udc.proyectotempe.ApiRest.adapter.Rest_adapter;
import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.Vista.MainActivity;
import edu.temp.udc.proyectotempe.tempo.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 16/11/2017.
 */

public class AddUserDevice extends DialogFragment {
    private AutoCompleteTextView mNombre;
    private EditText mApellido;
    private Button crear;
    private EditText mEdad;
    private View v;
    private ProgressBar progressBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.dialog_add_user_device, null);
        mNombre = (AutoCompleteTextView) v.findViewById(R.id.nombre);
        mEdad = (EditText) v.findViewById(R.id.edad);
        mApellido = (EditText) v.findViewById(R.id.apellido);
        crear = (Button) v.findViewById(R.id.crear_User);
        progressBar = (ProgressBar) v.findViewById(R.id.progresUp);
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });
        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(v);

        return builder.create();
    }

    private void attemptLogin() {

        // Reset errors.
        mNombre.setError(null);
        mApellido.setError(null);
        mEdad.setError(null);

        // Store values at the time of the login attempt.
        String nombre = mNombre.getText().toString();
        String apellido = mApellido.getText().toString();
        String edad = mEdad.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(nombre)) {
            mNombre.setError(getString(R.string.name_error));
            focusView = mNombre;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(apellido)) {
            mApellido.setError(getString(R.string.error_invalid_password));
            focusView = mApellido;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(edad)) {
            mEdad.setError(getString(R.string.error_field_required));
            focusView = mEdad;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {


            showProgress(true);
            SignUp();

        }
    }





    public void SignUp() {
        Rest_adapter rest_adapter = new Rest_adapter();
        Endpoints endpoints = rest_adapter.establecerConexionRest();
        final Call<String> login = endpoints.createUserDevice(mNombre.getText().toString().trim(), mApellido.getText().toString(),mEdad.getText().toString());
        login.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        Toast.makeText(v.getContext(),response.body(),Toast.LENGTH_SHORT).show();
                        dismiss();
                        break;
                    case 500:
                        showProgress(false);
                        Toast.makeText(v.getContext(),"error al registrar usuario. ",Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        showProgress(false);
                        Toast.makeText(v.getContext(),"Ups...lo sentimos el servidor no responde,favor intente mas tarde. ",Toast.LENGTH_SHORT).show();
                        break;

                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                showProgress(false);
                Toast.makeText(v.getContext(),"Favor revise su conexion.",Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void showProgress(boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (show) {
            progressBar.setVisibility(View.VISIBLE);
            mNombre.setEnabled(false);
            mApellido.setEnabled(false);
            mEdad.setEnabled(false);
            crear.setEnabled(false);

        } else {
            progressBar.setVisibility(View.GONE);
            mNombre.setEnabled(true);
            mApellido.setEnabled(true);
            mEdad.setEnabled(true);
            crear.setEnabled(true);
        }
    }

}
