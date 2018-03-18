package edu.temp.udc.proyectotempe.Vista.fragment.fragment_usuario.sub_fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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
import edu.temp.udc.proyectotempe.R;
import edu.temp.udc.proyectotempe.Vista.Dialog.AddUserDevice;
import edu.temp.udc.proyectotempe.Vista.fragment.fragment_usuario.AdapterUserDevice;
import edu.temp.udc.proyectotempe.tempo.Token;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
/**
 * Created by haskhell on 3/11/18.
 */

public class Fragment_user extends Fragment implements SwipeRefreshLayout.OnRefreshListener, Observer {
    private View view;
    private RecyclerView recyclerView;
    private ArrayList<UserDevice> userDevices;
    private AdapterUserDevice ca;
    private SwipeRefreshLayout swipeLayout;
    private LinearLayout lineraError;
    private LinearLayout lineraVacio;
    private TextView pro_text;
    private ImageView pro_imagen;
    private ProgressBar pro_progrss;
    private FloatingActionButton agregar;
    private String data;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.fragment_usuarios,container,false);
        recyclerView = (RecyclerView) view.findViewById(R.id.lstListaNotifi);
        agregar = (FloatingActionButton) view.findViewById(R.id.bottonUser);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AddUserDevice dialog = new AddUserDevice();
                dialog.show(getFragmentManager(), "dialog");
            }
        });

        swipeLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipe_container_no);
        swipeLayout.setOnRefreshListener(this);
        swipeLayout.setRefreshing(true);
        //Podemos espeficar si queremos, un patron de colores diferente al patrón por defecto.
        swipeLayout.setColorSchemeResources(
                android.R.color.holo_blue_dark);
        lineraError = (LinearLayout) view.findViewById(R.id.liner_error_no);
        lineraVacio = (LinearLayout) view.findViewById(R.id.liner_vacio_no);
        pro_text = (TextView) view.findViewById(R.id.pro_text_no);
        pro_imagen = (ImageView) view.findViewById(R.id.pro_image_no);
        pro_progrss = (ProgressBar) view.findViewById(R.id.pro_progress_no);
        lineraError.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lineraVacio.setVisibility(View.GONE);
                pro_progrss.setVisibility(View.VISIBLE);
                pro_text.setVisibility(View.GONE);
                pro_imagen.setVisibility(View.GONE);
                getNotificacion();

            }
        });
        addRecyclerView();
        getNotificacion();
        Token.getInstance().addObserver(this);

        return view;
    }
    private void addRecyclerView() {
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(),2));
        userDevices = new ArrayList<>();
        ca = new AdapterUserDevice(view.getContext(), userDevices);
        recyclerView.setAdapter(ca);
        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                if (newState > 0) {
                    agregar.hide();
                } else {
                    agregar.show();
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
//                if(dy>0){
//                    fab.hide();
//                }else{
//                    fab.show();
//                }
            }
        });

        ca.notifyDataSetChanged();
    }

    private void getNotificacion() {
        Rest_adapter restApiAdapter = new Rest_adapter();
        Endpoints endponits = restApiAdapter.establecerConexionRest();
        Call<List<UserDevice>> usuarioResponseCall = endponits.getUserDevice();
        usuarioResponseCall.enqueue(new Callback<List<UserDevice>>() {
            @Override
            public void onResponse(Call<List<UserDevice>> call, Response<List<UserDevice>> response) {
                swipeLayout.setRefreshing(false);
                lineraError.setVisibility(View.GONE);
                if (response.code()==200) {

                    Token.getInstance().setUserDevices(response.body());
                    if (response.body().isEmpty()) {
                        lineraVacio.setVisibility(View.VISIBLE);
                        userDevices.clear();
                    } else {
                        lineraVacio.setVisibility(View.GONE);
                        userDevices.clear();
                        userDevices.addAll(response.body());
                        ca.notifyDataSetChanged();
                        lineraError.setVisibility(View.GONE);
                    }
                } else {
                    lineraVacio.setVisibility(View.GONE);
                    userDevices.clear();
                    userDevices.addAll(response.body());
                    ca.notifyDataSetChanged();
                    lineraError.setVisibility(View.GONE);
                }

            }

            @Override
            public void onFailure(Call<List<UserDevice>> call, Throwable t) {
                swipeLayout.setRefreshing(false);
                if (userDevices.isEmpty()) {

                    lineraVacio.setVisibility(View.GONE);
                    lineraError.setVisibility(View.VISIBLE);
                    pro_progrss.setVisibility(View.GONE);
                    pro_text.setVisibility(View.VISIBLE);
                    pro_imagen.setVisibility(View.VISIBLE);

                    Toast.makeText(view.getContext(), "Error al cargar Sus Procesos" + t.getMessage(), Toast.LENGTH_SHORT).show();
                } else {

                    Toast.makeText(view.getContext(), "Error al cargar Sus Procesos" + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    @Override
    public void onRefresh() {
        getNotificacion();
    }

    @Override
    public void update(Observable o, final Object arg) {
        getNotificacion();
           /* String data=(String) arg;
        this.data=data;
        SimpleTask
                simpleTask= new SimpleTask();
        simpleTask.execute();*/


    }
    class SimpleTask extends AsyncTask<Void, Integer, Void> {

        /*
        Se hace visible el botón "Cancelar" y se desactiva
        el botón "Ordenar"
         */
        @Override
        protected void onPreExecute() {
        }

        /*
        Ejecución del ordenamiento y transmision de progreso
         */
        @Override
        protected Void doInBackground(Void... params) {
            UserDevice aux = null;
            String dato[] = data.split(" ");
            for (int i = 0; i < userDevices.size(); i++) {
                if (userDevices.get(i).getDevice().equals(dato[1])) {
                    aux = new UserDevice(userDevices.get(i).get_id(), userDevices.get(i).getNombre(), userDevices.get(i).getApellido(), userDevices.get(i).getEdad(), userDevices.get(i).getHistorial(), userDevices.get(i).getDevice(), dato[0]);
                    userDevices.remove(i);
                }
            }
            userDevices.clear();
            userDevices.add(0, aux);
            ca.updateData(userDevices);
            return null;
        }

        /*
         Se informa en progressLabel que se canceló la tarea y
         se hace invisile el botón "Cancelar"
          */
        @Override
        protected void onCancelled() {
            super.onCancelled();
        }

        /*
        Impresión del progreso en tiempo real
          */
        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
        }

        /*
        Se notifica que se completó el ordenamiento y se habilita
        de nuevo el botón "Ordenar"
         */
        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);

        }
    }
}
