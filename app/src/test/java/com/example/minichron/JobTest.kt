package com.example.minichron

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource


class JobTest {
    companion object {

        private const val NO_FIRE = "There is no fire by input parameters"

        @JvmStatic
        fun parameters() = listOf(
            Arguments.of(Job(0, 0, Job.Type.DAILY), "12:45", "00:00 tomorrow"),
            Arguments.of(Job(3, 1, Job.Type.DAILY), "01:45", "01:03 tomorrow"),
            Arguments.of(Job(45, 12, Job.Type.DAILY), "12:45", "12:45 tomorrow"),
            Arguments.of(Job(45, 12, Job.Type.DAILY), "12:44", "12:45 today"),
            Arguments.of(Job(45, 12, Job.Type.DAILY), "12:46", "12:45 tomorrow"),
            Arguments.of(Job(45, null, Job.Type.DAILY), "12:46", "00:45 tomorrow"),
            Arguments.of(Job(null, 10, Job.Type.DAILY), "12:46", "10:00 tomorrow"),
            Arguments.of(Job(10, 10, Job.Type.DAILY), "ioiois", "Unable to parse the time"),
            Arguments.of(Job(null, null, Job.Type.HOURLY), "22:30", NO_FIRE),
            Arguments.of(Job(0, null, Job.Type.HOURLY), "0:00", "01:00 today"),
            Arguments.of(Job(12, null, Job.Type.HOURLY), "12:00", "12:12 today"),
            Arguments.of(Job(50, null, Job.Type.HOURLY), "23:50", "00:50 tomorrow"),
            Arguments.of(Job(null, null, Job.Type.MINUTELY), "23:59", "00:00 tomorrow"),
            Arguments.of(Job(30, 1, Job.Type.SIXTY_TIMES), "22:30", "00:00 tomorrow"),
            Arguments.of(Job(2, null, Job.Type.SIXTY_TIMES), "22:30", NO_FIRE),
            Arguments.of(Job(null, null, Job.Type.SIXTY_TIMES), "22:30", NO_FIRE),
        )
    }

    @ParameterizedTest
    @MethodSource("parameters")
    fun getFireTimeTest(job: Job, input: String, expected: String) {
        assertEquals(expected, job.getFireTime(input))
    }
}