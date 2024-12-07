package com.practicum.playlistmaker.presentation.Helpers

import android.os.Handler
import android.os.Looper

class ClickDebouncer {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true

    fun performClickActionWithDebounce(action: () -> Unit) {
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
            action()
        }
    }

}