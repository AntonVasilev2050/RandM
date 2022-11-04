package com.avvsoft2050.rickandmorty.adapters

import androidx.recyclerview.widget.DiffUtil
import com.avvsoft2050.rickandmorty.pojo.LocationResult

class LocationResultsDiffUtil: DiffUtil.ItemCallback<LocationResult>() {
    override fun areItemsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: LocationResult, newItem: LocationResult): Boolean {
        return oldItem == newItem
    }
}