package com.itachi.core.domain.repositories

import kotlinx.coroutines.flow.Flow

interface RemoteConfigRepository {
    fun checkUpdate() : Flow<Boolean>
}