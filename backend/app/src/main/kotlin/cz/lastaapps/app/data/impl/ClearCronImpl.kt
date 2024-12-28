package cz.lastaapps.app.data.impl

import cz.lastaapps.app.config.ServerConfig
import cz.lastaapps.app.data.ClearCron
import cz.lastaapps.app.data.DishNameRepository
import cz.lastaapps.app.data.RatingRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.Clock
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime
import kotlinx.datetime.plus
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import org.lighthousegames.logging.logging

class ClearCronImpl(
    private val config: ServerConfig,
    private val repo: RatingRepository,
    private val namesRepo: DishNameRepository,
    private val statistics: RatingRepository,
    private val clock: Clock,
    private val scope: CoroutineScope,
) : ClearCron {
    companion object {
        private val log = logging()
    }

    override fun start() {
        scope.launch {
            while (true) {
                val now = clock.now()
                val date = now.toLocalDateTime(config.timeZone).date.plus(DatePeriod(days = 1))
                log.i { "Waiting for midnight until $date" }
                delay(LocalDateTime(date, LocalTime(0, 0, 0)).toInstant(config.timeZone) - now)

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
