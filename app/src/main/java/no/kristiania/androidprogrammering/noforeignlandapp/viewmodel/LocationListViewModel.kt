package no.kristiania.androidprogrammering.noforeignlandapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationProperties
import no.kristiania.androidprogrammering.noforeignlandapp.service.database.LocationPropertiesRepository

class LocationListViewModel : ViewModel () {
    val locationListLiveData: LiveData<List<LocationProperties?>>

    private val placePropertiesRepository =
        LocationPropertiesRepository.get()
    private val mutableSearchTerm = MutableLiveData<String>()

    init {
        mutableSearchTerm.value = "%"
        locationListLiveData = Transformations.switchMap(mutableSearchTerm) { searchTerm ->
            placePropertiesRepository.searchPlaces(searchTerm)
        }
    }

    fun searchPlaces(query: String?){
        mutableSearchTerm.value = "$query%"
    }
}