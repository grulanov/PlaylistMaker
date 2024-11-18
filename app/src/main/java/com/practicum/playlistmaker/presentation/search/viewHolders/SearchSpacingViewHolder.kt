package com.practicum.playlistmaker.presentation.search.viewHolders

import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmaker.presentation.search.SearchListItem

class SearchSpacingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    fun bind(model: SearchListItem.SpacingItem) {
        val layoutParams = itemView.layoutParams
        layoutParams?.height = model.height
        itemView.layoutParams = layoutParams
    }

    companion object {
        fun create(parent: ViewGroup): SearchSpacingViewHolder {
            val view = View(parent.context).apply {
                layoutParams = ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    0
                )
            }
            return SearchSpacingViewHolder(view)
        }
    }
}