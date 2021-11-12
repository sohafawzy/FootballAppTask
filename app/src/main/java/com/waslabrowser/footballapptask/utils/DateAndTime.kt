package com.waslabrowser.footballapptask.utils

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun toDate(date: String?): String {
    val outputFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val output = inputFormat.parse(date)
    return outputFormat.format(output)
}

fun toHour(hour: String?): String {
    val outputFormat = SimpleDateFormat("HH:mm", Locale.getDefault())
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val output = inputFormat.parse(hour)
    return outputFormat.format(output)
}

fun String.greaterOrEqualThan(date:String):Boolean{
    val inputFormat = SimpleDateFormat("EEE, d MMM yyyy", Locale.getDefault())
    return inputFormat.parse(this).after(inputFormat.parse(date)) || inputFormat.parse(this) == inputFormat.parse(date)
}
