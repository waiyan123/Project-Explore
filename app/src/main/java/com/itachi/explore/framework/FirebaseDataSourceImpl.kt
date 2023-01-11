package com.itachi.explore.framework

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage

abstract class FirebaseDataSourceImpl(
    protected val firestoreRef: FirebaseFirestore,
    protected val firebaseStorage: FirebaseStorage,
    protected val auth: FirebaseAuth
) {

}