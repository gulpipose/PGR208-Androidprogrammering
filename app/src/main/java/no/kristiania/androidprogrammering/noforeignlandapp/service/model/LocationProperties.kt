package no.kristiania.androidprogrammering.noforeignlandapp.service.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity
data class LocationProperties(
    @PrimaryKey var id: String = "",
    var name: String = ""
)