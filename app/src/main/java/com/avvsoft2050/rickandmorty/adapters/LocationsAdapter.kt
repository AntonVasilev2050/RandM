package com.avvsoft2050.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avvsoft2050.rickandmorty.databinding.ItemLocationBinding
import com.avvsoft2050.rickandmorty.pojo.LocationResult

class LocationsAdapter(
    private val onClickAction: (LocationResult) -> Unit
) : ListAdapter<LocationResult, LocationsAdapter.LocationViewHolder>(LocationResultsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemLocationBinding.inflate(inflater)
        return LocationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LocationViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            onClickAction(item)
        }
    }


    class LocationViewHolder(val binding: ItemLocationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(locationResult: LocationResult) {
            binding.textViewLocationName.text = locationResult.name
            binding.textViewLocationType.text = locationResult.type
            binding.textViewDimension.text = locationResult.dimension
        }
    }
}