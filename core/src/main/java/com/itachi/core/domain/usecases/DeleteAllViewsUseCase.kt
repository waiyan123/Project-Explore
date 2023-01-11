package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.ViewRepository

class DeleteAllViewsUseCase(private val viewRepository: ViewRepository) {

    suspend operator fun invoke() = viewRepository.deleteAllViews()
}