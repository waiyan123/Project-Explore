package com.itachi.explore.mvp.views

import com.itachi.core.domain.UploadedPhotoVO

interface ViewsView : BaseView{

    fun showPhotoViews(uploadedPhotoList : ArrayList<UploadedPhotoVO>)

    fun showError(error : String)

    fun navigateViewPhoto(uploadedPhotoVO : UploadedPhotoVO)
}