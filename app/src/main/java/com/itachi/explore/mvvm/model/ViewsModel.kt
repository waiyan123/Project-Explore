package com.itachi.explore.mvvm.model

import androidx.lifecycle.LiveData
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import io.reactivex.Observable

interface ViewsModel {

    fun getPhotoViews(
        onSuccess : (uploadedPhotoList : ArrayList<UploadedPhotoVO>) -> Unit,
        onFailure : (error : String) -> Unit
    )

    fun getViewsList(
        onSuccess : (ArrayList<ViewVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    fun getViewById(
        viewId : String,
        onSuccess : (Observable<ViewVO>) -> Unit,
        onFailure : (String) -> Unit
    )

    fun getViewsListByUserId(
        userId : String,
        onSuccess : (Observable<ArrayList<ItemVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun deleteViewById(
        view: ViewVO,
        onSuccess : (String) -> Unit,
        onFailure: (String) -> Unit
    )

    fun addView(viewVO : ViewVO) : LiveData<String>

    fun updateView(viewVO: ViewVO) : LiveData<String>
}