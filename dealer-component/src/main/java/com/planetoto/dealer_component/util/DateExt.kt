package com.planetoto.dealer_component.util

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.dateFormat(to: String = "dd MMM yyyy, HH:mm") =
    SimpleDateFormat(to, Locale("id")).format(this.times(1000))

fun Long.timeAlias(isInSecond: Boolean = false): String {
    var different = Date().time - if (isInSecond) (this * 1000L) else this

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = different / daysInMilli
    different %= daysInMilli

    val elapsedHours = different / hoursInMilli
    different %= hoursInMilli

    val elapsedMinutes = different / minutesInMilli
    different %= minutesInMilli

    val elapsedSeconds = different / secondsInMilli

    return when {
        elapsedDays >= 1 -> this.dateFormat()
        elapsedDays < 1 && elapsedHours < 24 && elapsedHours > 0 -> "$elapsedHours jam yang lalu"
        elapsedDays < 1 && elapsedHours < 1 && elapsedMinutes < 60 && elapsedMinutes > 0 -> "$elapsedMinutes menit yang lalu"
        else -> "Baru saja"
    }
}

fun Long.isExpired(isInSecond: Boolean = false): Boolean {
    val now = Date().time
    return now >= if (isInSecond) this * 1000 else this
}

fun Long.maskTimeShortFormat(isInSecond: Boolean): String {
    var different = Date().time - if (isInSecond) (this * 1000L) else this

    val secondsInMilli: Long = 1000
    val minutesInMilli = secondsInMilli * 60
    val hoursInMilli = minutesInMilli * 60
    val daysInMilli = hoursInMilli * 24

    val elapsedDays = different / daysInMilli
    different %= daysInMilli

    val elapsedHours = different / hoursInMilli
    different %= hoursInMilli

    val elapsedMinutes = different / minutesInMilli
    different %= minutesInMilli

    val elapsedSeconds = different / secondsInMilli

    return when {
        elapsedDays >= 1 -> "${elapsedDays}d"
        elapsedDays < 1 && elapsedHours < 24 && elapsedHours > 0 -> "${elapsedHours}h"
        elapsedDays < 1 && elapsedHours < 1 && elapsedMinutes < 60 && elapsedMinutes > 0 -> "${elapsedMinutes}m"
        else -> "${elapsedSeconds}s"
    }
}