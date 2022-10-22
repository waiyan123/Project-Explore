package com.itachi.core.data

import com.itachi.core.data.sharedpreferences.LanguageSharedPreferencesDataSource

class LanguageRepository(
    private val languageSharedPreferenceDataSource : LanguageSharedPreferencesDataSource
) : LanguageDataSource{

    override fun setLanguage(lang: String) = languageSharedPreferenceDataSource.setLanguageToSharedPreferences(lang)

    override fun getLanguage() = languageSharedPreferenceDataSource.getLanguageFromSharedPreferences()

}