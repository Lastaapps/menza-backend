package cz.lastaapps.app.data

import cz.lastaapps.app.domain.ClearCron
import cz.lastaapps.app.domain.RatingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class ClearCronImpl(
    private val repo: RatingRepository,
    private val statistics: RatingRepository,
    private val scope: CoroutineScope,
) : ClearCron {
    companion object {
        private val log = logging()
    }

    override fun start() {
        scope.launch {
            log.i { "Scheduling job" }
            while (true) {
                try {
                    log.i { "Starting job" }
                    repo.resetRepository()
                    statistics.resetRepository()
                } catch (e: Exception) {
                    log.e(e) { "Job failed" }
                }
            }
        }
    }
}
