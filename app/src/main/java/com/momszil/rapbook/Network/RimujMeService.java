package com.momszil.rapbook.Network;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.Path;

/**
 * Created by Vedran on 3.10.2014..
 */
public interface RimujMeService {

    @Headers({"Accept: application/json"})
    @GET("/upit/{upit}")
    Response testiramo(@Path("upit") String upit);

}
