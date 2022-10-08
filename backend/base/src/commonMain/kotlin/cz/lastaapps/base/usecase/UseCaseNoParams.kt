package cz.lastaapps.base.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface UseCaseNoParams<R : Any?> {
    suspend operator fun invoke(): R
}

abstract class UseCaseNoParamsImpl<R : Any?>(private val dispatcher: CoroutineContext = Dispatchers.Default) :
    UseCaseNoParams<R> {
    override suspend fun invoke(): R = withContext(dispatcher) { doWork() }

    protected abstract suspend fun doWork(): R
}
