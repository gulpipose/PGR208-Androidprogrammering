package no.kristiania.androidprogrammering.noforeignlandapp.service.api

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import no.kristiania.androidprogrammering.noforeignlandapp.service.database.LocationPropertiesRepository
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val TAG = "NoForeignLandClient"

class NoForeignLandRepository {

    private val noForeignLandService: NoForeignLandService

    init {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl("https://noforeignland.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        noForeignLandService = retrofit.create(NoForeignLandService::class.java)
    }


    fun getPlaces() {
        val request: Call<NoForeignLandLocationResponse> = noForeignLandService.getPlaces()

        //Enqueue method asynchronously sends the given request
        request.enqueue(object : Callback<NoForeignLandLocationResponse> {
            override fun onFailure(call: Call<NoForeignLandLocationResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get places", t)
            }

            override fun onResponse(call: Call<NoForeignLandLocationResponse>, placeResponse: Response<NoForeignLandLocationResponse>) {
                val noForeignLandPlaceResponse: NoForeignLandLocationResponse? = placeResponse.body()

                //Extracts the location-info object
                val placeItems: List<LocationItem> = noForeignLandPlaceResponse?.locationItems ?: mutableListOf()
                //Extracts the property object from the location-object
                val placeProperties: List<LocationProperties> = placeItems.map { locationItem -> locationItem.locationProperties }

                //Insert data in database.
                LocationPropertiesRepository.get().insertAll(placeProperties)
            }
        })

    }


    fun getLocationDetails(url: String): LiveData<LocationDetails> {
        val response: MutableLiveData<LocationDetails> = MutableLiveData()
        val request: Call<NoForeignLandLocationInfoResponse> =
            noForeignLandService.getLocationDetails(url)

        //Enqueue method asynchronously sends the given request
        request.enqueue(object : Callback<NoForeignLandLocationInfoResponse> {
            override fun onFailure(call: Call<NoForeignLandLocationInfoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get places", t)
            }

            override fun onResponse(
                call: Call<NoForeignLandLocationInfoResponse>,
                locationResponse: Response<NoForeignLandLocationInfoResponse>
            ) {
                val noForeignLandLocationDetailResponse: NoForeignLandLocationInfoResponse? =
                    locationResponse.body()
                val placeDetails = noForeignLandLocationDetailResponse?.locationDetails
                response.value = placeDetails
            }
        })
        return response
    }

    fun getLocationGeo(url: String): LiveData<LocationLatLon> {
        val response: MutableLiveData<LocationLatLon> = MutableLiveData()
        val request: Call<NoForeignLandLocationGeoResponse> = noForeignLandService.getLocationGeo(url)

        //Enqueue method asynchronously sends the given request
        request.enqueue(object : Callback<NoForeignLandLocationGeoResponse> {
            override fun onFailure(call: Call<NoForeignLandLocationGeoResponse>, t: Throwable) {
                Log.e(TAG, "Failed to get places", t)
            }

            override fun onResponse(
                call: Call<NoForeignLandLocationGeoResponse>,
                locationResponse: Response<NoForeignLandLocationGeoResponse>
            ) {
                val noForeignLandLocationDetailResponse: NoForeignLandLocationGeoResponse? =
                    locationResponse.body()
                val placeDetails = noForeignLandLocationDetailResponse?.locationLatLon
                response.value = placeDetails
            }
        })
        return response
    }
}