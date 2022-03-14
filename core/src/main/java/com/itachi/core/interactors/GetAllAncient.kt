package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class GetAllAncient(
    private val ancientRepository: AncientRepository
) {

    suspend fun fromRoom() = ancientRepository.getAllAncientsFromRoom()

    suspend fun fromFirebase(
        onSuccess: (List<AncientVO>) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientRepository.getAllAncientsFromFirebase(onSuccess, onFailure)
}