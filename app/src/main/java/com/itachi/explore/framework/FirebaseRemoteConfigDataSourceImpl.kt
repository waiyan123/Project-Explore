package com.itachi.explore.framework

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.itachi.core.data.network.FirebaseRemoteConfigDataSource
import com.itachi.explore.BuildConfig
import com.itachi.explore.R
import com.itachi.explore.utils.VERSION_CODE_KEY
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import java.util.HashMap

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