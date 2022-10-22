package com.itachi.core.data.sharedpreferences

import kotlinx.coroutines.flow.Flow

interface LanguageSharedPreferencesDataSource {

    fun setLanguageToSharedPreferences(lang : String)

    fun getLanguageFromSharedPreferences() : Flow<String>
}