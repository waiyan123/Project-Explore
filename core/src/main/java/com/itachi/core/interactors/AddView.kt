package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.data.ViewRepositoryImpl
import com.itachi.core.domain.ViewVO

class AddView(private val viewRepository: ViewRepository) {

    operator fun invoke(viewVO : ViewVO) = viewRepository.addView(viewVO)
}