package com.avvsoft2050.rickandmorty.pojo

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.avvsoft2050.rickandmorty.converters.Converter
import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

@Entity(tableName = "characters")
@TypeConverters(Converter::class)
data class Character (
    @PrimaryKey
    val characterId: Int = 1,

    @SerializedName("info")
    @Expose
     val characterInfo: CharacterInfo?,

    @SerializedName("results")
    @Expose
     val characterResults: List<CharacterResult>
)
