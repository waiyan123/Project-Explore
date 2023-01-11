package com.itachi.explore.framework

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.itachi.core.data.firebase.FirebaseRemoteConfigDataSource
import com.itachi.explore.utils.VERSION_CODE_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn

class FirebaseRemoteConfigDataSourceImpl(
    private val remoteConfig: FirebaseRemoteConfig
) : FirebaseRemoteConfigDataSource {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun checkUpdate(): Flow<Boolean> = callbackFlow {

        remoteConfig.fetchAndActivate()
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    Log.d("test---",remoteConfig.getDouble(VERSION_CODE_KEY).toString())
                    val updated = it.result
                    if (!updated) {
                        trySend(true)
                    } else trySend(false)
                }
            }
            .addOnFailureListener {
                Log.d("test---", "${it.localizedMessage}")
            }
        awaitClose { channel.close() }
    }
        .flowOn(Dispatchers.IO)
}