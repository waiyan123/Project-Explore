package com.itachi.explore.framework

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.domain.UserVO
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.persistence.entities.UserEntity
import com.itachi.explore.utils.FACEBOOK_ID
import com.itachi.explore.utils.USER
import com.itachi.explore.utils.USER_ID
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserFirebaseDataSourceImpl(private val userMapper: UserMapper,
                                 firestoreRef: FirebaseFirestore,
                                 firebaseStorage: FirebaseStorage,
                                 auth: FirebaseAuth
) : UserFirebaseDataSource,FirebaseDataSourceImpl(firestoreRef, firebaseStorage, auth){

    override suspend fun addUser(userVO: UserVO,
                             onSuccess: (UserVO) -> Unit,
                             onFailure: (String) -> Unit) {
        firestoreRef.collection(USER)
            .document(userVO.user_id)
            .set(userMapper.voToFirebaseHashmap(userVO))
            .addOnSuccessListener {
                onSuccess(userVO)
            }
            .addOnFailureListener { exception ->
                onFailure(exception.localizedMessage)
            }
    }

    override suspend fun getUser(
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(USER)
            .whereEqualTo(USER_ID,auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener {snap->
                val userVO = snap.documents[0].toObject(UserVO::class.java)
                if (userVO != null) {
                    onSuccess(userVO)
                }
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
            }
    }

    override suspend fun getUploader(
        userId: String,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(USER).whereEqualTo(USER_ID, userId)

            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
                    val userEntity = it.documents[0].toObject(UserEntity::class.java)
                    onSuccess(userMapper.entityToVO(userEntity!!))
                }
            }
            .addOnFailureListener {
            }
    }

    override suspend fun delete(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
    }

    override suspend fun update(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        firestoreRef.collection(USER)
            .document(userVO.user_id!!)
            .update(userMapper.voToFirebaseHashmap(userVO))
            .addOnSuccessListener {
                onSuccess(userVO)
            }
            .addOnFailureListener {
                onFailure(it.localizedMessage)
            }
    }


}