package com.itachi.explore.framework

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.common.Resource
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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class ViewFirebaseDataSourceImpl(private val viewMapper: ViewMapper,
                                 firestoreRef : FirebaseFirestore,
                                 firebaseStorage : FirebaseStorage,
                                 auth : FirebaseAuth
) : ViewFirebaseDataSource,FirebaseDataSourceImpl(firestoreRef,firebaseStorage,auth){

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getViewsPhotos(): Flow<Resource<List<UploadedPhotoVO>>> = callbackFlow{
        firestoreRef.collection(UPLOADED_PHOTO)
            .get()
            .addOnSuccessListener {
                if (it.documents.size != 0) {
                    val viewPhotoList = it.toObjects(UploadedPhotoEntity::class.java)
                    trySend(Resource.Success(viewMapper.uploadedPhotoEntityToVoList(viewPhotoList)))
                }
                else {
                    trySend(Resource.Error("There's no item yet "))
                }
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getViewById(viewId: String) : Flow<Resource<ViewVO>> = callbackFlow{

        firestoreRef.collection(VIEWS).whereEqualTo(ITEM_ID, viewId)
            .get()
            .addOnSuccessListener {snapShot->
                if (snapShot.documents.isNotEmpty()) {
                    val viewVO = snapShot.documents[0].toObject(ViewVO::class.java)
                    viewVO?.let {
                        trySend(Resource.Success(it))
                    }
                }
                else trySend(Resource.Error("There's no item yet"))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllViews() : Flow<Resource<List<ViewVO>>> = callbackFlow{
        firestoreRef.collection(VIEWS)
            .get()
            .addOnSuccessListener {snapShot->
                if (snapShot.documents.isNotEmpty()) {
                    val viewsList = snapShot.toObjects(ViewVO::class.java)
                    trySend(Resource.Success(viewsList))
                }
                else trySend(Resource.Error("There's no item yet "))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getViewsListByUserId(userId: String) : Flow<Resource<List<ViewVO>>> = callbackFlow{

        firestoreRef.collection(VIEWS).whereEqualTo(UPLOADER_ID, userId)
            .get()
            .addOnSuccessListener {snapShot->
                if (snapShot.documents.isNotEmpty()) {
                    val itemList = snapShot.toObjects(ViewVO::class.java)
                    trySend(Resource.Success(itemList))
                }
                else trySend(Resource.Error("There's no item yet "))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun deleteViewById(viewVO: ViewVO) : Flow<Resource<String>> = callbackFlow {
        firestoreRef.collection(VIEWS)
            .document(viewVO.item_id)
            .delete()
            .addOnSuccessListener {
                trySend(Resource.Success("Successfully deleted..."))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur in deletion!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun addView(viewVO: ViewVO) : Flow<Resource<String>> = callbackFlow{

        firestoreRef.collection(VIEWS)
            .document(viewVO.item_id)
            .set(viewMapper.voToFirebaseHashmap(viewVO))
            .addOnSuccessListener {
                trySend(Resource.Success("Successfully added View"))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur in adding!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun updateView(viewVO: ViewVO) : Flow<Resource<String>> = callbackFlow{

        firestoreRef.collection(VIEWS)
            .document(viewVO.item_id)
            .update(viewMapper.voToFirebaseHashmap(viewVO))
            .addOnSuccessListener {
                trySend(Resource.Success("Successfully updated View"))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur in updating!"))
            }
        awaitClose { channel.close() }
    }
}