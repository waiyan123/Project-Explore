package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class AddView(private val viewRepository: ViewRepository) {

    suspend fun toRoom(viewVO : ViewVO) = viewRepository.addViewToRoom(viewVO)

    suspend fun toFirebase(
        viewVO: ViewVO ,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    ) = viewRepository.addViewToFirebase(viewVO,onSuccess,onFailure)

}