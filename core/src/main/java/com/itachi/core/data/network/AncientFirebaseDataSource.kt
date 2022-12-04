package com.itachi.core.data.network

import com.itachi.core.common.Resource
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.ItemVO
import kotlinx.coroutines.flow.Flow

interface AncientFirebaseDataSource {

    fun addAncient(ancientVO : AncientVO) : Flow<Resource<String>>

    fun addAllAncients(ancientList : List<AncientVO>) : Flow<Resource<String>>

    fun getAncientBackground() : Flow<Resource<String>>

    fun getAllAncients() : Flow<Resource<List<AncientVO>>>

    fun getAncientById(ancientId : String) : Flow<Resource<AncientVO>>

    fun getAncientsListByUserId(userId : String) : Flow<Resource<List<AncientVO>>>

    fun deleteAncient(ancientVO: AncientVO) : Flow<Resource<String>>

    fun updateAncient(ancientVO : AncientVO) : Flow<Resource<String>>
}