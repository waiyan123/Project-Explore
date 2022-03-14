package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class AddAncient(
    private val ancientRepository: AncientRepository
) {

    suspend fun toRoom(ancientVO: AncientVO) = ancientRepository.addAncientToRoom(ancientVO)

    suspend fun toFirebase(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientRepository.addAncientToFirebase(ancientVO, onSuccess, onFailure)

}