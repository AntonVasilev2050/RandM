package com.avvsoft2050.rickandmorty.pojo

import com.google.gson.annotations.Expose

import com.google.gson.annotations.SerializedName

data class LocationInfo(
    @SerializedName("count")
    @Expose
    val count: Int?,

    @SerializedName("pages")
    @Expose
    val pages: Int?,

    @SerializedName("next")
    @Expose
    val next: String?,

    @SerializedName("prev")
    @Expose
    val prev: String?
)