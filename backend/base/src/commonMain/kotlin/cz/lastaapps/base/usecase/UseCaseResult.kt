package cz.lastaapps.base.usecase

import cz.lastaapps.base.Outcome
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UseCaseOutcome<P : UCParam, R : Any> {
    suspend operator fun invoke(params: P): Outcome<R>
}

abstract class UseCaseOutcomeImpl<P : UCParam, R : Any>(
    private val dispatcher: CoroutineContext = Dispatchers.Default,
) : UseCaseOutcome<P, R> {
    override suspend fun invoke(params: P): Outcome<R> = withContext(dispatcher) { doWork(params) }

    protected abstract suspend fun doWork(params: P): Outcome<R>
}
