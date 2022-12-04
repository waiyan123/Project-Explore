package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UploadedPhotoVO
import kotlinx.coroutines.flow.Flow

interface PhotoRepository {

    fun uploadPhotos(filePathList : List<String>) : Flow<Resource<List<PhotoVO>>>

    suspend fun deletePhotos(photoList : List<PhotoVO>,itemId : String)

    suspend fun uploadPhotoUrl(uploadedPhotoVO: UploadedPhotoVO)
}