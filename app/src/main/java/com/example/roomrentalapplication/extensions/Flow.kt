package com.example.roomrentalapplication.extensions

import com.example.roomrentalapplication.data.remote.exception.BaseError
import com.example.roomrentalapplication.data.remote.exception.DefaultError
import com.example.roomrentalapplication.ui.base.viewmodel.BaseViewModel
import com.example.roomrentalapplication.utils.LogUtils
import kotlinx.coroutines.flow.*
import java.net.UnknownHostException

sealed class FlowResult<out T> {
    data class Success<T>(val value: T) : FlowResult<T>()
    data class Error(val baseError: BaseError) : FlowResult<Nothing>()
}

suspend inline fun <T> safeUseCase(
    crossinline block: suspend () -> T,
): FlowResult<T> = try {
    FlowResult.Success(block())
} catch (e: BaseError) {
    FlowResult.Error(e)
}

inline fun <T> safeFlow(
    crossinline block: suspend () -> T,
): Flow<FlowResult<T>> = flow {
    try {
        val repoResult = block()
        emit(FlowResult.Success(repoResult))
    } catch (e: BaseError) {
        emit(FlowResult.Error(e))
    } catch (e: UnknownHostException) {
        LogUtils.d(e.stackTraceToString())
    } catch (e: Exception) {
        LogUtils.e(e.stackTraceToString())
        emit(FlowResult.Error(DefaultError()))
    }
}

fun <T> Flow<FlowResult<T>>.onSuccess(action: suspend (T) -> Unit): Flow<FlowResult<T>> =
    transform { result ->
        if (result is FlowResult.Success<T>) {
            action(result.value)
        }
        return@transform emit(result)
    }

fun <T> Flow<FlowResult<T>>.mapSuccess(): Flow<T> =
    transform { result ->
        if (result is FlowResult.Success<T>) {
            emit(result.value)
        }
    }

fun <T> Flow<FlowResult<T>>.onError(
    action: suspend (BaseError) -> Unit = {}
): Flow<FlowResult<T>> =
    transform { result ->
        if (result is FlowResult.Error) {
            action(result.baseError)
        }
        return@transform emit(result)
    }

fun <H, X> Flow<H>.bindLoading(x: X): Flow<H> where  X : BaseViewModel =
    this.onStart {
        x.handleLoading(true)
    }.onCompletion {
        x.handleLoading(false)
    }

fun <H, X> Flow<FlowResult<H>>.bindError(x: X): Flow<FlowResult<H>> where  X : BaseViewModel =
    this.onError {
        x.handleError(it)
    }
