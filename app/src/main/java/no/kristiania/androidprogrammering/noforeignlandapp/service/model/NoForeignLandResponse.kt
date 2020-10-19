package no.kristiania.androidprogrammering.noforeignlandapp.service.model

import com.google.gson.annotations.SerializedName

class NoForeignLandLocationResponse {
    @SerializedName("features")
    lateinit var locationItems: List <LocationItem>
}

class NoForeignLandLocationInfoResponse {
    @SerializedName("place")
    lateinit var locationDetails: LocationDetails
}

class NoForeignLandLocationGeoResponse {
    @SerializedName("place")
    lateinit var locationLatLon: LocationLatLon
}

