package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.LanguageRepository

class GetLanguageUseCase(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke() = languageRepository.getLanguage()
}