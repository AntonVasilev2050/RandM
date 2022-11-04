package com.avvsoft2050.rickandmorty.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avvsoft2050.rickandmorty.converters.Converter
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "location_results")
@TypeConverters(Converter::class)
data class LocationResult(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("type")
    @Expose
    val type: String?,

    @SerializedName("dimension")
    @Expose
    val dimension: String?,

    @SerializedName("residents")
    @Expose
    val residents: List<String>?,

    @SerializedName("url")
    @Expose
    val url: String?,

    @SerializedName("created")
    @Expose
    val created: String?
)