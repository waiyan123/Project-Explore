package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class AddAllViewsUseCase(private val viewRepository: ViewRepository) {

    suspend operator fun invoke(viewVoList : List<ViewVO>) = viewRepository.addAllViews(viewVoList)

}