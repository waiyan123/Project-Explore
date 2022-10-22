package com.itachi.explore.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.FirestoreResult
import com.itachi.core.data.network.AncientFirebaseDataSource
import com.itachi.core.domain.AncientVO
import com.itachi.explore.framework.mappers.AncientMapper
import com.itachi.explore.persistence.entities.AncientEntity
import com.itachi.explore.utils.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.withContext

class AncientFirebaseDataSourceImpl(
    private val ancientMapper: AncientMapper,
    firestoreRef: FirebaseFirestore,
    firebaseStorage: FirebaseStorage,
    auth: FirebaseAuth
) : AncientFirebaseDataSource, FirebaseDataSourceImpl(firestoreRef, firebaseStorage, auth) {

    override suspend fun getAncientBackground(
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(BANNERS).document(ANCIENT_BACKGROUND)
            .get()
            .addOnSuccessListener {
                onSuccess(it.get(ANCIENT_BACKGROUND_PHOTO) as String)
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
            }
    }

    override suspend fun getAncientList(
        onSuccess: (List<AncientVO>) -> Unit,
        onFailure: (String) -> Unit
    ): Flow<FirestoreResult<List<AncientVO>>> = callbackFlow {

        firestoreRef.collection(ANCIENTS)
            .get().addOnSuccessListener {
                val ancientList = it.toObjects(AncientEntity::class.java)
                val strList = ancientList.map { ancientEntity ->
                    ancientEntity.title
                }
                var str = ""
                str = strList[0]!!
                onSuccess(ancientMapper.entityListToVOList(it.toObjects(AncientEntity::class.java)))
            }
            .addOnFailureListener {

            }

        firestoreRef.collection(ANCIENTS)
            .get()
            .addOnSuccessListener {

                trySend(FirestoreResult.Success(ancientMapper.entityListToVOList(it.toObjects(AncientEntity::class.java)))).isSuccess

                TODO("Something")
            }
            .addOnFailureListener {
//                offer(FirestoreResult.Error(it.localizedMessage))
            }
        awaitClose()
    }

    override suspend fun getAncientById(
        ancientId: String,
        onSuccess: (AncientVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

        firestoreRef.collection(ANCIENTS).whereEqualTo(ITEM_ID, ancientId)

            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val ancientEntity = it.documents[0].toObject(AncientEntity::class.java)
                    onSuccess(ancientMapper.entityToVO(ancientEntity!!))
                }
            }
            .addOnFailureListener {
                onFailure("Failed to get Ancient")
            }
    }

    override suspend fun getAncientsListByUserId(
        userId: String,
        onSuccess: (List<AncientVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        withContext(Dispatchers.IO) {

        }

        firestoreRef.collection(ANCIENTS).whereEqualTo(UPLOADER_ID, userId)

            .get()
            .addOnSuccessListener { snapShot ->
                if (snapShot.documents.isNotEmpty()) {
                    val ancientList = snapShot.toObjects(AncientEntity::class.java)
                    onSuccess(ancientMapper.entityListToVOList(ancientList))
                }
            }
            .addOnFailureListener {
                onFailure("Failed to get Ancient List")
            }
    }

    override suspend fun deleteAncientById(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        deletePhotos(ancientVO.photos, ancientVO.item_id)

        firestoreRef.collection(ANCIENTS)
            .document(ancientVO.item_id)
            .delete()
            .addOnSuccessListener {
                onSuccess("Successfully deleted...")
            }
            .addOnFailureListener {
                onFailure("Failed to delete Ancient")
            }
    }

    override suspend fun deleteAllAncients(
        ancientList: List<AncientVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun addAncient(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val firestoreAncient = ancientMapper.voToFirebaseHashmap(ancientVO)

        firestoreRef.collection(ANCIENTS)
            .document(ancientVO.item_id)
            .set(firestoreAncient)
            .addOnSuccessListener {
                onSuccess("Successfully added Ancient")
            }
            .addOnFailureListener {
                onFailure("Failed to add Ancient")
            }
    }

    override suspend fun addAllAncients(
        ancientList: List<AncientVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun updateAncient(
        ancientVO: AncientVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        val firestoreAncient = ancientMapper.voToFirebaseHashmap(ancientVO)
        firestoreRef.collection(ANCIENTS)
            .document(ancientVO.item_id)
            .update(firestoreAncient as Map<String, Any>)
            .addOnSuccessListener {
                onSuccess("Successfully updated Ancient")
            }
            .addOnFailureListener {
                onFailure("Failed to update Ancient")
            }
    }
}