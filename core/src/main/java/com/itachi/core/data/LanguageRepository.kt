package com.itachi.core.data

import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun setLanguage(lang : String)
    fun getLanguage() : Flow<String>
}