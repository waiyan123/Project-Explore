package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.data.network.PhotoFirebaseDataSource
import com.itachi.core.domain.PhotoVO
import kotlinx.coroutines.flow.Flow

class PhotoRepositoryImpl(
    private val photoFirebaseDataSource: PhotoFirebaseDataSource,
) : PhotoRepository{

    override fun uploadPhotos(
        filePathList : List<String>
    ): Flow<Resource<List<PhotoVO>>> = photoFirebaseDataSource.uploadPhotos(filePathList)

    override suspend fun deletePhotos(photoList: List<PhotoVO>,itemId : String) = photoFirebaseDataSource.deletePhotos(photoList,itemId)
}