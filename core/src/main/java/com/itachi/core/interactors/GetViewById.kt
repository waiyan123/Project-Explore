package com.itachi.core.interactors

import com.itachi.core.data.ViewRepository

class GetViewById(private val viewRepository: ViewRepository) {

    operator fun invoke(viewId : String) = viewRepository.getViewById(viewId)

}