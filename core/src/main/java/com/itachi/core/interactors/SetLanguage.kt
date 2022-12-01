package com.itachi.core.interactors

import com.itachi.core.data.LanguageRepository

class SetLanguage(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(lang : String) = languageRepository.setLanguage(lang)
}