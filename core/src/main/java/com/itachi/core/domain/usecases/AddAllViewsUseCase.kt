package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.ViewRepository
import com.itachi.core.domain.models.ViewVO

class AddAllViewsUseCase(private val viewRepository: ViewRepository) {

    suspend operator fun invoke(viewVoList : List<ViewVO>) = viewRepository.addAllViews(viewVoList)

}