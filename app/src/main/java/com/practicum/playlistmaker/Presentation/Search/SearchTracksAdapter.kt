package com.practicum.playlistmaker.Presentation.Search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.Logic.DomainModels.Track
import com.practicum.playlistmaker.databinding.ItemSearchTrackBinding

class SearchTracksAdapter(): RecyclerView.Adapter<SearchTrackViewHolder>() {

    var tracks: List<Track> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTrackViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemSearchTrackBinding.inflate(layoutInflater, parent, false)
        return SearchTrackViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

    override fun onBindViewHolder(holder: SearchTrackViewHolder, position: Int) {
        holder.bind(tracks[position])
    }
}