package com.practicum.playlistmaker.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.databinding.ItemSearchActionButtonBinding
import com.practicum.playlistmaker.databinding.ItemSearchHeaderBinding
import com.practicum.playlistmaker.logic.domainModels.Track
import com.practicum.playlistmaker.databinding.ItemSearchTrackBinding
import com.practicum.playlistmaker.presentation.search.viewHolders.SearchActionButtonViewHolder
import com.practicum.playlistmaker.presentation.search.viewHolders.SearchHeaderViewHolder
import com.practicum.playlistmaker.presentation.search.viewHolders.SearchSpacingViewHolder
import com.practicum.playlistmaker.presentation.search.viewHolders.SearchTrackViewHolder

sealed class SearchListItem {
    data class TrackItem(val track: Track, val onTrackClick: () -> Unit) : SearchListItem()
    data class HeaderItem(val title: String) : SearchListItem()
    data class ActionButtonItem(val title: String, val onAction: () -> Unit) : SearchListItem()
    data class SpacingItem(val height: Int) : SearchListItem()
}

class SearchTracksAdapter(): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_TRACK = 0
        private const val TYPE_HEADER = 1
        private const val TYPE_ACTION_BUTTON = 2
        private const val TYPE_SPACING = 3
    }

    var items: List<SearchListItem> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        when (viewType) {
            TYPE_TRACK -> {
                val binding = ItemSearchTrackBinding.inflate(layoutInflater, parent, false)
                return SearchTrackViewHolder(binding)
            }
            TYPE_HEADER -> {
                val binding = ItemSearchHeaderBinding.inflate(layoutInflater, parent, false)
                return SearchHeaderViewHolder(binding)
            }
            TYPE_ACTION_BUTTON -> {
                val binding = ItemSearchActionButtonBinding.inflate(layoutInflater, parent, false)
                return SearchActionButtonViewHolder(binding)
            }
            TYPE_SPACING -> {
                return SearchSpacingViewHolder.create(parent)
            }
            else -> throw IllegalArgumentException("Unknown view type")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (items[position]) {
            is SearchListItem.TrackItem -> TYPE_TRACK
            is SearchListItem.HeaderItem -> TYPE_HEADER
            is SearchListItem.ActionButtonItem -> TYPE_ACTION_BUTTON
            is SearchListItem.SpacingItem -> TYPE_SPACING
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = items[position]
        when {
            holder is SearchTrackViewHolder && item is SearchListItem.TrackItem -> {
                holder.bind(item)
            }
            holder is SearchHeaderViewHolder && item is SearchListItem.HeaderItem -> {
                holder.bind(item)
            }
            holder is SearchActionButtonViewHolder && item is SearchListItem.ActionButtonItem -> {
                holder.bind(item)
            }
            holder is SearchSpacingViewHolder && item is SearchListItem.SpacingItem -> {
                holder.bind(item)
            }
        }
    }
}