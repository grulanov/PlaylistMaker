package com.practicum.playlistmaker.presentation.search.viewHolders

import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.ItemSearchActionButtonBinding
import com.practicum.playlistmaker.presentation.search.SearchListItem

class SearchActionButtonViewHolder(private val binding: ItemSearchActionButtonBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: SearchListItem.ActionButtonItem) {
        binding.actionButton.text = model.title
        binding.actionButton.setOnClickListener {
            model.onAction()
        }
    }
}