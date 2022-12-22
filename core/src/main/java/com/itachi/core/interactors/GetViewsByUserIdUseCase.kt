package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository

class GetViewsByUserIdUseCase(
    private val viewRepository: ViewRepository
) {
    operator fun invoke(id : String) = viewRepository.getViewListByUserId(id)
}