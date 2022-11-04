package com.avvsoft2050.rickandmorty.adapters

import androidx.recyclerview.widget.DiffUtil
import com.avvsoft2050.rickandmorty.pojo.CharacterResult

class CharacterResultsDiffUtil : DiffUtil.ItemCallback<CharacterResult>() {
    override fun areItemsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CharacterResult, newItem: CharacterResult): Boolean {
        return oldItem == newItem
    }
}