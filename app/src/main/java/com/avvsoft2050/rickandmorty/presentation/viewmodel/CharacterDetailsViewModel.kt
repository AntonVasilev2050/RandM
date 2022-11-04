package com.avvsoft2050.rickandmorty.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import com.avvsoft2050.rickandmorty.database.AppDatabase
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult
import com.avvsoft2050.rickandmorty.presentation.viewmodel.CharacterViewModel

class CharacterDetailsViewModel(application: Application): CharacterViewModel(application) {
    private val db = AppDatabase.getInstance(application)

    fun getMultipleEpisode(ids: List<String>): LiveData<List<EpisodeResult>> {
        return db.episodeResultDao().getMultipleEpisode(ids)
    }
}