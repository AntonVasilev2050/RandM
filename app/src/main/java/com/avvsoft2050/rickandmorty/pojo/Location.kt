package com.avvsoft2050.rickandmorty.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avvsoft2050.rickandmorty.converters.Converter
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "locations")
@TypeConverters(Converter::class)
data class Location(
    @PrimaryKey
    val locationId: Int = 1,

    @SerializedName("info")
    @Expose
    val locationInfo: LocationInfo?,

    @SerializedName("results")
    @Expose
    val locationResults: List<LocationResult>
)