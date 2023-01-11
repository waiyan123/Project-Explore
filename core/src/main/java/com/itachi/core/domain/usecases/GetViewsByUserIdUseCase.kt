package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.ViewRepository

class GetViewsByUserIdUseCase(
    private val viewRepository: ViewRepository
) {
    operator fun invoke(id : String) = viewRepository.getViewListByUserId(id)
}