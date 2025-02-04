package com.practicum.playlistmaker.ui.search.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.ItemSearchHeaderBinding
import com.practicum.playlistmaker.presentation.search.SearchListItem

class SearchHeaderViewHolder(private val binding: ItemSearchHeaderBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: SearchListItem.HeaderItem) {
        binding.headerTextView.text = model.title
    }
}