package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class GetAncient(
    private val ancientRepository: AncientRepository
) {
    suspend fun fromRoom(id: String) = ancientRepository.getAncientByIdFromRoom(id)

    suspend fun fromFirebase(
        id: String,
        onSuccess: (AncientVO) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientRepository.getAncientByIdFromFirebase(id, onSuccess, onFailure)

    suspend fun getBackground(
        bgUrl: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientRepository.getAncientBackgroundFromFirebase(bgUrl,onFailure)
}