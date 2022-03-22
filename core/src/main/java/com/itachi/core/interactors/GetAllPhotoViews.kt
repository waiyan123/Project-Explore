package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.UploadedPhotoVO

class GetAllPhotoViews(private val viewRepository: ViewRepository) {

    suspend fun fromFirebase(
        onSuccess: (uploadedPhotoList: ArrayList<UploadedPhotoVO>) -> Unit,
        onFailure: (error: String) -> Unit
    ) = viewRepository.getAllPhotoViewsFromFirebase(onSuccess,onFailure)

}