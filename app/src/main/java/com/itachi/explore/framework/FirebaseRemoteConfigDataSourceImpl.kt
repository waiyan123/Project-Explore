package com.itachi.explore.framework

import android.util.Log
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import com.itachi.core.data.network.FirebaseRemoteConfigDataSource
import com.itachi.explore.BuildConfig
import com.itachi.explore.utils.VERSION_CODE_KEY
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import java.util.HashMap

class FirebaseRemoteConfigDataSourceImpl(
    private val remoteConfig : FirebaseRemoteConfig
) : FirebaseRemoteConfigDataSource {

    override fun checkUpdate() : Flow<Boolean> = callbackFlow {
        val firebaseDefaultMap = HashMap<String, Any>()
        firebaseDefaultMap[VERSION_CODE_KEY] = BuildConfig.VERSION_CODE
        remoteConfig.setDefaultsAsync(firebaseDefaultMap)

        remoteConfig.setConfigSettingsAsync(
            FirebaseRemoteConfigSettings.Builder()
                .build()
        )

        remoteConfig.fetch().addOnCompleteListener {
            if (it.isSuccessful) {
                remoteConfig.activate()
                if (remoteConfig.getDouble(VERSION_CODE_KEY) > BuildConfig.VERSION_CODE) {
                    trySend(true)
                } else trySend(false)
            }
        }
        awaitClose { channel.close() }
    }
}