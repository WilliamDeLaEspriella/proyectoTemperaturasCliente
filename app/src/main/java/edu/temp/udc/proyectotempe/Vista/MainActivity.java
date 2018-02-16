package edu.temp.udc.proyectotempe.Vista;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.iid.FirebaseInstanceId;

import java.io.Serializable;

import edu.temp.udc.proyectotempe.ApiRest.adapter.Endpoints;
import edu.temp.udc.proyectotempe.ApiRest.adapter.Rest_adapter;
import edu.temp.udc.proyectotempe.Vista.Dialog.CerrarDevice;
import edu.temp.udc.proyectotempe.Vista.Dialog.CreateDevice;
import edu.temp.udc.proyectotempe.Vista.Dialog.Dialog_contra;
import edu.temp.udc.proyectotempe.Vista.Dialog.SignUp;
import edu.temp.udc.proyectotempe.Vista.fragment.fragment_acerca_de.Fragment_acerca_de;
import edu.temp.udc.proyectotempe.tempo.Token;
import edu.temp.udc.proyectotempe.util.BottomNavigationViewHelper;
import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.Vista.fragment.fragment_inicio.Fragment_inicio;
import edu.temp.udc.proyectotempe.Vista.fragment.fragment_usuario.Fragment_usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_Inicio:
                    Bundle args = new Bundle();
                    //toolbar.setVisibility(View.VISIBLE);
                    //cmbToolbar.setVisibility(View.VISIBLE);
                    Fragment_inicio fragment = new Fragment_inicio();
                    if (!Token.getInstance().getUserDevices().isEmpty()) {
                        // Colocamos el String
                        args.putSerializable("object", (Serializable) Token.getInstance().getUserDevices().get(0));

                        fragment.setArguments(args);
                    }
                    fragmentTransaction.replace(R.id.content, fragment);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
                case R.id.navigation_usuario:
                    //toolbar.setVisibility(View.VISIBLE);
                    //cmbToolbar.setVisibility(View.GONE);
                    Fragment_usuario f = new Fragment_usuario();
                    fragmentTransaction.replace(R.id.content, f);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
               /* case R.id.navigation_personal:
               cmbToolbar.setVisibility(View.GONE);
                    mTextMessage.setText(R.string.title_notifications);
                    return true;*/
                case R.id.navigation_acerca:
                    //toolbar.setVisibility(View.GONE);
                    Fragment_acerca_de f2 = new Fragment_acerca_de();
                    fragmentTransaction.replace(R.id.content, f2);
                    fragmentTransaction.addToBackStack(null);
                    fragmentTransaction.commit();
                    return true;
            }
            return true;
        }

    };
    private Toolbar toolbar;
    //private Spinner cmbToolbar;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
      toolbar = (Toolbar) findViewById(R.id.toolbar_recycler_view);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.app_name));
        String[] names;

        //Appbar page filter

       /* cmbToolbar = (Spinner) findViewById(R.id.CmbToolbar);
        names = new String[Token.getInstance().getUserDevices().size()];
        for (int i = 0; i < names.length; i++) {
            names[i] = Token.getInstance().getUserDevices().get(i).getNombre();
        }
        ArrayAdapter<String> adapter;
        if (Token.getInstance().getUserDevices().size() != 0) {
            adapter = new ArrayAdapter<>(
                    getSupportActionBar().getThemedContext(),
                    R.layout.appbar_filter_title, names);
        } else {
            adapter = new ArrayAdapter<>(
                    getSupportActionBar().getThemedContext(),
                    R.layout.appbar_filter_title, new String[]{"No hay Usuarios"});
        }

        adapter.setDropDownViewResource(R.layout.appbar_filter_list);

        cmbToolbar.setAdapter(adapter);
        cmbToolbar.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                // Colocamos el String
                Fragment_inicio fragment = new Fragment_inicio();
                if (!Token.getInstance().getUserDevices().isEmpty()) {
                    Bundle args = new Bundle();
                    args.putSerializable("object", (Serializable) Token.getInstance().getUserDevices().get(0));
                    fragment.setArguments(args);
                }

                fragmentTransaction.replace(R.id.content, fragment);
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.commit();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                //... Acciones al no existir ningún elemento seleccionado
            }
        });*/

        /*Window window = getWindow();
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        // getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.argb(33, 0, 0, 0));*/
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        // Colocamos el String
        Fragment_inicio fragment = new Fragment_inicio();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        BottomNavigationViewHelper.disableShiftMode(navigation);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.game_menu, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.create_new:
                Dialog_contra dialog = new Dialog_contra();
                dialog.show(getSupportFragmentManager(), "dialog");
                return true;
            case R.id.open:
                AlertDialog.Builder d = new AlertDialog.Builder(this);
                d.setIcon(R.drawable.ic_close_black_24dp);
                d.setMessage("¿Deseas cerrar sesión?");
                d.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        cerrarSesion();
                        CerrarDevice dialog = new CerrarDevice();
                        dialog.show(getFragmentManager(), "dialog");
                    }
                });
                d.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                d.create();
                d.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    @Override
    public void onBackPressed() {
            AlertDialog.Builder d = new AlertDialog.Builder(this);
            d.setTitle(R.string.app_name);
            d.setIcon(R.drawable.ic_warning_black_24dp);
            d.setMessage("¿Deseas salir?");
            d.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    finish();
                }
            });
            d.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                }
            });
            d.create();
            d.show();

    }
    public void cerrarSesion(){
        Rest_adapter rest_adapter=new Rest_adapter();
        Endpoints endpoints=rest_adapter.establecerConexionRest();
        Call<String> rer= endpoints.cerrar(FirebaseInstanceId.getInstance().getToken());
        rer
                .enqueue(new Callback<String>() {
                    @Override
                    public void onResponse(Call<String> call, Response<String> response) {
                        if(response.code()==200){
                            Toast.makeText(MainActivity.this,response.body(),Toast.LENGTH_SHORT);
                            SharedPreferences prefs =
                                    getSharedPreferences("MisPreferencias", Context.MODE_PRIVATE);

                            SharedPreferences.Editor editor = prefs.edit();
                            editor.putString("email", " ");
                            editor.putString("contra", " ");
                            editor.apply();
                            Intent i=new Intent(MainActivity.this,LoginActivity.class);
                            startActivity(i);
                            finish();
                        }
                    }

                    @Override
                    public void onFailure(Call<String> call, Throwable t) {
                        Toast.makeText(MainActivity.this,"verifique su conexion",Toast.LENGTH_SHORT);
                    }
                });
    }
}
