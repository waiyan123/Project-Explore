package com.itachi.explore.framework

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.network.AncientFirebaseDataSource
import com.itachi.core.data.network.PagodaFirebaseDataSource
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.framework.mappers.AncientMapper
import com.itachi.explore.framework.mappers.PagodaMapper
import com.itachi.explore.persistence.entities.PagodaEntity
import com.itachi.explore.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PagodaFirebaseDataSourceImpl(
    private val pagodaMapper: PagodaMapper,
    firestoreRef: FirebaseFirestore,
    firebaseStorage: FirebaseStorage,
    auth: FirebaseAuth
) : PagodaFirebaseDataSource, FirebaseDataSourceImpl(firestoreRef, firebaseStorage, auth) {

    override suspend fun getPagodaBanner(
        onSuccess: (List<String>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(BANNERS).document(PAGODA_BANNER)
            .get()
            .addOnSuccessListener {
                if (it.exists()) onSuccess(it.get(PAGODA_BANNER_PHOTOS) as List<String>)
            }

    }

    override suspend fun getPagodaList(
        onSuccess: (List<PagodaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(PAGODAS)
            .get()
            .addOnSuccessListener {
                if (it.documents.size != 0) {
                    val pagodaList = it.toObjects(PagodaEntity::class.java)
                    onSuccess(pagodaMapper.entityListToVOList(pagodaList))

                } else {
                    onFailure("There's no item yet ")
                }
            }
    }

    override suspend fun getPagodaById(
        pagodaId: String,
        onSuccess: (PagodaVO) -> Unit,
        onFailure: (String) -> Unit
    ) {

        firestoreRef.collection(PAGODAS).whereEqualTo(ITEM_ID, pagodaId)
            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val pagodaEntity = it.documents[0].toObject(PagodaEntity::class.java)
                    onSuccess(pagodaMapper.entityToVO(pagodaEntity!!))
                }
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
            }
    }

    override suspend fun getPagodaListByUserId(
        userId: String,
        onSuccess: (List<PagodaVO>) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(PAGODAS).whereEqualTo(UPLOADER_ID, userId)
            .get()
            .addOnSuccessListener { snapShot ->
                if (snapShot.documents.isNotEmpty()) {
                    val pagodaList = snapShot.toObjects(PagodaEntity::class.java)
                    onSuccess(pagodaMapper.entityListToVOList(pagodaList))
                }
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
            }
    }

    override suspend fun deletePagodaById(
        pagodaVO: PagodaVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        deletePhotos(pagodaVO.photos, pagodaVO.item_id)

        firestoreRef.collection(PAGODAS)
            .document(pagodaVO.item_id)
            .delete()
            .addOnSuccessListener {
                onSuccess("Successfully deleted Pagoda")
            }
            .addOnFailureListener {
                onFailure("Failed to delete Pagoda")
            }
    }

    override suspend fun deleteAllPagodas(
        pagodaList: List<PagodaVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun addAllPagodas(
        pagodaList: List<PagodaVO>,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    @SuppressLint("CheckResult")
    override suspend fun addPagoda(
        byteArrayList: ArrayList<ByteArray>,
        geoPointsList: ArrayList<String>,
        pagodaVO: PagodaVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        uploadPhoto(byteArrayList, geoPointsList,
            { photoVOList ->
                pagodaVO.photos = photoVOList
                photoVOList.forEachIndexed { index, photoVO ->

                    uploadPhotoUrl(
                        photoVO.path,
                        pagodaVO.uploader_id,
                        pagodaVO.item_id,
                        pagodaVO.item_type,
                        geoPointsList[index]
                    )
                }
                val firestorePagoda = pagodaMapper.voToFirebaseHashmap(pagodaVO)

                firestoreRef.collection(PAGODAS)
                    .document(pagodaVO.item_id)
                    .set(firestorePagoda)
                    .addOnSuccessListener {
                        onSuccess("Successfully added Pagoda")
                    }
                    .addOnFailureListener {
                        onFailure("Failed to add Pagoda")
                    }
            },
            {
                onFailure(it)
            })

    }

    @SuppressLint("CheckResult")
    override suspend fun updatePagoda(
        photoVOList: List<PhotoVO>,
        byteArrayList: ArrayList<ByteArray>,
        geoPointsList: ArrayList<String>,
        pagodaVO: PagodaVO,
        onSuccess: (String) -> Unit,
        onFailure: (String) -> Unit
    ) {
        deletePhotos(photoVOList, pagodaVO.item_id)
        uploadPhoto(byteArrayList,geoPointsList,
            {photoVOList->
                pagodaVO.photos= photoVOList
                photoVOList.forEachIndexed { index, photoVO ->
                    uploadPhotoUrl(photoVO.path,pagodaVO.uploader_id,pagodaVO.item_id,pagodaVO.item_type,geoPointsList[index])
                }
                val firestorePagoda = pagodaMapper.voToFirebaseHashmap(pagodaVO)
                firestoreRef.collection(PAGODAS)
                    .document(pagodaVO.item_id)
                    .update(firestorePagoda as Map<String, Any>)
                    .addOnSuccessListener {
                        onSuccess("Successfully updated Pagoda")
                    }
                    .addOnFailureListener {
                        onFailure("Failed to update Pagoda")
                    }
            },
            {
                onFailure(it)
            })
    }
}