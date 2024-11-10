package com.example.weathermate.utils

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun formatDate(timestamp: Int): String {
    val simpleDateFormat = SimpleDateFormat("EEE, MMM d")
    val date = Date(timestamp.toLong() * 1000)
    return simpleDateFormat.format(date)
}

fun formatDateTime(timestamp: Int): String {
    val simpleDateFormat = SimpleDateFormat("hh:mm:aa")
    val date = Date(timestamp.toLong() * 1000)
    return simpleDateFormat.format(date)
}

//add formatHour
fun formatHour(timestamp: Int): String {
    val simpleDateFormat = SimpleDateFormat("hh a", Locale.getDefault())
    val date = Date(timestamp.toLong() * 1000)
    return simpleDateFormat.format(date)
}


fun formatDecimals(item: Double): String {
    return "${item.toInt()}°"
}

fun formatDay(timestamp: Int): String {
    val simpleDateFormat = SimpleDateFormat("EEE") // "EEE" gives the abbreviated day of the week
    val date = Date(timestamp.toLong() * 1000)
    return simpleDateFormat.format(date)
}

