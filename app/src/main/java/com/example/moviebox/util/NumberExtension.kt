package com.example.moviebox.util

import kotlin.math.ln
import kotlin.math.pow

fun Int.toShortenedString(): String {
    return when {
        this >= 1000 -> {
            val suffixes = listOf("", "k", "M", "B", "T")
            val exp = (ln(this.toDouble()) / ln(1000.0)).toInt()
            val value = this / 1000.0.pow(exp.toDouble())
            String.format("%.1f%s", value, suffixes[exp])
        }
        else -> this.toString()
    }
}

fun Int.toHoursMinutes():String{
    val hours = this/60
    val minutes = this %60
    return "${hours}h ${minutes}m"
}