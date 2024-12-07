package com.practicum.playlistmaker.presentation.Helpers

import android.os.Handler
import android.os.Looper

class ActionDebouncer(
    val action: () -> Unit,
    val delay: Long = 2000L
) {
    private val handler = Handler(Looper.getMainLooper())
    private val actionRunnable = Runnable { action() }

    fun cancelAction() {
        handler.removeCallbacks(actionRunnable)
    }

    fun performAction() {
        cancelAction()
        handler.postDelayed(actionRunnable, delay)
    }
}