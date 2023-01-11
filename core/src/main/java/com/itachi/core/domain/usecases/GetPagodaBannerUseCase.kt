package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.PagodaRepository

class GetPagodaBannerUseCase(private val pagodaRepository: PagodaRepository){
    operator fun invoke() = pagodaRepository.getPagodaBanner()
}