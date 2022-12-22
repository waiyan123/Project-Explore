package com.itachi.core.data

import com.itachi.core.common.Resource
import com.itachi.core.data.db.PagodaRoomDataSource
import com.itachi.core.data.network.PagodaFirebaseDataSource
import com.itachi.core.data.network.PhotoFirebaseDataSource
import com.itachi.core.domain.PagodaVO
import kotlinx.coroutines.flow.*

class PagodaRepositoryImpl(
    private val pagodaFirebaseDataSource: PagodaFirebaseDataSource,
    private val pagodaRoomDataSource: PagodaRoomDataSource
) : PagodaRepository {

    override fun getPagodaBanner(): Flow<Resource<List<String>>> =
        pagodaFirebaseDataSource.getPagodaBanner()

    override fun getAllPagodas(): Flow<Resource<List<PagodaVO>>> =
        pagodaRoomDataSource.getAllPagodas()
            .flatMapConcat { pagodaFirebaseDataSource.getAllPagodas() }
            .onEach { resource ->
                resource.data?.let {
                    pagodaRoomDataSource.addAllPagodas(it)
                }
            }

    override fun getPagodaById(pagodaId: String): Flow<Resource<PagodaVO>> =
        pagodaRoomDataSource.getPagodaById(pagodaId)
            .flatMapConcat { pagodaFirebaseDataSource.getPagodaById(pagodaId) }
            .onEach { resource ->
                resource.data?.let {
                    pagodaRoomDataSource.addPagoda(it)
                }
            }


    override fun getPagodaListByUploaderId(uploaderId: String): Flow<Resource<List<PagodaVO>>> =
        pagodaFirebaseDataSource.getPagodaListByUserId(uploaderId)

    override fun deletePagoda(pagodaVO: PagodaVO): Flow<Resource<String>> = pagodaFirebaseDataSource
        .deletePagodaById(pagodaVO.item_id)
        .onEach { pagodaRoomDataSource.deletePagoda(pagodaVO) }

    override suspend fun deleteAllPagodas() = pagodaRoomDataSource.deleteAllPagodas()

    override fun addPagoda(pagodaVO: PagodaVO): Flow<Resource<String>> =
        pagodaFirebaseDataSource.addPagoda(pagodaVO)
            .onEach { pagodaRoomDataSource.addPagoda(pagodaVO) }


    override fun updatePagoda(pagodaVO: PagodaVO): Flow<Resource<String>> =
        pagodaFirebaseDataSource.updatePagoda(pagodaVO)
            .onEach { pagodaRoomDataSource.updatePagoda(pagodaVO) }

}