package com.itachi.explore.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.common.Resource
import com.itachi.core.data.network.AncientFirebaseDataSource
import com.itachi.core.domain.AncientVO
import com.itachi.explore.framework.mappers.AncientMapper
import com.itachi.explore.persistence.entities.AncientEntity
import com.itachi.explore.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

@OptIn(ExperimentalCoroutinesApi::class)
class AncientFirebaseDataSourceImpl(
    private val ancientMapper: AncientMapper,
    firestoreRef: FirebaseFirestore,
    firebaseStorage: FirebaseStorage,
    auth: FirebaseAuth
) : AncientFirebaseDataSource, FirebaseDataSourceImpl(firestoreRef, firebaseStorage, auth) {

    override fun getAncientBackground(): Flow<Resource<String>> = callbackFlow {
        firestoreRef.collection(BANNERS).document(ANCIENT_BACKGROUND)
            .get()
            .addOnSuccessListener { snapShot ->
                trySend(Resource.Success(snapShot.get(ANCIENT_BACKGROUND_PHOTO).toString()))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    override fun getAllAncients(): Flow<Resource<List<AncientVO>>> = callbackFlow {

        firestoreRef.collection(ANCIENTS)
            .get()
            .addOnSuccessListener {

                trySend(Resource.Success(ancientMapper.entityListToVOList(it.toObjects(AncientEntity::class.java)))).isSuccess

            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    override fun getAncientById(ancientId: String): Flow<Resource<AncientVO>> = callbackFlow{
        firestoreRef.collection(ANCIENTS).whereEqualTo(ITEM_ID, ancientId)
            .get()
            .addOnSuccessListener {snapShot->
                if (snapShot.documents.isNotEmpty()) {
                    val ancientEntity = snapShot.documents[0].toObject(AncientEntity::class.java)
                    ancientEntity?.let {
                        trySend(Resource.Success(ancientMapper.entityToVO(it)))
                    }
                }
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    override fun getAncientsListByUserId(userId: String): Flow<Resource<List<AncientVO>>> = callbackFlow{

        firestoreRef.collection(ANCIENTS).whereEqualTo(UPLOADER_ID, userId)
            .get()
            .addOnSuccessListener { snapShot ->
                if (snapShot.documents.isNotEmpty()) {
                    val ancientList = snapShot.toObjects(AncientEntity::class.java)
                    trySend(Resource.Success(ancientMapper.entityListToVOList(ancientList)))
                }
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    override fun deleteAncient(ancientVO: AncientVO): Flow<Resource<String>> = callbackFlow{

        firestoreRef.collection(ANCIENTS)
            .document(ancientVO.item_id)
            .delete()
            .addOnSuccessListener {
                trySend(Resource.Success("Successfully deleted..."))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    override fun addAncient(ancientVO: AncientVO): Flow<Resource<String>> = callbackFlow {

        val firestoreAncient = ancientMapper.voToFirebaseHashmap(ancientVO)
        firestoreRef.collection(ANCIENTS)
            .document(ancientVO.item_id)
            .set(firestoreAncient)
            .addOnSuccessListener {
                trySend(Resource.Success("Successfully added Ancient"))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }

    override fun addAllAncients(ancientList: List<AncientVO>): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    override fun updateAncient(ancientVO: AncientVO): Flow<Resource<AncientVO>> = callbackFlow{

        val firestoreAncient = ancientMapper.voToFirebaseHashmap(ancientVO)
        firestoreRef.collection(ANCIENTS)
            .document(ancientVO.item_id)
            .update(firestoreAncient as Map<String, Any>)
            .addOnSuccessListener {
                trySend(Resource.Success(ancientVO))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error occur!"))
            }
        awaitClose { channel.close() }
    }
}