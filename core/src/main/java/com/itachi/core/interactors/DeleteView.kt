package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class DeleteView(private val viewRepository: ViewRepository) {

    suspend fun fromRoom(viewVO: ViewVO) = viewRepository.deleteViewFromRoom(viewVO)

    suspend fun fromFirebase(
        viewVO: ViewVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewRepository.deleteViewFromFirebase(viewVO,onSuccess,onFailure)

}