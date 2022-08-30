package com.itachi.explore.framework.mappers

import javax.inject.Inject

class ListMapperImpl<I, O> @Inject constructor(
    private val mapper: Mapper<I, O>
) : ListMapper<I, O> {
    override fun map(input: List<I>?): List<O> {
        return input?.map { mapper.map(it) }.orEmpty()
    }
}