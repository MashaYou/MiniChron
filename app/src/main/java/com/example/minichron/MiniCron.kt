package com.example.minichron

import android.os.Build
import androidx.annotation.RequiresApi
import java.io.BufferedReader
import java.io.File

@RequiresApi(Build.VERSION_CODES.O)
fun main() {
    val bufferedReader: BufferedReader = File("app/src/input.txt").bufferedReader()
    val inputString = bufferedReader.use { it.readText() }.split("\n")

    val dailyJob = inputString[0].mapToJob()
    val hourlyJob = inputString[1].mapToJob()
    val minutelyJob = inputString[2].mapToJob()
    val sixtyTimesJob = inputString[3].mapToJob()

    println("Time:")
    val time = readLine()
    time?.let {
        dailyJob.printFireTime(it)
        hourlyJob.printFireTime(it)
        minutelyJob.printFireTime(it)
        sixtyTimesJob.printFireTime(it)
    } ?: print("Parsing error")
}

fun String.mapToJob(): Job {
    val list = split(' ')
    return Job(
        min = list[0].toIntOrNull(),
        hour = list[1].toIntOrNull(),
        type = Job.Type.getType(list[2]),
    )
}

