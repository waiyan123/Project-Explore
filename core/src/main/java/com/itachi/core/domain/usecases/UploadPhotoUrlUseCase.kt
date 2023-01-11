package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.PhotoRepository
import com.itachi.core.domain.models.UploadedPhotoVO

class UploadPhotoUrlUseCase(
    private val photoRepository : PhotoRepository
) {
    suspend operator fun invoke(uploadedPhotoVO: UploadedPhotoVO) = photoRepository.uploadPhotoUrl(uploadedPhotoVO)
}