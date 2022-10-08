package cz.lastaapps.base.usecase

import cz.lastaapps.base.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface UseCaseResult<P : UCParam, R : Any> {
    suspend operator fun invoke(params: P): Result<R>
}

abstract class UseCaseResultImpl<P : UCParam, R : Any>(private val dispatcher: CoroutineContext = Dispatchers.Default) :
    UseCaseResult<P, R> {
    override suspend fun invoke(params: P): Result<R> = withContext(dispatcher) { doWork(params) }

    protected abstract suspend fun doWork(params: P): Result<R>
}
