package com.avvsoft2050.rickandmorty.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.avvsoft2050.rickandmorty.api.ApiFactory
import com.avvsoft2050.rickandmorty.database.AppDatabase
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class EpisodeViewModel(application: Application) : AndroidViewModel(application) {

    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()

    val episodeResults = db.episodeResultDao().getEpisodeResults()
    val episode = db.episodeDao().getEpisode()

    fun getEpisodeDetails(episodeId: Int): LiveData<EpisodeResult> {
        return db.episodeResultDao().getOneEpisodeResult(episodeId)
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
        compositeDisposable.dispose()
    }
}