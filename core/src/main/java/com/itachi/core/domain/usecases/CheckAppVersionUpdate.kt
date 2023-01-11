package com.itachi.core.domain.usecases

import com.itachi.core.domain.repositories.RemoteConfigRepository

class CheckAppVersionUpdate(
    private val remoteConfigRepository : RemoteConfigRepository
) {
    operator fun invoke() = remoteConfigRepository.checkUpdate()
}