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
) : PagodaRepository{

    override fun getPagodaBanner() : Flow<Resource<List<String>>> = pagodaFirebaseDataSource.getPagodaBanner()

    override fun getAllPagodas() : Flow<Resource<List<PagodaVO>>> = flow {
        pagodaRoomDataSource.getAllPagodas()
            .onEach {
                emit(Resource.Success(it))
            }
            .flatMapConcat {
                pagodaFirebaseDataSource.getAllPagodas()
            }
            .collect { resourceFirebase->
                resourceFirebase.data?.let { pagodaList->
                    pagodaRoomDataSource.addAllPagodas(pagodaList)
                    emit(resourceFirebase)
                }
            }
    }

    override fun getPagodaById(pagodaId : String) : Flow<Resource<PagodaVO>> = flow {
        pagodaRoomDataSource.getPagodaById(pagodaId)
            .onEach {
                emit(Resource.Success(it))
            }
            .flatMapConcat {
                pagodaFirebaseDataSource.getPagodaById(pagodaId)
            }
            .collect { resourceFirebase->
                resourceFirebase.data?.let {pagodaVO->
                    pagodaRoomDataSource.addPagoda(pagodaVO)
                    emit(resourceFirebase)
                }
            }

    }

    override fun getPagodaListByUploaderId(uploaderId : String) : Flow<Resource<List<PagodaVO>>> = pagodaFirebaseDataSource.getPagodaListByUserId(uploaderId)

    override fun deletePagoda(pagodaVO: PagodaVO) : Flow<Resource<String>> = flow {

        pagodaFirebaseDataSource.deletePagodaById(pagodaVO.item_id)
            .collect {
                emit(it)
                pagodaRoomDataSource.deletePagoda(pagodaVO)
            }
    }

    override suspend fun deleteAllPagodas() = pagodaRoomDataSource.deleteAllPagodas()

    override fun addPagoda(pagodaVO: PagodaVO) : Flow<Resource<String>> = flow {

        pagodaFirebaseDataSource.addPagoda(pagodaVO)
            .collect {
                pagodaRoomDataSource.addPagoda(pagodaVO)
                emit(it)
            }
    }

    override fun updatePagoda(pagodaVO: PagodaVO) : Flow<Resource<String>> = flow {
        pagodaFirebaseDataSource.updatePagoda(pagodaVO)
            .collect {
                emit(it)
                pagodaRoomDataSource.updatePagoda(pagodaVO)
            }
    }

}