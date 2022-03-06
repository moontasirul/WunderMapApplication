package com.moon.map_application.utils.extension


import java.text.SimpleDateFormat
import java.util.*

object DateExtension {
    /**
     * Date time to custom format
     */
    fun Date?.toString(dateTimeFormat: String): String {
        var output = ""
        val formatter = SimpleDateFormat(dateTimeFormat, Locale.getDefault())
        this?.let {
            output = formatter.format(this)
        }
        return output
    }

    /**
     * Date time to custom format with UTC
     */
    fun Date?.toString(dateTimeFormat: String, utc: String): String {
        var output = ""
        val formatter = SimpleDateFormat(dateTimeFormat, Locale.getDefault())
        formatter.timeZone = TimeZone.getTimeZone(utc)
        this?.let {
            output = formatter.format(this)
        }
        return output
    }


    /**
     * Add Days
     */
    fun Date.addDays(daysAgo: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.add(Calendar.DAY_OF_YEAR, daysAgo)
        return calendar.time
    }

    /**
     * Add second
     */
    fun Date.addSeconds(seconds: Int): Date {
        val calendar = Calendar.getInstance()
        calendar.time = this
        calendar.add(Calendar.SECOND, seconds)
        return calendar.time
    }


}