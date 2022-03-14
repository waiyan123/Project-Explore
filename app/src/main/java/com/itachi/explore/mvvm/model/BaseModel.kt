package com.itachi.explore.mvvm.model

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.utils.ITEM_ID
import com.itachi.explore.utils.PHOTO_PATH
import com.itachi.explore.utils.UPLOADED_PHOTO

abstract class BaseModel {

    protected var firestoreRef : FirebaseFirestore = FirebaseFirestore.getInstance()
    protected var firebaseStorage : FirebaseStorage = FirebaseStorage.getInstance()
    protected var auth = FirebaseAuth.getInstance()

    fun deletePhotos(photos: ArrayList<PhotoVO>?, item_id: String) {
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

    fun deleteUserPhoto(photo : PhotoVO?,item_id: String) {
        val photoRef = firebaseStorage.getReference(PHOTO_PATH).child(photo!!.path!!)
        photoRef.delete().addOnCompleteListener {

        }
    }
}