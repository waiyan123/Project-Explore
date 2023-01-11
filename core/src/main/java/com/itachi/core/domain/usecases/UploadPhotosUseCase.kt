package com.itachi.core.domain.usecases

import com.itachi.core.common.Resource
import com.itachi.core.domain.repositories.PhotoRepository
import com.itachi.core.domain.models.PhotoVO
import kotlinx.coroutines.flow.Flow

class UploadPhotosUseCase(private val photoRepository: PhotoRepository) {
    operator fun invoke(
        filePathList: List<String>
    ) : Flow<Resource<List<PhotoVO>>> = photoRepository.uploadPhotos(filePathList)
}