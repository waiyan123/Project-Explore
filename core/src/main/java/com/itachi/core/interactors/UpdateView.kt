package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class UpdateView(private val viewRepository: ViewRepository) {

    suspend fun toRoom(viewVO: ViewVO) = viewRepository.updateViewToRoom(viewVO)

    suspend fun toFirebase(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewRepository.updateViewToFirebase(viewVO,onSuccess,onFailure)

}