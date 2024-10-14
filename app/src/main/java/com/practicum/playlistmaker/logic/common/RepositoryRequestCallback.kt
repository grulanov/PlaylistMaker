package com.practicum.playlistmaker.logic.common

fun interface RepositoryRequestCallback<T> {
    fun onResult(result: Result<T>)
}