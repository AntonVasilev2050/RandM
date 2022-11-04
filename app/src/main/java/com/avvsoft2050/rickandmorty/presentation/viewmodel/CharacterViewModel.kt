package com.avvsoft2050.rickandmorty.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.avvsoft2050.rickandmorty.api.ApiFactory
import com.avvsoft2050.rickandmorty.database.AppDatabase
import com.avvsoft2050.rickandmorty.pojo.CharacterResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class CharacterViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val character = db.characterDao().getCharacter()
    val characterResults = db.characterResultDao().getCharacterResults()

    fun getCharacterDetails(characterId: Int): LiveData<CharacterResult> {
        return db.characterResultDao().getOneCharacterResult(characterId)
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}