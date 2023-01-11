package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.PhotoRepository
import com.itachi.core.domain.models.PhotoVO

class DeletePhotoUseCase(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(
        photoList : List<PhotoVO>,
        itemId : String
    ) = photoRepository.deletePhotos(photoList,itemId)
}