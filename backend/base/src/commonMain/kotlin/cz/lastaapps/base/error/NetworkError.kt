package cz.lastaapps.base.error

import cz.lastaapps.base.ErrorResult

sealed class NetworkError(message: String? = null, throwable: Throwable? = null) : ErrorResult(message, throwable) {
    class FailedToConnect(m: String, e: Throwable) : NetworkError(m, e)
    class NoNetworkConnection(m: String, e: Throwable) : NetworkError(m, e)
}