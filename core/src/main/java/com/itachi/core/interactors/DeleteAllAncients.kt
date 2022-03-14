package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class DeleteAllAncients(private val ancientRepository: AncientRepository) {

    suspend fun fromRoom(ancientList : List<AncientVO>) = ancientRepository.deleteAllAncientsFromRoom(ancientList)

    suspend fun fromFirebase(
        ancientList : List<AncientVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientRepository.deleteAllAncientsFromFirebase(ancientList,onSuccess,onFailure)
}