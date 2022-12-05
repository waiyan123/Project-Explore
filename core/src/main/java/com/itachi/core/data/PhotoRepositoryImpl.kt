package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.data.network.PhotoFirebaseDataSource
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UploadedPhotoVO
import kotlinx.coroutines.flow.Flow

class PhotoRepositoryImpl(
    private val photoFirebaseDataSource: PhotoFirebaseDataSource,
) : PhotoRepository{

    override fun uploadPhotos(
        filePathList : List<String>
    ): Flow<Resource<List<PhotoVO>>> = photoFirebaseDataSource.uploadPhotos(filePathList)

    override fun deletePhotos(photoList: List<PhotoVO>,itemId : String) = photoFirebaseDataSource.deletePhotos(photoList,itemId)
    override suspend fun uploadPhotoUrl(uploadedPhotoVO: UploadedPhotoVO) {
        photoFirebaseDataSource.uploadPhotoUrl(uploadedPhotoVO)
    }
}