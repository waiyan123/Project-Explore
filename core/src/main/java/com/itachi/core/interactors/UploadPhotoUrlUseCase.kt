package com.itachi.core.interactors

import com.itachi.core.data.PhotoRepository
import com.itachi.core.domain.UploadedPhotoVO

class UploadPhotoUrlUseCase(
    private val photoRepository : PhotoRepository
) {
    suspend operator fun invoke(uploadedPhotoVO: UploadedPhotoVO) = photoRepository.uploadPhotoUrl(uploadedPhotoVO)
}