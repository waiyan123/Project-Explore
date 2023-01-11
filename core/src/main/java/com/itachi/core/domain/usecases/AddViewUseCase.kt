package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.ViewRepository
import com.itachi.core.domain.models.ViewVO

class AddViewUseCase(private val viewRepository: ViewRepository) {

    operator fun invoke(viewVO : ViewVO) = viewRepository.addView(viewVO)
}