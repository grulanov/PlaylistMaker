package com.practicum.playlistmaker.Presentation.Search

import com.practicum.playlistmaker.R
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.Logic.Models.Track
import com.practicum.playlistmaker.databinding.ItemSearchTrackBinding

class SearchTrackViewHolder(private val binding: ItemSearchTrackBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: Track) {
        binding.trackNameTextView.text = model.trackName
        binding.artistNameTextView.text = model.artistName
        binding.trackTimeTextView.text = model.trackTime

        Glide.with(binding.root)
            .load(model.artworkUrl100.toUri())
            .placeholder(R.drawable.placeholder_search_artwork)
            .transform(RoundedCorners(2))
            .into(binding.artworkImageView)

        binding.artistNameTextView.requestLayout()
    }
}