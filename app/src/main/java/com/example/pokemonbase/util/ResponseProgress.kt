package com.example.pokemonbase.util

sealed class ResponseProgress<T>(
    val data:T? = null,
    val message:String? = null
){
    class Success<T>(data: T?):ResponseProgress<T>(data)
    class Error<T>(message: String?):ResponseProgress<T>(message = message)
}
