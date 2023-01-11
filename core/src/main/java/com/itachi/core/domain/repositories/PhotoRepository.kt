package com.itachi.core.domain.repositories

import com.itachi.core.common.Resource
import com.itachi.core.domain.models.PhotoVO
import com.itachi.core.domain.models.UploadedPhotoVO
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun uploadPhotos(filePathList : List<String>) : Flow<Resource<List<PhotoVO>>>

    fun deletePhotos(photoList : List<PhotoVO>, itemId : String) : Flow<Resource<String>>

    suspend fun uploadPhotoUrl(uploadedPhotoVO: UploadedPhotoVO)
}