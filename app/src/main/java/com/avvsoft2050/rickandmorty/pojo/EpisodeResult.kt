package com.avvsoft2050.rickandmorty.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avvsoft2050.rickandmorty.converters.Converter
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "episode_results")
@TypeConverters(Converter::class)
data class EpisodeResult(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("air_date")
    @Expose
    val airDate: String?,

    @SerializedName("episode")
    @Expose
    val episode: String?,

    @SerializedName("characters")
    @Expose
    val characters: List<String>?,

    @SerializedName("url")
    @Expose
    val url: String?,

    @SerializedName("created")
    @Expose
    val created: String?
)