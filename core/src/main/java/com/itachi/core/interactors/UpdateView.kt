package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class UpdateView(private val viewRepository: ViewRepository) {

    suspend operator fun invoke(viewVO: ViewVO) = viewRepository.updateView(viewVO)
}