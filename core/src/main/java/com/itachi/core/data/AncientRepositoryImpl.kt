package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.data.db.AncientRoomDataSource
import com.itachi.core.data.network.AncientFirebaseDataSource
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.ItemVO
import kotlinx.coroutines.flow.*

class AncientRepositoryImpl(
    private val ancientFirebaseDataSource: AncientFirebaseDataSource,
    private val ancientRoomDataSource: AncientRoomDataSource
) : AncientRepository{

    override fun addAncient(ancientVO: AncientVO): Flow<Resource<String>> = flow{
        ancientFirebaseDataSource.addAncient(ancientVO)
            .collect { resourceFirebase->
                when(resourceFirebase) {
                    is Resource.Success -> {
                        resourceFirebase.data?.let {
                            ancientRoomDataSource.addAncient(ancientVO)
                            emit(Resource.Success(it))
                        }
                    }
                    is Resource.Error -> {
                        resourceFirebase.message?.let {
                            emit(Resource.Error(it))
                        }
                    }
                }
            }
    }

    override suspend fun addAllAncients(ancientList: List<AncientVO>) {
        ancientRoomDataSource.addAllAncients(ancientList)
    }

    override fun getAncientBackground(): Flow<Resource<String>> {
        return ancientFirebaseDataSource.getAncientBackground()
    }

    override fun getAllAncients(): Flow<Resource<List<AncientVO>>> = flow {
        ancientRoomDataSource.getAllAncients()
            .onEach {
                emit(Resource.Success(it))
            }
            .flatMapConcat {
                ancientFirebaseDataSource.getAllAncients()
            }
            .collect {
                emit(it)
            }
    }

    override fun getAncientById(ancientId: String): Flow<Resource<AncientVO>> = flow{
        ancientRoomDataSource.getAncientById(ancientId)
            .onEach {
                emit(Resource.Success(it))
            }
            .flatMapConcat {
                ancientFirebaseDataSource.getAncientById(ancientId)
            }
            .collect {
                emit(it)
            }
    }

    override fun getAncientsListByUserId(userId: String): Flow<Resource<List<AncientVO>>> {
        return ancientFirebaseDataSource.getAncientsListByUserId(userId)
    }

    override fun updateAncient(ancientVO: AncientVO): Flow<Resource<AncientVO>> = flow{
        ancientFirebaseDataSource.updateAncient(ancientVO)
            .collect {resourceFirebase->
                ancientRoomDataSource.update(ancientVO)
                emit(resourceFirebase)
            }
    }

    override fun deleteAncient(ancientVO: AncientVO): Flow<Resource<String>> = flow{
        ancientFirebaseDataSource.deleteAncient(ancientVO)
            .collect {
                ancientRoomDataSource.deleteAncient(ancientVO)
                emit(it)
            }
    }

    override suspend fun deleteAllAncients() {
        ancientRoomDataSource.deleteAllAncients()
    }

}