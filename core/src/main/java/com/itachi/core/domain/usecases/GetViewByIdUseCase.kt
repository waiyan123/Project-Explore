package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.ViewRepository

class GetViewByIdUseCase(private val viewRepository: ViewRepository) {

    operator fun invoke(viewId : String) = viewRepository.getViewById(viewId)

}