package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.ViewRepository

class GetAllViewsUseCase(private val viewRepository: ViewRepository) {

    operator fun invoke() = viewRepository.getAllViews()
}