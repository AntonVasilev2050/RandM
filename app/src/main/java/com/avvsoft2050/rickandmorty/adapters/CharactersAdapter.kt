package com.avvsoft2050.rickandmorty.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.avvsoft2050.rickandmorty.databinding.ItemCharacterBinding
import com.avvsoft2050.rickandmorty.pojo.CharacterResult
import com.squareup.picasso.Picasso

class CharactersAdapter(
    private val onClickAction: (CharacterResult) -> Unit
) : ListAdapter<CharacterResult, CharactersAdapter.CharactersViewHolder>(CharacterResultsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharactersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCharacterBinding.inflate(inflater)
        return CharactersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CharactersViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.binding.root.setOnClickListener {
            onClickAction(item)
        }
    }

   inner class CharactersViewHolder(val binding: ItemCharacterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(characterResult: CharacterResult) {
            Picasso.get().load(characterResult.image)
                .into(binding.imageViewCharacter)
            binding.textViewCharacterName.text = characterResult.name
            binding.textViewSpecies.text = characterResult.species
            binding.textViewStatus.text = characterResult.status
            binding.textViewGender.text = characterResult.gender
        }
    }
}