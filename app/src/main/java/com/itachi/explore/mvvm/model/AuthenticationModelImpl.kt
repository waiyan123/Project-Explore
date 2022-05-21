//package com.itachi.explore.mvvm.model
//
//import android.annotation.SuppressLint
//import com.facebook.AccessToken
//import com.google.firebase.auth.FacebookAuthProvider
//import com.itachi.core.domain.PhotoVO
//import com.itachi.core.domain.UserVO
//import com.itachi.explore.persistence.MyDatabase
//import com.itachi.explore.persistence.entities.PhotoEntity
//import com.itachi.explore.persistence.entities.UserEntity
//import com.itachi.explore.utils.*
//import io.reactivex.Observable
//import io.reactivex.android.schedulers.AndroidSchedulers
//import io.reactivex.schedulers.Schedulers
//import org.koin.core.KoinComponent
//import org.koin.core.inject
//
//@SuppressLint("StaticFieldLeak")
//class AuthenticationModelImpl : AuthenticationModel,
//        BaseModel(), KoinComponent {
//
//    private val database: MyDatabase by inject()
//    private val uploadModel : UploadModelImpl by inject()
//
//    override fun isAlreadyLogin(): Boolean {
//        return auth.currentUser != null && database.userDao().userInDbExist()
//    }
//
//    override fun firebaseAuthWithFacebook(
//        accessToken: AccessToken,
//        onSuccess: (Observable<UserEntity>) -> Unit,
//        onError: (String) -> Unit
//    ) {
//
//        var observable: Observable<UserEntity>
//
//        val credential = FacebookAuthProvider.getCredential(accessToken.token)
//        auth.signInWithCredential(credential)
//                .addOnCompleteListener { task ->
//                    if (task.isSuccessful) {
//
//                        val user = auth.currentUser!!
//                        var profileEntity = PhotoEntity("","","")
//                        var backgroundEntity = PhotoEntity("","","")
//                        profileEntity.url = user.photoUrl.toString()
//
//                        val firestoreNormalUser =
//                                hashMapOf(
//                                        FACEBOOK_ID to accessToken.userId,
//                                        USER_ID to user.uid,
//                                        USER_PHONE_NUMBER to user.phoneNumber.toString(),
//                                        USER_FACEBOOK_URL to "",
//                                        USER_EMAIL to user.email,
//                                        USER_NAME to user.displayName,
//                                        USER_PROFILE_PIC to profileEntity,
//                                        USER_BACKGROUND_PIC to backgroundEntity,
//                                        USER_BRIEF_BIO to "",
//                                        USER_IS_UPLOADER to false
//                                )
//
//                        firestoreRef.collection(USER).whereEqualTo(USER_ID, user.uid)
//                                .get()
//                                .addOnSuccessListener {docSnap ->
//                                    if (docSnap.documents.size == 0) {
//                                        firestoreRef.collection(USER)
//                                                .document(user.uid)
//                                                .set(firestoreNormalUser)
//                                                .addOnSuccessListener {
//                                                    val userEntity = UserEntity(
//                                                            accessToken.userId,
//                                                            user.uid,
//                                                            user.phoneNumber.toString(),
//                                                            "",
//                                                            user.email ?: "",
//                                                            user.displayName ?: "",
//                                                        profileEntity,
//                                                            backgroundEntity,
//                                                            "",
//                                                            false
//                                                    )
//                                                    database.userDao().insertUser(userEntity)
//                                                            .subscribeOn(Schedulers.io())
//                                                            .observeOn(AndroidSchedulers.mainThread())
//                                                            .subscribe {
//                                                                observable = Observable.just(userEntity)
//                                                                onSuccess(observable)
//                                                            }
//
//                                                }
//                                                .addOnFailureListener { exception ->
//                                                    onError(exception.localizedMessage)
//                                                }
//                                    } else {
//
//                                        firestoreRef.collection(USER)
//                                                .document(user.uid)
//                                                .get()
//                                                .addOnSuccessListener { docSnap ->
//
//                                                    val userVO = docSnap.toObject(UserEntity::class.java)!!
//                                                    userVO.get_is_uploader = docSnap.get(
//                                                            USER_IS_UPLOADER
//                                                    ) as Boolean
//
//                                                    database.userDao().insertUser(userVO)
//                                                            .subscribeOn(Schedulers.io())
//                                                            .observeOn(AndroidSchedulers.mainThread())
//                                                            .subscribe {
//                                                                observable = Observable.just(userVO)
//                                                                onSuccess(observable)
//                                                            }
//
//                                                }
//                                    }
//
//                                }
//                                .addOnFailureListener {
//                                }
//
//
//                    } else {
//                        onError("Firebase auth failed")
//                    }
//                }
//    }
//
//}