package com.itachi.explore.framework

import android.content.Context
import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.itachi.core.common.Resource
import com.itachi.core.data.network.PhotoFirebaseDataSource
import com.itachi.core.domain.PhotoVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.explore.framework.mappers.UploadedPhotoMapper
import com.itachi.explore.framework.mappers.UploadedPhotoMapperFunctions
import com.itachi.explore.utils.ITEM_ID
import com.itachi.explore.utils.PHOTO_PATH
import com.itachi.explore.utils.UPLOADED_PHOTO
import com.itachi.explore.utils.Util
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import java.util.*
import kotlin.collections.ArrayList

class PhotoFirebaseDataSourceImpl(
    private val uploadedPhotoMapper : UploadedPhotoMapperFunctions,
    private val firestoreRef : FirebaseFirestore,
    private val firebaseStorage: FirebaseStorage,
    private val context: Context
) : PhotoFirebaseDataSource{

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun uploadPhotos(
        filePathList : List<String>
    ): Flow<Resource<List<PhotoVO>>> = callbackFlow{
        trySend(Resource.Loading())

        val byteArrayList = Util.compressImage(filePathList,context)
        val geoPointsList = Util.getGeoPointsFromImageList(context,filePathList)
        var count = 0

        val photoVOList = ArrayList<PhotoVO>()
        byteArrayList.forEachIndexed { index, data ->

            var downloadUrl: String
            data.let {
                val photoId = UUID.randomUUID().toString()
                val ref = firebaseStorage.reference.child(PHOTO_PATH).child(photoId)
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
                        val photoVO = PhotoVO(photoId, downloadUrl, geoPointsList[index])
                        photoVOList.add(photoVO)

                        if (count == byteArrayList.size) {
                            trySend(Resource.Success(photoVOList))
                        }
                    }
                }.addOnFailureListener {
                    trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
                }
            }
        }
        awaitClose { channel.close() }

    }

    override suspend fun deletePhotos(photoList: List<PhotoVO>, itemId: String) {
        photoList.forEach {
            val photoRef = firebaseStorage.getReference(PHOTO_PATH).child(it.id)
            photoRef.delete().addOnCompleteListener {

            }
        }

        val snapQuery = firestoreRef.collection(UPLOADED_PHOTO)
            .whereEqualTo(ITEM_ID, itemId)

        snapQuery.get()
            .addOnSuccessListener {
                it.documents.forEach { doc->
                    doc.reference.delete()
                }
            }

    }

    override suspend fun uploadPhotoUrl(uploadedPhotoVO: UploadedPhotoVO) {
        firestoreRef.collection(UPLOADED_PHOTO)
            .add(uploadedPhotoMapper.voToFirebaseHashmap(uploadedPhotoVO))
            .addOnSuccessListener {

            }
            .addOnFailureListener {
            }
    }


}