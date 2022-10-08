package cz.lastaapps.base.usecase

import cz.lastaapps.base.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface UseCaseResultNoParams<R : Any> {
    suspend operator fun invoke(): Result<R>
}

abstract class UseCaseResultNoParamsImpl<R : Any>(private val dispatcher: CoroutineContext = Dispatchers.Default) :
    UseCaseResultNoParams<R> {
    override suspend fun invoke(): Result<R> = withContext(dispatcher) { doWork() }

    protected abstract suspend fun doWork(): Result<R>
}
