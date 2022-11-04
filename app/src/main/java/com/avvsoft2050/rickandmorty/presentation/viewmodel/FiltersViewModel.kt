package com.avvsoft2050.rickandmorty.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.avvsoft2050.rickandmorty.api.ApiFactory
import com.avvsoft2050.rickandmorty.database.AppDatabase
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.cancel

class FiltersViewModel(application: Application) : AndroidViewModel(application){

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    fun deleteCharacterResult(){
        db.characterResultDao().deleteAllCharacterResult()
    }

    fun deleteLocationResult(){
        db.locationResultDao().deleteAllLocationResult()
    }

    fun deleteEpisodeResult(){
        db.episodeResultDao().deleteAllEpisodeResult()
    }

    fun loadCharacterData(
        characterPage: String,
        characterName: String,
        characterStatus: String,
        characterSpecies: String,
        characterType: String,
        characterGender: String
    ) {
        val disposable = ApiFactory.apiService.getCharacters(
            charactersPage = characterPage,
            characterName = characterName,
            characterStatus = characterStatus,
            characterSpecies = characterSpecies,
            characterType = characterType,
            characterGender = characterGender
        )
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.characterDao().insertCharacter(it)
                db.characterResultDao().insertCharacterResults(it.characterResults)
            }, {
                Log.d("TEST_OF_LOADING_DATA", it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    fun loadLocationData(
        locationPage: String,
        locationName: String,
        locationType: String,
        locationDimension: String
    ) {
        val disposable = ApiFactory.apiService.getLocations(
            locationPage = locationPage,
            locationName = locationName,
            locationType = locationType,
            locationDimension = locationDimension
        )
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.locationResultDao().insertLocationResults(it.locationResults)
                db.locationDao().insertLocation(it)
            }, {
                Log.d("TEST_OF_LOADING_DATA", it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    fun loadEpisodeData(
        episodePage: String,
        episodeName: String,
        episodeCode: String
    ) {
        val disposable = ApiFactory.apiService.getEpisodes(
            episodePage = episodePage,
            episodeName = episodeName,
            episodeCode = episodeCode
        )
            .subscribeOn(Schedulers.io())
            .subscribe({
                db.episodeDao().insertEpisode(it)
                db.episodeResultDao().insertEpisodeResults(it.episodeResults)
            }, {
                Log.d("TEST_OF_LOADING_DATA", it.message.toString())
            })
        compositeDisposable.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        viewModelScope.cancel()
        compositeDisposable.dispose()
    }
}