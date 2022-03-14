package com.itachi.explore.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.utils.ITEM_ID
import com.itachi.explore.utils.PHOTO_PATH
import com.itachi.explore.utils.UPLOADED_PHOTO

abstract class FirebaseDataSourceImpl(protected val firestoreRef : FirebaseFirestore,
                                  protected val firebaseStorage : FirebaseStorage,
                                  protected val auth : FirebaseAuth)                        {
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

    suspend fun deleteUserPhoto(photo : PhotoVO?, item_id: String) {
        val photoRef = firebaseStorage.getReference(PHOTO_PATH).child(photo!!.path!!)
        photoRef.delete().addOnCompleteListener {

        }
    }
}