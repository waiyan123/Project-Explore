package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.data.ViewRepositoryImpl
import com.itachi.core.domain.UploadedPhotoVO

class GetAllViewsPhotoUseCase(private val viewRepository: ViewRepository) {

    operator fun invoke() = viewRepository.getAllViewsPhoto()
}