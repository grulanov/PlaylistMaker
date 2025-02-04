package com.practicum.playlistmaker.ui.search.viewHolders

import com.practicum.playlistmaker.R
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmaker.databinding.ItemSearchTrackBinding
import com.practicum.playlistmaker.presentation.search.SearchListItem

class SearchTrackViewHolder(private val binding: ItemSearchTrackBinding): RecyclerView.ViewHolder(binding.root) {
    fun bind(model: SearchListItem.TrackItem) {
        binding.trackNameTextView.text = model.track.trackName
        binding.artistNameTextView.text = model.track.artistName
        binding.trackTimeTextView.text = model.track.trackTime

        Glide.with(binding.root)
            .load(model.track.artworkUrl100?.toUri())
            .placeholder(R.drawable.placeholder_search_artwork)
            .transform(RoundedCorners(2))
            .into(binding.artworkImageView)

        binding.artistNameTextView.requestLayout()

        itemView.setOnClickListener {
            model.onTrackClick()
        }
    }
}