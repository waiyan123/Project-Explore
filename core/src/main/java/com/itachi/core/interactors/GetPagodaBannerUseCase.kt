package com.itachi.core.interactors

import com.itachi.core.data.PagodaRepository

class GetPagodaBannerUseCase(private val pagodaRepository: PagodaRepository){
    operator fun invoke() = pagodaRepository.getPagodaBanner()
}