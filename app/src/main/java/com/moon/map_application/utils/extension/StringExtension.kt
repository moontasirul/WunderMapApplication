package com.moon.map_application.utils.extension


import java.text.SimpleDateFormat
import java.util.*


object StringExtension {

    fun String?.toDateFormat(inputDateFormat: String, outputDateFormat: String): String {
        var output = ""
        try {
            val dateCalendar = Calendar.getInstance()
            val inputDateSdf = SimpleDateFormat(inputDateFormat, Locale.getDefault())
            val outputDateSdf = SimpleDateFormat(outputDateFormat, Locale.getDefault())

            this?.let {
                dateCalendar.time = inputDateSdf.parse(it)
                output = outputDateSdf.format(dateCalendar.time)
            }

        } catch (e: Exception) {
            output = ""
        }

        return output
    }

    fun String?.toDate(inputDateFormat: String): Date? {
        var output: Date? = null
        try {
            val dateCalendar = Calendar.getInstance()
            val inputDateSdf = SimpleDateFormat(inputDateFormat, Locale.getDefault())
            this?.let {
                dateCalendar.time = inputDateSdf.parse(it)
                output = dateCalendar.time
            }

        } catch (e: Exception) {
            output = null
        }

        return output
    }


    fun String?.toSafeString(): String {
        return if (this == null || this.isEmpty()) "" else {
            this
        }
    }
}