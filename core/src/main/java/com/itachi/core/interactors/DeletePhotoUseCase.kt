package com.itachi.core.interactors

import com.itachi.core.data.PhotoRepository
import com.itachi.core.domain.PhotoVO

class DeletePhotoUseCase(private val photoRepository: PhotoRepository) {
    suspend operator fun invoke(
        photoList : List<PhotoVO>,
        itemId : String
    ) = photoRepository.deletePhotos(photoList,itemId)
}