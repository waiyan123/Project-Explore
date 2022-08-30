package com.itachi.explore.framework

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.network.ViewFirebaseDataSource
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.framework.mappers.PagodaMapper
import com.itachi.explore.framework.mappers.ViewMapper
import com.itachi.explore.persistence.entities.PagodaEntity
import com.itachi.explore.persistence.entities.UploadedPhotoEntity
import com.itachi.explore.utils.*
import io.reactivex.Observable
import javax.inject.Inject

class ViewFirebaseDataSourceImpl @Inject constructor(private val viewMapper: ViewMapper,
                                 firestoreRef : FirebaseFirestore,
                                 firebaseStorage : FirebaseStorage,
                                 auth : FirebaseAuth
) : ViewFirebaseDataSource,FirebaseDataSourceImpl(firestoreRef,firebaseStorage,auth){
    override suspend fun getPhotoViews(
        onSuccess: (uploadedPhotoList: List<UploadedPhotoVO>) -> Unit,
        onFailure: (error: String) -> Unit
    ) {
        firestoreRef.collection(UPLOADED_PHOTO)
            .get()
            .addOnSuccessListener {
                if (it.documents.size != 0) {

                    val viewPhotoList = it.toObjects(UploadedPhotoEntity::class.java)
                    onSuccess(viewMapper.uploadedPhotoEntityToVoList(viewPhotoList))
                }
                else {
                    onFailure("There's no item yet ")
                }
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage.toString())
            }
    }

    override suspend fun getViewsList(
        onSuccess: (List<ViewVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(VIEWS)
            .get()
            .addOnSuccessListener {
                if (it.documents.size != 0) {

                    val viewsList = it.toObjects(ViewVO::class.java)
                    onSuccess(viewsList)
                }
                else {
                    onFailure("There's no item yet ")
                }
            }
    }

    override suspend fun getViewById(
        viewId: String,
        onSuccess: (ViewVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

        firestoreRef.collection(VIEWS).whereEqualTo(ITEM_ID, viewId)

            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val viewVO = it.documents[0].toObject(ViewVO::class.java)
                    onSuccess(viewVO!!)
                }
            }
            .addOnFailureListener {

            }
    }

    override suspend fun getViewsListByUserId(
        userId: String,
        onSuccess: (List<ViewVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {

        firestoreRef.collection(VIEWS).whereEqualTo(UPLOADER_ID, userId)

            .get()
            .addOnSuccessListener {snapShot->
                if (snapShot.documents.isNotEmpty()) {
                    val itemList = snapShot.toObjects(ViewVO::class.java)
                    onSuccess(itemList)
                }
            }
            .addOnFailureListener {
                onFailure(it.toString())
            }
    }

    override suspend fun deleteViewById(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(VIEWS)
            .document(viewVO.item_id)
            .delete()
            .addOnSuccessListener {
                onSuccess("Successfully deleted...")
            }
            .addOnFailureListener {
                onFailure("Failed to delete $it")
            }
    }

    override suspend fun addView(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val firestoreView = hashMapOf(
            ABOUT to viewVO.about,
            COMMENTS to viewVO.comments,
            CREATED_DATE to viewVO.created_date,
            FESTIVAL_DATE to viewVO.festival_date,
            IS_THERE_FESTIVAL to viewVO.is_there_festival,
            ITEM_ID to viewVO.item_id,
            ITEM_TYPE to viewVO.item_type,
            PHOTOS to viewVO.photos,
            TITLE to viewVO.title,
            UPLOADER_ID to viewVO.uploader_id
        )

        firestoreRef.collection(VIEWS)
            .document(viewVO.item_id)
            .set(firestoreView)
            .addOnSuccessListener {
                onSuccess("Successfully added View")
            }
            .addOnFailureListener {
                onFailure("Failed to add View")
            }
    }

    override suspend fun updateView(
        viewVO: ViewVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val firestoreView = hashMapOf(
            ABOUT to viewVO.about,
            COMMENTS to viewVO.comments,
            CREATED_DATE to viewVO.created_date,
            FESTIVAL_DATE to viewVO.festival_date,
            IS_THERE_FESTIVAL to viewVO.is_there_festival,
            ITEM_ID to viewVO.item_id,
            ITEM_TYPE to viewVO.item_type,
            PHOTOS to viewVO.photos,
            TITLE to viewVO.title,
            UPLOADER_ID to viewVO.uploader_id
        )
        firestoreRef.collection(VIEWS)
            .document(viewVO.item_id)
            .update(firestoreView as Map<String, Any>)
            .addOnSuccessListener {
                onSuccess("Successfully updated View")
            }
            .addOnFailureListener {
                onFailure("Failed to update View")
            }
    }
}