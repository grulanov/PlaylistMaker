package com.practicum.playlistmaker.logic.common

sealed class RepositoryException(message: String) : Exception(message) {
    class NetworkError(t: Throwable) : RepositoryException(t.message ?: "Unknown network error")
    class RequestError(val code: Int, val errorBody: String) : RepositoryException("Request fails with http code $code and errorBody $errorBody")
}