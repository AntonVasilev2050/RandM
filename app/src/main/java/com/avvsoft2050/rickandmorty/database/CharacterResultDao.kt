package com.avvsoft2050.rickandmorty.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avvsoft2050.rickandmorty.pojo.CharacterResult
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult
import io.reactivex.Single

@Dao
interface CharacterResultDao {
    @Query("SELECT * FROM character_results ORDER BY id")
    fun getCharacterResults(): LiveData<List<CharacterResult>>

    @Query("SELECT * FROM character_results WHERE id == :requestedId LIMIT 1")
    fun getOneCharacterResult(requestedId: Int): LiveData<CharacterResult>

    @Query("SELECT * FROM character_results WHERE id IN (:ids) ORDER BY id")
    fun getMultipleCharacter(ids: List<String>): LiveData<List<CharacterResult>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacterResults(characterResults: List<CharacterResult>)

    @Query("DELETE FROM character_results")
    fun deleteAllCharacterResult()
}
