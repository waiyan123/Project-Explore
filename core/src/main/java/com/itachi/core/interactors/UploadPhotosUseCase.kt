package com.itachi.core.interactors

import com.itachi.core.common.Resource
import com.itachi.core.data.PhotoRepository
import com.itachi.core.domain.PhotoVO
import kotlinx.coroutines.flow.Flow

class UploadPhotosUseCase(private val photoRepository: PhotoRepository) {
    operator fun invoke(filePathList: List<String>) : Flow<Resource<List<PhotoVO>>> = photoRepository.uploadPhotos(filePathList)
}