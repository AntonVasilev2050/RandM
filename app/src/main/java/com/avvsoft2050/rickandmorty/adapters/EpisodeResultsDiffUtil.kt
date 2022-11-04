package com.avvsoft2050.rickandmorty.adapters

import androidx.recyclerview.widget.DiffUtil
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult

class EpisodeResultsDiffUtil : DiffUtil.ItemCallback<EpisodeResult>(){
    override fun areItemsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: EpisodeResult, newItem: EpisodeResult): Boolean {
        return oldItem == newItem
    }
}