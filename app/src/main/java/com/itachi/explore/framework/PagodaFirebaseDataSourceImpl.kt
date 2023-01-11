package com.itachi.explore.framework

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.common.Resource
import com.itachi.core.data.firebase.PagodaFirebaseDataSource
import com.itachi.core.domain.models.PagodaVO
import com.itachi.explore.framework.mappers.PagodaMapper
import com.itachi.explore.persistence.entities.PagodaEntity
import com.itachi.explore.utils.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class PagodaFirebaseDataSourceImpl(
    private val pagodaMapper: PagodaMapper,
    firestoreRef: FirebaseFirestore,
    firebaseStorage: FirebaseStorage,
    auth: FirebaseAuth
) : PagodaFirebaseDataSource, FirebaseDataSourceImpl(firestoreRef, firebaseStorage, auth) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPagodaBanner() : Flow<Resource<List<String>>> = callbackFlow{
        firestoreRef.collection(BANNERS).document(PAGODA_BANNER)
            .get()
            .addOnSuccessListener {
                if(it.exists()) {
                    val bannerList = it.get(PAGODA_BANNER_PHOTOS) as List<String>?
                    bannerList?.let {list->
                        trySend(Resource.Success(list))
                    }
                }
            }
            .addOnFailureListener{
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getAllPagodas() : Flow<Resource<List<PagodaVO>>> = callbackFlow{
        firestoreRef.collection(PAGODAS)
            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val pagodaList = it.toObjects(PagodaEntity::class.java)
                    Log.d("test---","get all pagodas from firebase ${pagodaList.size}")
                    trySend(Resource.Success(pagodaMapper.entityListToVOList(pagodaList)))

                } else {
                    trySend(Resource.Error("There's no item yet "))
                }
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPagodaById(pagodaId : String) : Flow<Resource<PagodaVO>> = callbackFlow{

        firestoreRef.collection(PAGODAS).whereEqualTo(ITEM_ID, pagodaId)
            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    it.documents[0].toObject(PagodaEntity::class.java)?.let {pagodaEntity->
                        Log.d("test---","get pagoda from firebase ${pagodaEntity.title}")
                        trySend(Resource.Success(pagodaMapper.entityToVO(pagodaEntity)))
                    }
                }
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getPagodaListByUserId(userId : String) : Flow<Resource<List<PagodaVO>>> = callbackFlow{
        firestoreRef.collection(PAGODAS).whereEqualTo(UPLOADER_ID, userId)
            .get()
            .addOnSuccessListener { snapShot ->
                if (snapShot.documents.isNotEmpty()) {
                    val pagodaList = snapShot.toObjects(PagodaEntity::class.java)
                    trySend(Resource.Success(pagodaMapper.entityListToVOList(pagodaList)))
                }
                else trySend(Resource.Error("There's no item yet "))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun deletePagodaById(pagodaId: String) : Flow<Resource<String>> = callbackFlow{

        firestoreRef.collection(PAGODAS)
            .document(pagodaId)
            .delete()
            .addOnSuccessListener {
                trySend(Resource.Success("Successfully deleted Pagoda"))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun addPagoda(pagodaVO: PagodaVO) : Flow<Resource<String>> = callbackFlow{

        val firestorePagoda = pagodaMapper.voToFirebaseHashmap(pagodaVO)

        firestoreRef.collection(PAGODAS)
            .document(pagodaVO.item_id)
            .set(firestorePagoda)
            .addOnSuccessListener {
                trySend(Resource.Success("Successfully added Pagoda"))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    override fun updatePagoda(pagodaVO: PagodaVO) : Flow<Resource<String>> = callbackFlow{
        val firestorePagoda = pagodaMapper.voToFirebaseHashmap(pagodaVO)
        firestoreRef.collection(PAGODAS)
            .document(pagodaVO.item_id)
            .update(firestorePagoda as Map<String, Any>)
            .addOnSuccessListener {
                trySend(Resource.Success("Successfully updated Pagoda"))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }
}