package com.itachi.explore.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.domain.UserVO
import com.itachi.explore.persistence.entities.UserEntity
import com.itachi.explore.utils.USER
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class UserFirebaseDataSourceImpl(firestoreRef: FirebaseFirestore,
                                 firebaseStorage: FirebaseStorage,
                                 auth: FirebaseAuth
) : UserFirebaseDataSource,FirebaseDataSourceImpl(firestoreRef, firebaseStorage, auth){

    override suspend fun add(userVO: UserVO,
                             onSuccess: (UserVO) -> Unit,
                             onFailure: (String) -> Unit) {
        firestoreRef.collection(USER)
            .document(userVO.user_id)
            .set(firestoreNormalUser)
            .addOnSuccessListener {
                onSuccess(userVO)
            }
            .addOnFailureListener { exception ->
                onFailure(exception.localizedMessage)
            }
    }

    override suspend fun get(
        userVO: UserVO,
        onSuccess: (UserVO) -> Unit,
        onFailure: (String) -> Unit
    ) {
        TODO("Not yet implemented")
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
        TODO("Not yet implemented")
    }


}