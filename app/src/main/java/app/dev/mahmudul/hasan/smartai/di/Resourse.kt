package com.appdevmhr.dpi_sai.di

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

sealed class Resourse<out R> {
    data class Success<out R>(val result: R) : Resourse<R>()
    data class Failure(val exception: Throwable) : Resourse<Nothing>()
    object Loading : Resourse<Nothing>()

    override fun toString(): String {
        return when (this) {
            is Success -> "Success[data=$result]"
            is Failure -> "Failure[exception=${exception.message}]"
            Loading -> "Loading"
        }
    }

}

fun <T, R> Resourse<T>.map(mapper: (T) -> R): Resourse<R> {
    return when (this) {
        is Resourse.Success -> Resourse.Success(mapper(result))
        is Resourse.Failure -> Resourse.Failure(exception)
        Resourse.Loading -> Resourse.Loading
    }
}

fun <T> Flow<Resourse<T>>.doOnSuccess(action: suspend (value: T) -> Unit): Flow<Resourse<T>> =
    transform {
        if (it is Resourse.Success) {
            action(it.result)
        }
        return@transform emit(it)
    }

fun <T> Flow<Resourse<T>>.doOnFailure(action: suspend (value: Throwable) -> Unit): Flow<Resourse<T>> = transform {
    if (it is Resourse.Failure) {
        action(it.exception)
    }
    return@transform emit(it)
}

fun <T> Flow<Resourse<T>>.doOnLoading(action: suspend () -> Unit): Flow<Resourse<T>> = transform {
    if (it is Resourse.Loading) {
        action()
    }
    return@transform emit(it)
}