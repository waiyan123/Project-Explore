package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.ViewRepository

class GetAllViewsPhotoUseCase(private val viewRepository: ViewRepository) {

    operator fun invoke() = viewRepository.getAllViewsPhoto()
}