package com.itachi.core.data

import kotlinx.coroutines.flow.Flow

interface RemoteConfigDataSource {
    fun checkUpdate() : Flow<Boolean>
}