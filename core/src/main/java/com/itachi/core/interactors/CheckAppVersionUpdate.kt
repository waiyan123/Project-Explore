package com.itachi.core.interactors

import com.itachi.core.data.RemoteConfigDataSource

class CheckAppVersionUpdate(
    private val remoteConfigDataSource : RemoteConfigDataSource
) {
    operator fun invoke() = remoteConfigDataSource.checkUpdate()
}