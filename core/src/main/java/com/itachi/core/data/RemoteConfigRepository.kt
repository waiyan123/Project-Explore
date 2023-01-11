package com.itachi.core.data

import com.itachi.core.data.firebase.FirebaseRemoteConfigDataSource
import com.itachi.core.domain.repositories.RemoteConfigRepository
import kotlinx.coroutines.flow.Flow

class RemoteConfigRepository(
    private val firebaseRemoteConfigDataSource: FirebaseRemoteConfigDataSource
) : RemoteConfigRepository {

    override fun checkUpdate(): Flow<Boolean> = firebaseRemoteConfigDataSource.checkUpdate()

}