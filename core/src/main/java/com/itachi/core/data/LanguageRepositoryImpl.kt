package com.itachi.core.data

import com.itachi.core.data.sharedpreferences.LanguageSharedPreferencesDataSource
import com.itachi.core.domain.repositories.LanguageRepository

class LanguageRepositoryImpl(
    private val languageSharedPreferenceDataSource : LanguageSharedPreferencesDataSource
) : LanguageRepository {

    override fun setLanguage(lang: String) = languageSharedPreferenceDataSource.setLanguageToSharedPreferences(lang)

    override fun getLanguage() = languageSharedPreferenceDataSource.getLanguageFromSharedPreferences()

}