package com.itachi.explore.mvvm.model

import android.content.Context
import com.itachi.core.domain.PhotoVO
import io.reactivex.Observable

interface UploadModel {

    fun uploadPhoto(
            path: ArrayList<String>,
            geoPointsList : ArrayList<String>,
            mContext : Context,
            onSuccess : (Observable<ArrayList<PhotoVO>>) -> Unit
    )

    fun uploadPhotoUrl(path: String, userId: String,itemId: String,itemType : String,geoPoints : String)

}