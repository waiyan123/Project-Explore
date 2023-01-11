package com.itachi.core.domain.repositories

import kotlinx.coroutines.flow.Flow

interface LanguageRepository {
    fun setLanguage(lang : String)
    fun getLanguage() : Flow<String>
}