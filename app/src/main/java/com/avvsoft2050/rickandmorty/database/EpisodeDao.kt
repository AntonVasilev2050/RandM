package com.avvsoft2050.rickandmorty.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avvsoft2050.rickandmorty.pojo.Episode

@Dao
interface EpisodeDao {
    @Query("SELECT * FROM episodes")
    fun getEpisode(): LiveData<Episode>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertEpisode(episode: Episode)

    @Query("DELETE FROM episodes")
    fun deleteAllEpisodes()
}
