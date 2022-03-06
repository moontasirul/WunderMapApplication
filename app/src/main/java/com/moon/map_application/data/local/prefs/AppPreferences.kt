package com.moon.map_application.data.local.prefs

import android.content.Context
import android.content.SharedPreferences
import com.example.example.QuickRentalResponse
import com.google.gson.Gson
import com.moon.map_application.utils.Constants.Companion.PREFERENCE_NAME
import com.moon.map_application.utils.extension.StringExtension.toSafeString
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AppPreferences @Inject constructor(mContext: Context, private val mGson: Gson) {
    private val PREF_KEY_QUICK_RENTAL = "PREF_KEY_QUICK_RENTAL"

    private var mPrefs: SharedPreferences =
        mContext.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE)


    var quickRentalResponse: QuickRentalResponse?
        get() {
            val strTemp = mPrefs.getString(
                PREF_KEY_QUICK_RENTAL,
                ""
            ).toSafeString()
            return if (strTemp.isNotEmpty()) {
                mGson.fromJson(strTemp, QuickRentalResponse::class.java)
            } else {
                null
            }
        }
        set(token) {
            mPrefs.edit()
                .putString(
                    PREF_KEY_QUICK_RENTAL,
                    mGson.toJson(token)
                )
                .apply()
        }
}