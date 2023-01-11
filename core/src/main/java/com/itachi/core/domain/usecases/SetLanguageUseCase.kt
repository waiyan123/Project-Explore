package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.LanguageRepository

class SetLanguageUseCase(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke(lang : String) = languageRepository.setLanguage(lang)
}