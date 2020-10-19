package no.kristiania.androidprogrammering.noforeignlandapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import no.kristiania.androidprogrammering.noforeignlandapp.service.api.NoForeignLandRepository
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationDetails


class LocationDetailsViewModel : ViewModel() {
    lateinit var locationInformationLiveData: LiveData<LocationDetails>

    fun loadPlace(url: String){
        this.locationInformationLiveData = NoForeignLandRepository().getLocationDetails(url)
    }
}