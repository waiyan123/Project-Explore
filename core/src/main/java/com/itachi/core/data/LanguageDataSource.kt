package com.itachi.core.data

import kotlinx.coroutines.flow.Flow

interface LanguageDataSource {
    fun setLanguage(lang : String)
    fun getLanguage() : Flow<String>
}