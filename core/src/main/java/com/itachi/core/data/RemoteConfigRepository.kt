package com.itachi.core.data

import com.itachi.core.data.network.FirebaseRemoteConfigDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RemoteConfigRepository(
    private val firebaseRemoteConfigDataSource: FirebaseRemoteConfigDataSource
) : RemoteConfigDataSource{

    override fun checkUpdate(): Flow<Boolean> = firebaseRemoteConfigDataSource.checkUpdate()

}