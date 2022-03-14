package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class GetView(private val viewRepository: ViewRepository) {
    suspend operator fun invoke(id : String) = viewRepository.getView(id)
}