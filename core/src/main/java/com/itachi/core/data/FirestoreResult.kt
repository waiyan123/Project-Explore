package com.itachi.core.data

sealed class FirestoreResult<T>(val data :T?=null,val errorMessage : String?=null) {

    class Success<T>(data: T) : FirestoreResult<T>(data)

    class Error<T>(errorMessage :String,data: T?=null) : FirestoreResult<T>(data,errorMessage)

}