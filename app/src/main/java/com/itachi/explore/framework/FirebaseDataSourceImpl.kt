package com.itachi.explore.framework

import android.annotation.SuppressLint
import android.net.Uri
import com.google.android.gms.tasks.Continuation
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.UploadTask
import com.itachi.core.domain.PhotoVO
import com.itachi.explore.utils.*
import java.util.*
import kotlin.collections.ArrayList

abstract class FirebaseDataSourceImpl(
    protected val firestoreRef: FirebaseFirestore,
    protected val firebaseStorage: FirebaseStorage,
    protected val auth: FirebaseAuth
) {

}