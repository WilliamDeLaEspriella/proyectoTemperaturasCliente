package edu.temp.udc.proyectotempe.ApiRest.adapter;

import java.io.IOException;

import edu.temp.udc.proyectotempe.tempo.Token;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by ASTRICHI on 09/11/2017.
 */

public class Rest_adapter {

    public Endpoints establecerConexionRest() {
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                if (Token.getInstance().equals(" ")) {
                    Request request = original.newBuilder()
                            .header("Content-Type", "application/x-www-form-urlencoded")
                            .method(original.method(), original.body())
                            .build();

                    return chain.proceed(request);
                }
                Request request = original.newBuilder()
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .header("authorization", Token.getInstance().getToken())
                        .method(original.method(), original.body())
                        .build();

                return chain.proceed(request);
            }
        });

        OkHttpClient client = httpClient.build();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstantesApi.URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        return retrofit.create(Endpoints.class);

    }

}
