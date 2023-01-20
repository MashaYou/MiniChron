import java.time.*
import java.time.format.DateTimeFormatter

data class Job(
    val min: Int?,
    val hour: Int?,
    val type: Type,
) {

    companion object {
        private const val TODAY_ADDITION = " today"
        private const val TOMORROW_ADDITION = " tomorrow"
        private const val OUTPUT_PATTERN = "HH:mm"
    }

    enum class Type(
        val path: String,
    ) {
        DAILY(
            path = "/bin/run_me_daily",
        ),
        HOURLY(
            path = "/bin/run_me_hourly",
        ),
        MINUTELY(
            path = "/bin/run_me_every_minute",
        ),
        SIXTY_TIMES(
            path = "/bin/run_me_sixty_times"
        );

        companion object {
            fun getType(path: String): Type {
                return when (path) {
                    DAILY.path -> DAILY
                    HOURLY.path -> HOURLY
                    MINUTELY.path -> MINUTELY
                    else -> SIXTY_TIMES
                }
            }
        }
    }

    fun getFireTime(input: String): String {
        val (hour, minute) = input
            .split(':')
            .map { it.toIntOrNull() ?: return "Unable to parse the input" }

        val currentTime = ZonedDateTime.of(
            LocalDate.now(),
            LocalTime.of(hour, minute),
            ZoneId.systemDefault(),
        )

        val resultTime = when (type) {
            Type.DAILY -> getDailyFireTime(currentTime)
            Type.HOURLY -> getHourlyFireTime(currentTime)
            Type.MINUTELY -> getMinutelyFireTime(currentTime)
            Type.SIXTY_TIMES -> getSixtyTimes(currentTime)
        }

        return resultTime?.let {
            val addition = when (it.dayOfMonth) {
                currentTime.dayOfMonth -> TODAY_ADDITION
                else -> TOMORROW_ADDITION
            }
            it.format(DateTimeFormatter.ofPattern(OUTPUT_PATTERN)) + addition
        } ?: "There is no fire by input parameters"
    }

    private fun getDailyFireTime(currentTime: ZonedDateTime): ZonedDateTime? {
        if (hour == null && min == null) return null
        val hour = this.hour ?: 0
        val minutes = this.min ?: 0
        val todayFire = ZonedDateTime.of(
            LocalDate.now(),
            LocalTime.of(hour, minutes),
            ZoneId.systemDefault(),
        )
        return if (currentTime.isBefore(todayFire)) {
                todayFire
            } else {
                todayFire.plusDays(1)
            }
    }

    private fun getHourlyFireTime(currentTime: ZonedDateTime): ZonedDateTime? {
        return this.min?.let { minutes ->
            if (currentTime.minute >= minutes) {
                currentTime.plusHours(1).withMinute(minutes)
            } else {
                currentTime.withMinute(minutes)
            }
        }
    }

    private fun getMinutelyFireTime(currentTime: ZonedDateTime): ZonedDateTime {
        return currentTime.plusMinutes(1)
    }

    private fun getSixtyTimes(currentTime: ZonedDateTime): ZonedDateTime? {
        val defStartTime = ZonedDateTime.of(
            LocalDate.now(),
            LocalTime.of(0, 0),
            ZoneId.systemDefault(),
        ).toEpochSecond()

        var mins = 0L
        this.hour?.let {
            mins = (it * 60).toLong()
        }
        this.min?.let {
            mins += it
        }

        if (mins == 0L) return null

        val curTimeInMillis = currentTime.toEpochSecond()
        var fire = ZonedDateTime.ofInstant(
            Instant.ofEpochSecond(defStartTime),
            ZoneId.systemDefault()
        )
        for (i in 1..60) {
            fire = fire.plusMinutes(mins)
            if (fire.toEpochSecond() - curTimeInMillis > 0) {
                return fire
            }
        }
        return null
    }
}

fun Job.printFireTime(currentTime: String) {
    println(getFireTime(currentTime) + " ${type.path}")
}
