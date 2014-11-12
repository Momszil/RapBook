package com.momszil.rapbook.Network;

import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by Vedran on 3.10.2014..
 */
public interface RimujMeService {

    @GET("/upit/{upit}")
    String testiramo(@Path("upit") String upit);

}
