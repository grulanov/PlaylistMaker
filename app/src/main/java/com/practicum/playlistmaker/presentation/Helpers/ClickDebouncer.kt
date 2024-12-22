package com.practicum.playlistmaker.presentation.Helpers

import android.os.Handler
import android.os.Looper

class ClickDebouncer {

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val handler = Handler(Looper.getMainLooper())
    private var isClickAllowed = true
    private var clickFlagResetRunnable = Runnable { isClickAllowed = true }

    fun cancel() {
        handler.removeCallbacks(clickFlagResetRunnable)
    }

    fun performClickActionWithDebounce(action: () -> Unit) {
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed(clickFlagResetRunnable, CLICK_DEBOUNCE_DELAY)
            action()
        }
    }

}