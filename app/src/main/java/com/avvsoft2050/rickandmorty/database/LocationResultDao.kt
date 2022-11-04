package com.avvsoft2050.rickandmorty.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avvsoft2050.rickandmorty.pojo.LocationResult

@Dao
interface LocationResultDao {
    @Query("SELECT * FROM location_results ORDER BY id")
    fun getLocationResults(): LiveData<List<LocationResult>>

    @Query("SELECT * FROM location_results WHERE id == :requestedId")
    fun getOneLocationResult(requestedId: Int): LiveData<LocationResult>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocationResults(locationResults: List<LocationResult>)

    @Query("DELETE FROM location_results")
    fun deleteAllLocationResult()
}