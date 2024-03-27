package com.example.musify.data.utils

import java.time.Duration
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import java.time.format.DateTimeParseException


/**
 * A class that contains the duration and date of an episode
 * in the following format:
 * @param day an integer representing the day of the month.
 * @param month a **three letter** [String] representing the month.
 * Eg. February will be represented as "Feb".
 * @param year an integer representing the year.
 * @param hours an integer representing the hour portion of the duration
 * of the podcast. If a podcast's duration is less than an hour, then
 * this value would be zero.
 * @param minutes an integer representing the minutes portion of the duration
 * of the podcast. This value is **guaranteed to have a minimum value of 1**.
 * This means that if an episode's duration is lower than 1 minute, the
 * [minutes] property would have a value of 1.
 */
data class FormattedEpisodeDateAndDuration(
    val day: Int,
    val month: String,
    val year: Int,
    val hours: Int,
    val minutes: Int
)



/**
 * A utility method used to generate an instance of [FormattedEpisodeDateAndDuration]
 * based on the [releaseDateString] & [durationMillis] parameters.
 * @see FormattedEpisodeDateAndDuration
 */
fun getFormattedEpisodeReleaseDateAndDuration(
    releaseDateString: String,
    durationMillis: Long
): FormattedEpisodeDateAndDuration {
    return try {
        val localDate = LocalDate.parse(releaseDateString)
        val duration = Duration.ofMillis(durationMillis)
        val hours = duration.toHours()
        val minutes = duration.minusHours(hours).toMinutes()
        FormattedEpisodeDateAndDuration(
            month = localDate.month.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            day = localDate.dayOfMonth,
            year = localDate.year,
            hours = hours.toInt(),
            minutes = minutes.toInt().coerceAtLeast(1)
        )
    } catch (e: DateTimeParseException) {
        // Handle the exception gracefully
        val duration = Duration.ofMillis(durationMillis)
        val hours = duration.toHours()
        val minutes = duration.minusHours(hours).toMinutes()
        FormattedEpisodeDateAndDuration(
            month = "",
            day = 0,
            year = 0,
            hours = hours.toInt(),
            minutes = minutes.toInt().coerceAtLeast(1)
        )
    }
}

