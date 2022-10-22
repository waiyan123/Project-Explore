package com.itachi.core.data.network

import kotlinx.coroutines.flow.Flow

interface FirebaseRemoteConfigDataSource {
    fun checkUpdate() : Flow<Boolean>
}