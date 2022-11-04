package com.avvsoft2050.rickandmorty.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult

@Dao
interface EpisodeResultDao {
    @Query("SELECT * FROM episode_results ORDER BY id")
    fun getEpisodeResults(): LiveData<List<EpisodeResult>>

    @Query("SELECT * FROM episode_results WHERE id == :requestedId LIMIT 1" )
    fun getOneEpisodeResult(requestedId: Int):LiveData<EpisodeResult>

    @Query("SELECT * FROM episode_results WHERE id IN (:ids) ORDER BY id")
    fun getMultipleEpisode(ids: List<String>): LiveData<List<EpisodeResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisodeResults(episodeResults: List<EpisodeResult>)

    @Query("DELETE FROM episode_results")
    fun deleteAllEpisodeResult()
}