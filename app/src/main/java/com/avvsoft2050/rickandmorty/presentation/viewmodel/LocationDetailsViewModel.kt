package com.avvsoft2050.rickandmorty.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.avvsoft2050.rickandmorty.database.AppDatabase
import com.avvsoft2050.rickandmorty.pojo.CharacterResult

class LocationDetailsViewModel(application: Application): LocationViewModel(application) {
    private val db = AppDatabase.getInstance(application)

    fun getMultipleCharacters(ids: List<String>): LiveData<List<CharacterResult>> {
        return db.characterResultDao().getMultipleCharacter(ids)
    }
}