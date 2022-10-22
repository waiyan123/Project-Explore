package com.itachi.core.interactors

import com.itachi.core.data.LanguageDataSource
import com.itachi.core.data.LanguageRepository

class GetLanguage(
    private val languageDataSource: LanguageDataSource
) {
    operator fun invoke() = languageDataSource.getLanguage()
}