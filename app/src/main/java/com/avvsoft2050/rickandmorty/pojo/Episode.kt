package com.avvsoft2050.rickandmorty.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avvsoft2050.rickandmorty.converters.Converter
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "episodes")
@TypeConverters(Converter::class)
data class Episode (
    @PrimaryKey
    val episodeId: Int = 1,

    @SerializedName("info")
    @Expose
    val episodeInfo: EpisodeInfo?,

    @SerializedName("results")
    @Expose
    val episodeResults: List<EpisodeResult>
)