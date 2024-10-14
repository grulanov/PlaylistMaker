package com.practicum.playlistmaker.Logic.Common

fun interface RepositoryRequestCallback<T> {
    fun onResult(result: Result<T>)
}