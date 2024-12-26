package cz.lastaapps.app.data.impl

import cz.lastaapps.app.data.ClearCron
import cz.lastaapps.app.data.DishNameRepository
import cz.lastaapps.app.data.RatingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.lighthousegames.logging.logging

class ClearCronImpl(
    private val repo: RatingRepository,
    private val namesRepo: DishNameRepository,
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
                    namesRepo.resetRepository()
                    statistics.resetRepository()
                    System.gc()
                } catch (e: Exception) {
                    log.e(e) { "Job failed" }
                }
            }
        }
    }
}
