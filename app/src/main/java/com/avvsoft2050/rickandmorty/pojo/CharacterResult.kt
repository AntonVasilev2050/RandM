package com.avvsoft2050.rickandmorty.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avvsoft2050.rickandmorty.converters.Converter
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "character_results")
@TypeConverters(Converter::class)
data class CharacterResult(
    @PrimaryKey
    @SerializedName("id")
    @Expose
    val id: Int,

    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("status")
    @Expose
    val status: String?,

    @SerializedName("species")
    @Expose
    val species: String?,

    @SerializedName("type")
    @Expose
    val type: String?,

    @SerializedName("gender")
    @Expose
    val gender: String?,

    @SerializedName("origin")
    @Expose
    val characterOrigin: CharacterOrigin,

    @SerializedName("location")
    @Expose
    val characterLocation: CharacterLocation,

    @SerializedName("image")
    @Expose
    val image: String?,

    @SerializedName("episode")
    @Expose
    val episode: List<String>?,

    @SerializedName("url")
    @Expose
    val url: String?,

    @SerializedName("created")
    @Expose
    val created: String?
)