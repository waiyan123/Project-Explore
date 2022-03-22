package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class GetView(private val viewRepository: ViewRepository) {

    suspend fun fromRoom(id : String) = viewRepository.getViewByIdFromRoom(id)

    suspend fun fromFirebase(
        viewId : String,
        onSuccess : (ViewVO) -> Unit,
        onFailure : (String) -> Unit
    ) = viewRepository.getViewByIdFromFirebase(viewId,onSuccess,onFailure)

}