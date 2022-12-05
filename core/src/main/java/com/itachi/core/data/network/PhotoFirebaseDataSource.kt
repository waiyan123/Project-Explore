package com.itachi.core.data.network

import com.itachi.core.common.Resource
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UploadedPhotoVO
import kotlinx.coroutines.flow.Flow

interface PhotoFirebaseDataSource {

    fun uploadPhotos(filePathList : List<String>) : Flow<Resource<List<PhotoVO>>>

    fun deletePhotos(photoList: List<PhotoVO>, itemId: String) : Flow<Resource<String>>

    suspend fun uploadPhotoUrl(uploadedPhotoVO: UploadedPhotoVO)

}