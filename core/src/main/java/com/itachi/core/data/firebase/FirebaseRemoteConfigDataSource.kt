package com.itachi.core.data.firebase

import kotlinx.coroutines.flow.Flow

interface FirebaseRemoteConfigDataSource {
    fun checkUpdate() : Flow<Boolean>
}