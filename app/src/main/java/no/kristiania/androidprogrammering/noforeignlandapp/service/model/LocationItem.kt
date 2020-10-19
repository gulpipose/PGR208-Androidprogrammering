package no.kristiania.androidprogrammering.noforeignlandapp.service.model

import com.google.gson.annotations.SerializedName


data class LocationItem (
    @SerializedName("properties")
    var locationProperties: LocationProperties
)
