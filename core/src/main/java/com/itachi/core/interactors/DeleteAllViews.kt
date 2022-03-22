package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class DeleteAllViews(private val viewRepository: ViewRepository) {

    suspend fun fromRoom() = viewRepository.getAllViewsFromRoom()

    suspend fun fromFirebase(
        onSuccess : (List<ViewVO>) -> Unit,
        onFailure : (String) -> Unit
    ) = viewRepository.getAllViewsFromFirebase(onSuccess,onFailure)

}