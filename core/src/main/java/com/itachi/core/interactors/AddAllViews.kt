package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class AddAllViews(private val viewRepository: ViewRepository) {

    suspend fun toRoom(viewVoList : List<ViewVO>) = viewRepository.addAllViewsToRoom(viewVoList)

}