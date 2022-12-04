package com.itachi.core.interactors

import com.itachi.core.data.LanguageRepository

class GetLanguageUseCase(
    private val languageRepository: LanguageRepository
) {
    operator fun invoke() = languageRepository.getLanguage()
}