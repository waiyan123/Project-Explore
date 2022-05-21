package com.itachi.explore.mvvm.model

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.storage.UploadTask
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*
import kotlin.collections.ArrayList

class UploadModelImpl : UploadModel, BaseModel() {

    @SuppressLint("CheckResult")
    override fun uploadPhoto(
        byteArrayList: ArrayList<ByteArray>,
        geoPointsList: ArrayList<String>,
        onSuccess: (Observable<ArrayList<PhotoVO>>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        var count = 0

        var photoVOList = ArrayList<PhotoVO>()
        byteArrayList.forEachIndexed { index, data ->

            var downloadUrl: String
            if (data != null) {
                val photoUrl = UUID.randomUUID().toString()
                val ref = firebaseStorage.reference.child(PHOTO_PATH).child(photoUrl)
                val uploadTask = ref.putBytes(data)

                uploadTask.continueWithTask(Continuation<UploadTask.TaskSnapshot, Task<Uri>> { task ->
                    if (!task.isSuccessful) {
                        task.exception?.let {
                            throw it
                        }
                    }
                    return@Continuation ref.downloadUrl
                }).addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        count++
                        val downloadUri = task.result
                        downloadUrl = downloadUri.toString()
                        val photoVO = PhotoVO(photoUrl, downloadUrl, geoPointsList[index])
                        photoVOList.add(photoVO)

                        if (count == byteArrayList.size) {

                            onSuccess(Observable.just(photoVOList))
                        }
                    }
                }.addOnFailureListener {
                    onFailure(it.localizedMessage)
                }
            }
        }

    }

    override fun uploadPhotoUrl(
        path: String,
        facebookId: String,
        itemId: String,
        itemType: String,
        geoPoints: String
    ) {
        val uploadedPhoto = hashMapOf(
            PHOTO_URL to path,
            UPLOADER_ID to facebookId,
            ITEM_ID to itemId,
            ITEM_TYPE to itemType,
            GEO_POINTS to geoPoints
        )

        firestoreRef.collection(UPLOADED_PHOTO)
            .add(uploadedPhoto)
            .addOnSuccessListener {

            }
            .addOnFailureListener {
            }
    }

}