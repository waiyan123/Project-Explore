package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class DeleteAncient(
    private val ancientRepository: AncientRepository
) {

    suspend fun fromRoom(ancientVO: AncientVO) = ancientRepository.deleteAncientFromRoom(ancientVO)

    suspend fun fromFirebase(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientRepository.deleteAncientFromFirebase(ancientVO, onSuccess, onFailure)
}