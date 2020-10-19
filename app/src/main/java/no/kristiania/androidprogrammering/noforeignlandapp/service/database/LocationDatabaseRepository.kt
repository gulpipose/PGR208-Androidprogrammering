package no.kristiania.androidprogrammering.noforeignlandapp.service.database

import android.content.Context
import android.os.AsyncTask
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.room.Room
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationProperties
import java.lang.IllegalStateException

private const val DATABASE_NAME: String = "location_database"
private const val TAG: String = "LocationRepository"

class LocationPropertiesRepository private constructor(context: Context) {

    private val database : LocationDatabase = Room.databaseBuilder(
                    context.applicationContext,
                    LocationDatabase::class.java,
        DATABASE_NAME
                ).build()

    private val placePropertiesDao = database.placePropertiesDao()

    fun insertAll(locationList: List<LocationProperties>) {
        AsyncTask.execute{
            placePropertiesDao.insertAll(locationList)
        }
    }

    fun getPlaces(): LiveData<List<LocationProperties>> = placePropertiesDao.getPlaces()

    fun searchPlaces(search: String): LiveData<List<LocationProperties?>> {
          return placePropertiesDao.searchPlaces(search)
    }

    companion object {
        private var INSTANCE: LocationPropertiesRepository? = null

        fun initialize(context: Context) {
            if(INSTANCE == null){
                INSTANCE =
                    LocationPropertiesRepository(
                        context
                    )
            }
            Log.d(TAG, "repository initialized")
        }
        fun get(): LocationPropertiesRepository {
            return INSTANCE
                ?: throw IllegalStateException("PlaceRepository not initialized")
        }
    }
}