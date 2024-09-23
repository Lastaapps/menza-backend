package cz.lastaapps.base.error

import cz.lastaapps.base.ErrorOutcome

sealed class NetworkError(
    message: String? = null,
    throwable: Throwable? = null,
) : ErrorOutcome(message, throwable) {
    class FailedToConnect(
        m: String,
        e: Throwable,
    ) : NetworkError(m, e)

    class NoNetworkConnection(
        m: String,
        e: Throwable,
    ) : NetworkError(m, e)
}
