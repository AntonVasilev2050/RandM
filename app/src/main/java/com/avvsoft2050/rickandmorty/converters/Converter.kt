package com.avvsoft2050.rickandmorty.converters

import androidx.room.TypeConverter
import com.avvsoft2050.rickandmorty.pojo.*
import com.google.gson.Gson

class Converter {

    @TypeConverter
    fun listOfStringsToString(strings: List<String>): String {
        val gson = Gson()
        return gson.toJson(strings)
    }

    @TypeConverter
    fun stringToListOfStrings(string: String): List<String> {
        val gson = Gson()
//        val strings = ArrayList<String>()
//        for (any in listOfAny) {
//            strings.add(gson.fromJson(any.toString(), String::class.java))
//        }
        return gson.fromJson(string, ArrayList::class.java) as List<String>
    }

    @TypeConverter
    fun characterResultsToString(characterResults: List<CharacterResult>): String {
        val gson = Gson()
        return gson.toJson(characterResults)
    }

    @TypeConverter
    fun stringToCharacterResults(string: String): List<CharacterResult> {
        val gson = Gson()
        return gson.fromJson(string, List::class.java) as List<CharacterResult>
    }

    @TypeConverter
    fun characterInfoToString(characterInfo: CharacterInfo): String {
        val gson = Gson()
        return gson.toJson(characterInfo)
    }

    @TypeConverter
    fun stringToCharacterInfo(string: String): CharacterInfo {
        val gson = Gson()
        return gson.fromJson(string, CharacterInfo::class.java)
    }

    @TypeConverter
    fun characterOriginToString(characterOrigin: CharacterOrigin): String {
        val gson = Gson()
        return gson.toJson(characterOrigin)
    }

    @TypeConverter
    fun stringToCharacterOrigin(string: String): CharacterOrigin {
        val gson = Gson()
        return gson.fromJson(string, CharacterOrigin::class.java)
    }

    @TypeConverter
    fun characterLocationToString(characterLocation: CharacterLocation): String {
        val gson = Gson()
        return gson.toJson(characterLocation)
    }

    @TypeConverter
    fun stringToCharacterLocation(string: String): CharacterLocation {
        val gson = Gson()
        return gson.fromJson(string, CharacterLocation::class.java)
    }

    //    Episode
    @TypeConverter
    fun episodeResultsToString(episodeResults: List<EpisodeResult>): String {
        val gson = Gson()
        return gson.toJson(episodeResults)
    }

    @TypeConverter
    fun stringToEpisodeResults(string: String): List<EpisodeResult> {
        val gson = Gson()
        return gson.fromJson(string, List::class.java) as List<EpisodeResult>
    }

    @TypeConverter
    fun episodeInfoToString(episodeInfo: EpisodeInfo): String {
        val gson = Gson()
        return gson.toJson(episodeInfo)
    }

    @TypeConverter
    fun stringToEpisodeInfo(string: String): EpisodeInfo {
        val gson = Gson()
        return gson.fromJson(string, EpisodeInfo::class.java)
    }

    //    Location
    @TypeConverter
    fun locationResultsToString(locationResults: List<LocationResult>): String {
        val gson = Gson()
        return gson.toJson(locationResults)
    }

    @TypeConverter
    fun stringToLocationResults(string: String): List<LocationResult> {
        val gson = Gson()
        return gson.fromJson(string, List::class.java) as List<LocationResult>
    }

    @TypeConverter
    fun locationInfoToString(locationInfo: LocationInfo): String {
        val gson = Gson()
        return gson.toJson(locationInfo)
    }

    @TypeConverter
    fun stringToLocationInfo(string: String): LocationInfo {
        val gson = Gson()
        return gson.fromJson(string, LocationInfo::class.java)
    }
}