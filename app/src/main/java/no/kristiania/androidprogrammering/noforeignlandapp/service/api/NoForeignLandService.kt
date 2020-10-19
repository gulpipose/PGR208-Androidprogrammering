package no.kristiania.androidprogrammering.noforeignlandapp.service.api

import no.kristiania.androidprogrammering.noforeignlandapp.service.model.NoForeignLandLocationGeoResponse
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.NoForeignLandLocationInfoResponse
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.NoForeignLandLocationResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface NoForeignLandService {
    @GET("/home/api/v1/places/")
    fun getPlaces(): Call<NoForeignLandLocationResponse>

    @GET
    fun getLocationDetails(@Url url: String): Call<NoForeignLandLocationInfoResponse>

    @GET
    fun getLocationGeo(@Url url: String): Call<NoForeignLandLocationGeoResponse>

}