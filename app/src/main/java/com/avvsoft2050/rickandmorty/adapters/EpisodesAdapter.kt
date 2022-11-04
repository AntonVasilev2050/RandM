package com.avvsoft2050.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avvsoft2050.rickandmorty.databinding.ItemEpisodeBinding
import com.avvsoft2050.rickandmorty.pojo.EpisodeResult

class EpisodesAdapter(
    private val onClickAction: (EpisodeResult) -> Unit
) : ListAdapter<EpisodeResult, EpisodesAdapter.EpisodeViewHolder>(EpisodeResultsDiffUtil()) {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EpisodeViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemEpisodeBinding.inflate(inflater)
        return EpisodeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: EpisodeViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            onClickAction(item)
        }
    }

    class EpisodeViewHolder(val binding: ItemEpisodeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(episodeResult: EpisodeResult) {
            binding.textViewEpisodeName.text = episodeResult.name
            binding.textViewEpisode.text = episodeResult.episode
            binding.textViewAirDate.text = episodeResult.airDate
        }
    }
}