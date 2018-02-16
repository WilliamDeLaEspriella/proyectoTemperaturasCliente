package edu.temp.udc.proyectotempe.Vista.Dialog;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
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

import com.google.firebase.iid.FirebaseInstanceId;

import java.util.List;

import edu.temp.udc.proyectotempe.ApiRest.adapter.Endpoints;
import edu.temp.udc.proyectotempe.ApiRest.adapter.Rest_adapter;
import edu.temp.udc.proyectotempe.ApiRest.model.UserDevice;
import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.Vista.LoginActivity;
import edu.temp.udc.proyectotempe.Vista.MainActivity;
import edu.temp.udc.proyectotempe.tempo.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Usuario on 14/11/2017.
 */

public class SignUp extends DialogFragment {
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private EditText mPasswordView2;
    private Button crear;
    private AutoCompleteTextView mName;
    private View v;
    private ProgressBar progressBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater

        LayoutInflater inflater = getActivity().getLayoutInflater();
        v = inflater.inflate(R.layout.activity_sign_up, null);
        mEmailView = (AutoCompleteTextView) v.findViewById(R.id.emailUp);
        mName = (AutoCompleteTextView) v.findViewById(R.id.name);
        mPasswordView = (EditText) v.findViewById(R.id.passwordUp);
        mPasswordView2 = (EditText) v.findViewById(R.id.passwordUp2);
        crear = (Button) v.findViewById(R.id.email_sign_in_buttonUp);
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
        mEmailView.setError(null);
        mPasswordView.setError(null);
        mPasswordView2.setError(null);
        mName.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();
        String password2 = mPasswordView2.getText().toString();
        String name = mName.getText().toString();
        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(name)) {
            mName.setError(getString(R.string.name_error));
            focusView = mName;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        } else if (!isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password2));
            focusView = mPasswordView;
            cancel = true;
        }else if (!password.equals(password2)) {
            mPasswordView2.setError(getString(R.string.no_cohicidencia));
            focusView = mPasswordView2;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            View view = getActivity().getCurrentFocus();
            assert view != null;
            view.clearFocus();
            InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            showProgress(true);
            SignUp();

        }
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@") && email.contains(".");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 7;
    }



    public void SignUp() {
        Rest_adapter rest_adapter = new Rest_adapter();
        Endpoints endpoints = rest_adapter.establecerConexionRest();
        final Call<String> login = endpoints.signUp(mEmailView.getText().toString().trim(), mPasswordView.getText().toString(),mName.getText().toString(), FirebaseInstanceId.getInstance().getToken());
        login.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                switch (response.code()) {
                    case 200:
                        Log.d("astr",response.body());
                        Token.getInstance().setToken(response.body());
                        getNotificacion();
                        break;
                    case 500:
                        showProgress(false);
                        Toast.makeText(v.getContext(),"Email ya registrado. ",Toast.LENGTH_SHORT).show();
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
            mEmailView.setEnabled(false);
            mPasswordView.setEnabled(false);
            mPasswordView2.setEnabled(false);
            mName.setEnabled(false);
            crear.setEnabled(false);

        } else {
            progressBar.setVisibility(View.GONE);
            mEmailView.setEnabled(true);
            mPasswordView.setEnabled(true);
            mPasswordView2.setEnabled(true);
            mName.setEnabled(true);
            crear.setEnabled(true);
        }
    }
    private void getNotificacion() {
        Rest_adapter restApiAdapter = new Rest_adapter();
        Endpoints endponits = restApiAdapter.establecerConexionRest();
        Call<List<UserDevice>> usuarioResponseCall = endponits.getUserDevice();
        usuarioResponseCall.enqueue(new Callback<List<UserDevice>>() {
            @Override
            public void onResponse(Call<List<UserDevice>> call, Response<List<UserDevice>> response) {

                if (response.code() == 200) {
                    Token.getInstance().setUserDevices(response.body());
                    Intent i = new Intent(v.getContext(), MainActivity.class);
                    startActivity(i);
                    dismiss();
                } else {

                    showProgress(false);
                    Toast.makeText(v.getContext(),"Error al cargar Sus datos",Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<List<UserDevice>> call, Throwable t) {

                showProgress(false);
                Toast.makeText(v.getContext(),"Error al cargar Sus datos",Toast.LENGTH_SHORT).show();

            }
        });

    }

}
