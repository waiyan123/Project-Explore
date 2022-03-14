package com.itachi.explore.framework.mappers

interface Mapper<I,O> {
    fun map(input : I?) : O
}
