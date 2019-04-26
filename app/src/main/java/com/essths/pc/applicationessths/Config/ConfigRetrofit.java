package com.essths.pc.applicationessths.Config;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by pc on 12/04/2018.
 */

public class ConfigRetrofit {
    public Retrofit getConfig() {
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        final Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl("http://172.16.51.73:4015/")
                //.baseUrl("http://192.168.56.1:4015/")
                //.baseUrl("http://192.168.56.1:4015/")

                /*.baseUrl("http://192.168.56.1:8080/")*/
            //.baseUrl("http://10.0.2.2:4015/")
                .addConverterFactory(GsonConverterFactory.create(gson));

        Retrofit retrofit = builder.build();

        return retrofit;
    }
}
