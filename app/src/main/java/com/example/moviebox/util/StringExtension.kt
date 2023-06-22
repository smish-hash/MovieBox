package com.example.moviebox.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun String.convertToFormattedDate(): String {
    val inputFormat = DateTimeFormatter.ISO_LOCAL_DATE
    val outputFormat = DateTimeFormatter.ofPattern("dd MMM, yyyy", Locale.ENGLISH)

    val date = LocalDate.parse(this, inputFormat)
    return date.format(outputFormat)
}