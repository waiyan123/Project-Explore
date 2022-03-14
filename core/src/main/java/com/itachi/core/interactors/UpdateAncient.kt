package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class UpdateAncient(
    private val ancientRepository: AncientRepository
) {
    suspend fun toRoom(ancientVO: AncientVO) = ancientRepository.updateAncientToRoom(ancientVO)

    suspend fun toFirebase(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientRepository.updateAncientToFirebase(ancientVO, onSuccess, onFailure)
}