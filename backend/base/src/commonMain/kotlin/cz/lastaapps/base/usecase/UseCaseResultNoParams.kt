package cz.lastaapps.base.usecase

import cz.lastaapps.base.Outcome
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

interface UseCaseOutcomeNoParams<R : Any> {
    suspend operator fun invoke(): Outcome<R>
}

abstract class UseCaseOutcomeNoParamsImpl<R : Any>(
    private val dispatcher: CoroutineContext = Dispatchers.Default,
) : UseCaseOutcomeNoParams<R> {
    override suspend fun invoke(): Outcome<R> = withContext(dispatcher) { doWork() }

    protected abstract suspend fun doWork(): Outcome<R>
}
