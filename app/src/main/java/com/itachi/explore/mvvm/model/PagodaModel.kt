package com.itachi.explore.mvvm.model

import androidx.lifecycle.LiveData
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.explore.persistence.entities.PagodaEntity
import io.reactivex.Observable

interface PagodaModel {

    fun getPagodaBanner(
        onSuccess : (ArrayList<String>) -> Unit
    )

    fun getPagodasList(
        onFailure : (String) -> Unit
    ) : LiveData<List<PagodaEntity>>

    fun getPagodaById(
        pagodaId : String,
        onSuccess : (Observable<PagodaVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    fun getPagodaListByUserId(
        userId : String,
        onSuccess : (Observable<ArrayList<ItemVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun deletePagodaById(
        pagoda: PagodaVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addPagoda(pagodaVO: PagodaVO) : LiveData<String>

    fun updatePagoda(pagodaVO : PagodaVO) : LiveData<String>

}