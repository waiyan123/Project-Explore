package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.data.room.AncientRoomDataSource
import com.itachi.core.data.firebase.AncientFirebaseDataSource
import com.itachi.core.domain.models.AncientVO
import com.itachi.core.domain.repositories.AncientRepository
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*

@OptIn(FlowPreview::class)
class AncientRepositoryImpl(
    private val ancientFirebaseDataSource: AncientFirebaseDataSource,
    private val ancientRoomDataSource: AncientRoomDataSource
) : AncientRepository {

    override fun addAncient(ancientVO: AncientVO): Flow<Resource<String>> =
        ancientFirebaseDataSource.addAncient(ancientVO)
            .onEach { ancientRoomDataSource.addAncient(ancientVO) }

    override suspend fun addAllAncients(ancientList: List<AncientVO>) {
        ancientRoomDataSource.addAllAncients(ancientList)
    }

    override fun getAncientBackground(): Flow<Resource<String>> {
        return ancientFirebaseDataSource.getAncientBackground()
    }

    override fun getAllAncients(): Flow<Resource<List<AncientVO>>> =
        ancientRoomDataSource.getAllAncients()
            .flatMapConcat { ancientFirebaseDataSource.getAllAncients() }
            .onEach { resource ->
                resource.data?.let {
                    ancientRoomDataSource.addAllAncients(it)
                }
            }

    override fun getAncientById(ancientId: String): Flow<Resource<AncientVO>> =
        ancientRoomDataSource.getAncientById(ancientId)
            .flatMapConcat { ancientFirebaseDataSource.getAncientById(ancientId) }
            .onEach { resource ->
                resource.data?.let {
                    ancientRoomDataSource.addAncient(it)
                }
            }

    override fun getAncientsListByUserId(userId: String): Flow<Resource<List<AncientVO>>> {
        return ancientFirebaseDataSource.getAncientsListByUserId(userId)
    }

    override fun updateAncient(ancientVO: AncientVO): Flow<Resource<String>> =
        ancientFirebaseDataSource.updateAncient(ancientVO)
            .onEach { ancientRoomDataSource.update(ancientVO) }

    override fun deleteAncient(ancientVO: AncientVO): Flow<Resource<String>> =
        ancientFirebaseDataSource.deleteAncient(ancientVO)
            .onEach { ancientRoomDataSource.deleteAncient(ancientVO) }

    override suspend fun deleteAllAncients() {
        ancientRoomDataSource.deleteAllAncients()
    }

}