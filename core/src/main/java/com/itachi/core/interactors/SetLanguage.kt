package com.itachi.core.interactors

import com.itachi.core.data.LanguageDataSource

class SetLanguage(
    private val languageDataSource: LanguageDataSource
) {
    operator fun invoke(lang : String) = languageDataSource.setLanguage(lang)
}