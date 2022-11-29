package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.ViewVO

class DeleteView(private val viewRepository: ViewRepository) {

    operator fun invoke(viewVO: ViewVO) = viewRepository.deleteView(viewVO)

}