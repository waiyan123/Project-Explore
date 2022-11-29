package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.data.ViewRepositoryImpl
import com.itachi.core.domain.ViewVO

class DeleteAllViews(private val viewRepository: ViewRepository) {

    suspend operator fun invoke() = viewRepository.deleteAllViews()
}