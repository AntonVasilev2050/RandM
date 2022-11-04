package com.avvsoft2050.rickandmorty.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avvsoft2050.rickandmorty.pojo.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getCharacter(): LiveData<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCharacter(character : Character)

    @Query("DELETE FROM characters")
    fun deleteAllCharacters()
}