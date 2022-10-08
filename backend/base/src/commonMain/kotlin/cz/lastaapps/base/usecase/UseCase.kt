package cz.lastaapps.base.usecase

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

interface UseCase<P : UCParam, R : Any?> {
    suspend operator fun invoke(params: P): R
}

abstract class UseCaseImpl<P : UCParam, R : Any?>(private val dispatcher: CoroutineContext = Dispatchers.Default) :
    UseCase<P, R> {
    override suspend fun invoke(params: P): R = withContext(dispatcher) { doWork(params) }

    protected abstract suspend fun doWork(params: P): R
}
