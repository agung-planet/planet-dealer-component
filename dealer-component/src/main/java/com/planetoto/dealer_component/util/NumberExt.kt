package com.planetoto.dealer_component.util

import java.text.NumberFormat
import java.util.Locale

fun Int?.format(): String {
    val value = this ?: 0
    return NumberFormat.getInstance(Locale("id")).format(value)
}

fun Int?.formatInId() = format()
fun Int?.formatInRupiah() = "Rp ${this.format()}"

fun Long.getDuration(isInSecond: Boolean = false, useAlias: Boolean = false): String {
    var different = if (isInSecond) this * 1000L else this

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

    val elapsedHoursTimesDay = elapsedHours + (24 * elapsedDays)

    if (useAlias) {
        return "Sisa waktu $elapsedHoursTimesDay jam : $elapsedMinutes menit : $elapsedSeconds detik"
    }

    val textHours =
        if (elapsedHoursTimesDay < 10) "0${elapsedHoursTimesDay}" else elapsedHoursTimesDay.toString()
    val textMinutes = if (elapsedMinutes < 10) "0${elapsedMinutes}" else elapsedMinutes.toString()
    val textSeconds = if (elapsedSeconds < 10) "0${elapsedSeconds}" else elapsedSeconds.toString()

    return "$textHours : $textMinutes : $textSeconds Tersisa"
}

fun Long?.getRelativeInDays(): Long {
    if (this == null || this == 0L) return 0
    val left = System.currentTimeMillis() - this
    return left / 1000 / 60 / 60 / 24
}

fun Long?.getRelativeInSeconds(): Long {
    if (this == null || this == 0L) return 0
    val left = System.currentTimeMillis() - this
    return left / 1000
}