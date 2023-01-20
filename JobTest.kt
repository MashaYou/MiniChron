fun main() {
    args.forEach {
        with(it) {
            val result = job.getFireTime(time)
            if (result != expected) {
                throw TestFailedExeption("$result != $expected")
            }
        }
    }
    print("All tests are successful")
}

private val NO_FIRE = "There is no fire by input parameters"

private val args = listOf(
    TestCase(Job(0, 0, Job.Type.DAILY), "12:45", "00:00 tomorrow"),
    TestCase(Job(3, 1, Job.Type.DAILY), "01:45", "01:03 tomorrow"),
    TestCase(Job(45, 12, Job.Type.DAILY), "12:45", "12:45 tomorrow"),
    TestCase(Job(45, 12, Job.Type.DAILY), "12:44", "12:45 today"),
    TestCase(Job(45, 12, Job.Type.DAILY), "12:46", "12:45 tomorrow"),
    TestCase(Job(45, null, Job.Type.DAILY), "12:46", "00:45 tomorrow"),
    TestCase(Job(null, 10, Job.Type.DAILY), "12:46", "10:00 tomorrow"),
    TestCase(Job(10, 10, Job.Type.DAILY), "ioiois", "Unable to parse the input"),
    TestCase(Job(null, null, Job.Type.HOURLY), "22:30", NO_FIRE),
    TestCase(Job(0, null, Job.Type.HOURLY), "0:00", "01:00 today"),
    TestCase(Job(12, null, Job.Type.HOURLY), "12:00", "12:12 today"),
    TestCase(Job(50, null, Job.Type.HOURLY), "23:50", "00:50 tomorrow"),
    TestCase(Job(null, null, Job.Type.MINUTELY), "23:59", "00:00 tomorrow"),
    TestCase(Job(30, 1, Job.Type.SIXTY_TIMES), "22:30", "00:00 tomorrow"),
    TestCase(Job(2, null, Job.Type.SIXTY_TIMES), "22:30", NO_FIRE),
    TestCase(Job(null, null, Job.Type.SIXTY_TIMES), "22:30", NO_FIRE),
)

private data class TestCase(
    val job: Job,
    val time: String,
    val expected: String,
)

private class TestFailedExeption(message: String) : Exception(message)