package com.avvsoft2050.rickandmorty.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.avvsoft2050.rickandmorty.pojo.Location

@Dao
interface LocationDao {
    @Query("SELECT * FROM locations")
    fun getLocation(): LiveData<Location>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertLocation(location: Location)

    @Query("DELETE FROM locations")
    fun deleteAllLocations()
}