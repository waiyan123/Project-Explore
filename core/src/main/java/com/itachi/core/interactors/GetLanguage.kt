package com.itachi.core.interactors

import com.itachi.core.data.LanguageRepository

class GetLanguage(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke() = languageRepository.getLanguage()
}