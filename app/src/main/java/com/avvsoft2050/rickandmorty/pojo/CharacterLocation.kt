package com.avvsoft2050.rickandmorty.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class CharacterLocation(
    @SerializedName("name")
    @Expose
    val name: String?,

    @SerializedName("url")
    @Expose
    val url: String
)