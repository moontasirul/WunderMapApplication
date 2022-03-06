package com.moon.map_application.utils

object AppEnum {

    enum class API_CALL_STATUS(val data: String) {
        SUCCESS("SUCCESS"),
        ERROR("ERROR"),
        LOADING("LOADING");

        companion object {
            fun fromString(value: String): API_CALL_STATUS {
                return values().first { it.data == value }
            }
        }
    }
}