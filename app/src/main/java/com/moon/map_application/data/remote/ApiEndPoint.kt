package com.moon.map_application.data.remote

class ApiEndPoint {
    companion object {
        const val BASE_URL = "https://s3.eu-central-1.amazonaws.com/wunderfleet-recruiting-dev/"
        const val CAR_LIST_API = "cars.json"
        const val CAR_INTO_APR = "cars/{id}"
    }
}