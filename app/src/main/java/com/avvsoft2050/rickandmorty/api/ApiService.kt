package com.avvsoft2050.rickandmorty.api

import com.avvsoft2050.rickandmorty.pojo.*
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("character/")
     fun getCharacters(
        @Query(QUERY_PARAM_PAGE) charactersPage: String = "",
        @Query(QUERY_PARAM_NAME) characterName: String = "",
        @Query(QUERY_PARAM_STATUS) characterStatus: String = "",
        @Query(QUERY_PARAM_SPECIES) characterSpecies: String = "",
        @Query(QUERY_PARAM_TYPE) characterType: String = "",
        @Query(QUERY_PARAM_GENDER) characterGender: String = ""
     ): Single<Character>

     @GET("character/{ids}")
     fun getMultipleCharacters(
         @Path("ids") ids: String = ""
     ):Single<MutableList<CharacterResult>>

     @GET("episode/")
     fun getEpisodes(
         @Query(QUERY_PARAM_PAGE) episodePage: String = "",
         @Query(QUERY_PARAM_NAME) episodeName: String = "",
         @Query(QUERY_PARAM_EPISODE_CODE) episodeCode: String = ""
     ): Single<Episode>

     @GET("episode/{ids}")
     fun getMultipleEpisodes(
         @Path("ids") ids: String = ""
     ): Single<MutableList<EpisodeResult>>

     @GET("location/")
     fun getLocations(
         @Query(QUERY_PARAM_PAGE) locationPage: String = "",
         @Query(QUERY_PARAM_NAME) locationName: String = "",
         @Query(QUERY_PARAM_TYPE) locationType: String = "",
         @Query(QUERY_PARAM_LOCATION_DIMENSION) locationDimension: String = ""
     ): Single<Location>

    @GET("location/{ids}")
    fun getMultipleLocations(
        @Path("ids") ids: String = ""
    ): Single<MutableList<LocationResult>>

     companion object{
         private const val QUERY_PARAM_PAGE = "page"
         private const val QUERY_PARAM_NAME = "name"
         private const val QUERY_PARAM_STATUS = "status"
         private const val QUERY_PARAM_SPECIES = "species"
         private const val QUERY_PARAM_TYPE = "type"
         private const val QUERY_PARAM_GENDER = "gender"

         private const val QUERY_PARAM_EPISODE_CODE = "episode"

         private const val QUERY_PARAM_LOCATION_DIMENSION = "dimension"
     }
}