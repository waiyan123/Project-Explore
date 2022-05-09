package com.itachi.core.data.network

interface AuthFirebaseDataSource {

    suspend fun isAlreadyLogin() : Boolean

    suspend fun signInWithGoogle()

    suspend fun signOut()

}