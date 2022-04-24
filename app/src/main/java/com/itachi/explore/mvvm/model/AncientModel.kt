//package com.itachi.explore.mvvm.model
//
//import androidx.lifecycle.LiveData
//import com.itachi.core.domain.AncientVO
//import com.itachi.core.domain.ItemVO
//import io.reactivex.Observable
//
//interface AncientModel {
//
//    fun getAncientBackground(
//        onSuccess : (String) -> Unit,
//        onFailure: (String) -> Unit
//    )
//
//    fun getAncientList(
//        onSuccess : (ArrayList<AncientVO>) -> Unit,
//        onFailure : (String) -> Unit
//    )
//
//    fun getAncientById(
//        ancientId : String,
//        onSuccess : (Observable<AncientVO>) -> Unit,
//        onFailure : (String) -> Unit
//    )
//
//    fun getAncientsListByUserId(
//        userId : String,
//        onSuccess : (Observable<ArrayList<ItemVO>>) -> Unit,
//        onFailure: (String) -> Unit
//    )
//
//    fun deleteAncientById(
//        ancient: AncientVO,
//        onSuccess : (String) -> Unit,
//        onFailure: (String) -> Unit
//    )
//
//    fun addAncient(ancientVO : AncientVO) : LiveData<String>
//
//    fun updateAncient(ancientVO: AncientVO) : LiveData<String>
//}