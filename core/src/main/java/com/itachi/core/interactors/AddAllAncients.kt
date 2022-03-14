package com.itachi.core.interactors

import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO

class AddAllAncients (private val ancientRepository: AncientRepository){

    suspend fun toRoom(ancientList : List<AncientVO>) = ancientRepository.addAllAncientsToRoom(ancientList)

    suspend fun toFirebase(
        ancientList : List<AncientVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) = ancientRepository.addAllAncientsToFirebase(ancientList,onSuccess,onFailure)
}