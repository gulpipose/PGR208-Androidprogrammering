package no.kristiania.androidprogrammering.noforeignlandapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import no.kristiania.androidprogrammering.noforeignlandapp.service.api.NoForeignLandRepository
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationLatLon


class LocationLatLonViewModel : ViewModel() {
    lateinit var locationDetailsLiveData: LiveData<LocationLatLon>

    fun loadPlace(url: String){
        this.locationDetailsLiveData = NoForeignLandRepository().getLocationGeo(url)
    }
}