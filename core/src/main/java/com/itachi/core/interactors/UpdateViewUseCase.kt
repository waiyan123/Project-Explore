package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.data.ViewRepositoryImpl
import com.itachi.core.domain.ViewVO

class UpdateViewUseCase(private val viewRepository: ViewRepository) {

    operator fun invoke(viewVO: ViewVO) = viewRepository.updateView(viewVO)
}