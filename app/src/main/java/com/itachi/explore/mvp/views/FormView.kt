package com.itachi.explore.mvp.views

import android.net.Uri

interface FormView : BaseView{

    fun showPickUpImages(list : ArrayList<Uri>)

    fun successAddingItem(name : String)

    fun successUpdatingItem(message : String)

    fun showPhotoError(error : String)

    fun showErrorMessage(error : String)

    fun showProgressLoading()

    fun showEditDetails(name : String,created : String, festival : String,about : String,type : String)

}