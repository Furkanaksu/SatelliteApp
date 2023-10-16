package com.furkan.satellite.utils

sealed class Resource<out R> {
    data class Success<out T>(val data: T) : Resource<T>()
    data class Failure(val error: String) : Resource<Nothing>()
    class Loading<out R> : Resource<R>()
}