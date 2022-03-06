package com.moon.map_application.utils.extension

import java.text.SimpleDateFormat
import java.util.*

object LongExtension {

    fun Long?.toDate(inputDateFormat: String): String? {
        var output: String? = null
        try {
            val dateCalendar = Calendar.getInstance()
            val inputDateSdf = SimpleDateFormat(inputDateFormat)
            this?.let {
                // dateCalendar.time = inputDateSdf.format(Date(it))
                output = inputDateSdf.format(Date(it))
            }

        } catch (e: Exception) {
            output = null
        }

        return output
    }
}