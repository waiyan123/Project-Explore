package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.ViewRepository
import com.itachi.core.domain.models.ViewVO

class UpdateViewUseCase(private val viewRepository: ViewRepository) {

    operator fun invoke(viewVO: ViewVO) = viewRepository.updateView(viewVO)
}