package com.avvsoft2050.rickandmorty.presentation.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.avvsoft2050.rickandmorty.api.ApiFactory
import com.avvsoft2050.rickandmorty.database.AppDatabase
import com.avvsoft2050.rickandmorty.pojo.LocationResult
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

open class LocationViewModel(application: Application): AndroidViewModel(application) {
    private val db = AppDatabase.getInstance(application)
    private val compositeDisposable = CompositeDisposable()
    val locationResult = db.locationResultDao().getLocationResults()
    val location = db.locationDao().getLocation()

    fun getLocationDetails(locationId: Int): LiveData<LocationResult> {
        return db.locationResultDao().getOneLocationResult(locationId)
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

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }
}