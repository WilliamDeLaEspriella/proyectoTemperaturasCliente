package edu.temp.udc.proyectotempe.ApiRest.adapter;



import java.util.List;

import edu.temp.udc.proyectotempe.ApiRest.model.Dato;
import edu.temp.udc.proyectotempe.ApiRest.model.UserDevice;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by ASTRICHI on 09/11/2017.
 */

public interface Endpoints {

    @FormUrlEncoded
    @POST(ConstantesApi.SIGN_IN_CLIENTE)
    Call<String> signin(@Field("email") String email, @Field("password") String password,@Field("token") String token);

    @FormUrlEncoded
    @POST(ConstantesApi.SIGN_UP_CLIENTE)
    Call<String> signUp(@Field("email") String email, @Field("password") String password,@Field("displayName") String displayName,@Field("token") String token);


    @GET(ConstantesApi.GET_USER_DEVICE)
    Call<List<UserDevice>> getUserDevice();

    @FormUrlEncoded
    @POST(ConstantesApi.CREATE_USER_DEVICE)
    Call<String> createUserDevice(@Field("nombre") String nombre, @Field("apellido") String apellido,@Field("edad") String edad);

    @FormUrlEncoded
    @POST(ConstantesApi.CREATE_EDIT_DEVICE)
    Call<String> editUserDevice(@Field("nombre") String nombre, @Field("apellido") String apellido,@Field("edad") String edad,@Field("id") String is);


    @FormUrlEncoded
    @POST(ConstantesApi.CREATE_DEVICE)
    Call<String> createDevice(@Field("name") String name, @Field("id") String id);

    @FormUrlEncoded
    @POST(ConstantesApi.HISTO)
    Call<List<Dato>> HISTO(@Field("mes") String mes, @Field("year") String year, @Field("dia") String dia, @Field("id") String historial);
    @FormUrlEncoded
    @POST(ConstantesApi.CERRAR)
    Call<String> cerrar(@Field("token") String token);

    @FormUrlEncoded
    @POST(ConstantesApi.CONTRA)
    Call<String> contra(@Field("password") String password);

}
