package cz.lastaapps.base

sealed interface Result<T : Any> {

    data class Success<T : Any>(val data: T) : Result<T>
    data class Error<T : Any>(val error: ErrorResult, val data: T? = null) : Result<T>
}

open class ErrorResult(val message: String?, val throwable: Throwable?)

fun <T : Any> T.toResult() = Result.Success(this)
fun <T : Any> ErrorResult.toResult() = Result.Error<T>(this)
fun <T : Any, R : Any> Result<T>.casted() = asError().error.toResult<R>()
fun <T : Any> Result<T>.isSuccess() = this is Result.Success
fun <T : Any> Result<T>.isError() = this is Result.Error
fun <T : Any> Result<T>.asSuccess() = this as Result.Success
fun <T : Any> Result<T>.asError() = this as Result.Error
inline fun <T : Any, R : Any> Result<T>.getIfSuccess(onError: (Result.Error<R>) -> Nothing): T {
    when (this) {
        is Result.Success -> return data
        is Result.Error -> onError(casted())
    }
}

fun <T : Any> Result<T>.get() = (this as? Result.Success)?.data

