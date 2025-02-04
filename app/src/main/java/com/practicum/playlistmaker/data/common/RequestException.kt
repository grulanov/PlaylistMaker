package com.practicum.playlistmaker.data.common

sealed class RequestException(message: String) : Exception(message) {
    class NetworkError(t: Throwable) : RequestException(t.message ?: "Unknown network error")
    class DecodingError(t: Throwable) : RequestException(t.message ?: "Unknown decoding error")
    class RequestError(val code: Int, val errorBody: String) : RequestException("Request fails with http code $code and errorBody $errorBody")
}