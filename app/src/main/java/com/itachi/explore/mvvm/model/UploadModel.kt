package com.itachi.explore.mvvm.model

import android.content.Context
import com.itachi.core.domain.PhotoVO
import io.reactivex.Observable

interface UploadModel {

    fun uploadPhoto(
        byteArrayList: ArrayList<ByteArray>,
        geoPointsList: ArrayList<String>,
        onSuccess: (Observable<ArrayList<PhotoVO>>) -> Unit,
        onFailure: (String) -> Unit
    )

    fun uploadPhotoUrl(
        path: String,
        userId: String,
        itemId: String,
        itemType: String,
        geoPoints: String
    )

}