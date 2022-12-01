package com.itachi.explore.framework

import android.content.SharedPreferences
import com.itachi.core.data.sharedpreferences.LanguageSharedPreferencesDataSource
import com.itachi.explore.utils.LANGUAGE
import kotlinx.coroutines.flow.flow
import me.myatminsoe.mdetect.MDetect

class LanguageSharedPreferencesDataSourceImpl(
    private val sharedPreferences : SharedPreferences
) : LanguageSharedPreferencesDataSource {
    override fun setLanguageToSharedPreferences(lang: String) {
        sharedPreferences.edit().putString(LANGUAGE,lang).apply()
    }

    override fun getLanguageFromSharedPreferences() = flow {
        sharedPreferences.getString(LANGUAGE,"en")?.let {
            when(it) {
                "en" -> emit("en")
                "mm" -> {
                    if(MDetect.isUnicode()){
                        emit("mm_unicode")
                    } else {
                        emit("mm_zawgyi")
                    }
                }
                else -> {
                    emit("en")
                }
            }
        }
    }
}