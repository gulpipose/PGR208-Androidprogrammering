package no.kristiania.androidprogrammering.noforeignlandapp

import android.app.Application
import no.kristiania.androidprogrammering.noforeignlandapp.service.database.LocationPropertiesRepository

class NoForeignLand : Application() {

    override fun onCreate(){
        super.onCreate()

        //Initializes the database repository when App is loaded into memory
        LocationPropertiesRepository.initialize(this)
    }
}