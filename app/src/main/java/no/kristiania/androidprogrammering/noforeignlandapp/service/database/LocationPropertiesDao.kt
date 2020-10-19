package no.kristiania.androidprogrammering.noforeignlandapp.service.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import no.kristiania.androidprogrammering.noforeignlandapp.service.model.LocationProperties

@Dao
interface LocationPropertiesDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(locationProperties: List<LocationProperties>)

    @Insert
    fun insert(locationProperties: LocationProperties)

    @Query("SELECT * FROM LocationProperties")
    fun getPlaces(): LiveData<List<LocationProperties>>

    @Query("SELECT * FROM LocationProperties WHERE name LIKE :search")
    fun searchPlaces(search:String): LiveData<List<LocationProperties?>>
}