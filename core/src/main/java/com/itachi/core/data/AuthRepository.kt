package com.itachi.core.data

import com.itachi.core.data.network.AuthFirebaseDataSource

class AuthRepository(private val authFirebaseDataSource: AuthFirebaseDataSource) {

    suspend fun isAlreadyLogin() = authFirebaseDataSource.isAlreadyLogin()

    suspend fun signInWithGoogle() = authFirebaseDataSource.signInWithGoogle()

    suspend fun signOut() = authFirebaseDataSource.signOut()

}