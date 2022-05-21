package com.itachi.explore.framework

import android.annotation.SuppressLint
import android.net.Uri
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.utils.*
import io.reactivex.Observable
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.map
import java.util.*
import kotlin.collections.ArrayList

abstract class FirebaseDataSourceImpl(
    protected val firestoreRef: FirebaseFirestore,
    protected val firebaseStorage: FirebaseStorage,
    protected val auth: FirebaseAuth
) {
    suspend fun deletePhotos(photos: List<PhotoVO>?, item_id: String) {
        photos?.forEach {
            val photoRef = firebaseStorage.getReference(PHOTO_PATH).child(it.path!!)
            photoRef.delete().addOnCompleteListener {

            }
        }

        val snapQuery = firestoreRef.collection(UPLOADED_PHOTO)
            .whereEqualTo(ITEM_ID, item_id)

        snapQuery.get()
            .addOnSuccessListener {
                it.documents.let { list ->

                    list.forEach { doc ->
                        doc.reference.delete()
                    }
                }
            }

    }

    suspend fun deleteUserPhoto(photo: PhotoVO?, item_id: String) {
        val photoRef = firebaseStorage.getReference(PHOTO_PATH).child(photo!!.path!!)
        photoRef.delete().addOnCompleteListener {

        }
    }

    @SuppressLint("CheckResult")
    suspend fun uploadPhoto(
        byteArrayList: ArrayList<ByteArray>,
        geoPointsList: ArrayList<String>,
        onSuccess: (ArrayList<PhotoVO>) -> Unit,
        onFailure: (String) -> Unit
    ) : MutableLiveData<String>{

        var count = 0
        var successMsg = MutableLiveData<String>()

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

                            onSuccess(photoVOList)
                            successMsg.postValue("SuccessFully uploaded")
                        }
                    }
                }.addOnFailureListener {
                    onFailure(it.localizedMessage)
                }
            }
        }
    return successMsg
    }

     fun uploadPhotoUrl(
        path: String,
        userId: String,
        itemId: String,
        itemType: String,
        geoPoints: String
    ) {
        val uploadedPhoto = hashMapOf(
            PHOTO_URL to path,
            UPLOADER_ID to userId,
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