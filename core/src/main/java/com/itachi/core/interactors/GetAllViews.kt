package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository

class GetAllViews(private val viewRepository: ViewRepository) {

    suspend operator fun invoke() = viewRepository.getAllViews()
}