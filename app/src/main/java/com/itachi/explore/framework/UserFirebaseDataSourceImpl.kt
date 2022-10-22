package com.itachi.explore.framework

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.itachi.core.data.network.UserFirebaseDataSource
import com.itachi.core.domain.UserVO
import com.itachi.core.common.Resource
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.utils.USER
import com.itachi.explore.utils.USER_ID
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow

class UserFirebaseDataSourceImpl(
    private val userMapper: UserMapper,
    firestoreRef: FirebaseFirestore,
    firebaseStorage: FirebaseStorage,
    auth: FirebaseAuth
) : UserFirebaseDataSource, FirebaseDataSourceImpl(firestoreRef, firebaseStorage, auth) {

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun addUser(userVO: UserVO): Flow<Resource<UserVO>> = callbackFlow {

        firestoreRef.collection(USER)
            .whereEqualTo(USER_ID, userVO.user_id)
            .get()
            .addOnSuccessListener { snap ->
                if (snap.documents.isNotEmpty()) {
                    Log.d("test---", "user already exists ${userVO.user_id}")
                    val mUserVO = snap.documents[0].toObject(UserVO::class.java)
                    mUserVO?.let {
                        trySend(Resource.Success(it))
                    }
                } else {
                    firestoreRef.collection(USER)
                        .document(userVO.user_id)
                        .set(userMapper.voToFirebaseHashmap(userVO))
                        .addOnSuccessListener {
                            Log.d("test---", "added success ${userVO.name}")
                            trySend(Resource.Success(userVO))
                        }
                        .addOnFailureListener { exception ->
                            trySend(Resource.Error(exception.localizedMessage?: "Unexpected error"))
                        }
                }
            }
            .addOnFailureListener {
                firestoreRef.collection(USER)
                    .document(userVO.user_id)
                    .set(userMapper.voToFirebaseHashmap(userVO))
                    .addOnSuccessListener {
                        Log.d("test---", "added success ${userVO.name}")
                        trySend(Resource.Success(userVO))
                    }
                    .addOnFailureListener { exception ->

                    }
            }
        awaitClose { channel.close() }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun getUserById(userId: String?): Flow<Resource<UserVO>> = callbackFlow {
        firestoreRef.collection(USER)
            .whereEqualTo(USER_ID, userId ?: auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { snap ->
                if(snap.documents.isNotEmpty()){
                    val userVO = snap.documents[0].toObject(UserVO::class.java)
                    userVO?.let {
                        trySend(Resource.Success(it))
                    }
                }else trySend(Resource.Error("User doesn't exist"))

            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error"))
            }
        awaitClose { channel.close() }
    }

    override fun delete(userVO: UserVO): Flow<Resource<String>> {
        TODO("Not yet implemented")
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun update(userVO: UserVO): Flow<Resource<UserVO>> = callbackFlow {
        firestoreRef.collection(USER)
            .document(userVO.user_id)
            .update(userMapper.voToFirebaseHashmap(userVO))
            .addOnSuccessListener {
                trySend(Resource.Success(userVO))
            }
            .addOnFailureListener {
                trySend(Resource.Error(it.localizedMessage ?: "Unexpected error"))
            }
    }

    override fun signOut(): Flow<Resource<String>> = flow {
        auth.signOut()
        emit(Resource.Success("Sign out"))
    }

}