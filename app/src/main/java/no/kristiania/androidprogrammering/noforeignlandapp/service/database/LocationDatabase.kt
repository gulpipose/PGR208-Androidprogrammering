package no.kristiania.androidprogrammering.noforeignlandapp.service.database

import androidx.room.Database
import androidx.room.RoomDatabase
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationProperties

private const val DATABASE_NAME = "location_database"

@Database(entities = [ LocationProperties::class  ], version = 1)
abstract class LocationDatabase : RoomDatabase() {
    abstract fun placePropertiesDao(): LocationPropertiesDao

}
