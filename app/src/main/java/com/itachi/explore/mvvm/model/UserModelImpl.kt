package com.itachi.explore.mvvm.model

import android.annotation.SuppressLint
import com.itachi.core.domain.UserVO
import com.itachi.explore.framework.mappers.PhotoEntityToVoMapper
import com.itachi.explore.framework.mappers.UserMapper
import com.itachi.explore.persistence.MyDatabase
import com.itachi.explore.persistence.entities.UserEntity
import com.itachi.explore.utils.*
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

@SuppressLint("StaticFieldLeak")
class UserModelImpl : BaseModel(),UserModel,KoinComponent{

    override fun updateUserProfile(
        userVO: UserVO,
        onSuccess: (Observable<UserVO>) -> Unit,
        onFailure: (String?) -> Unit
    ) {

        val firestoreUser = hashMapOf(
                USER_BRIEF_BIO to userVO.brief_bio,
                USER_EMAIL to userVO.email,
                FACEBOOK_ID to userVO.facebook_id,
                USER_FACEBOOK_URL to userVO.facebook_profile_url,
                USER_IS_UPLOADER to userVO.get_is_uploader,
                USER_NAME to userVO.name,
                USER_PHONE_NUMBER to userVO.phone_number,
                USER_PROFILE_PIC to userVO.profile_pic,
                USER_BACKGROUND_PIC to userVO.background_pic,
                USER_ID to userVO.user_id
        )
        firestoreRef.collection(USER)
                .document(userVO.user_id!!)
                .update(firestoreUser as Map<String, Any>)
                .addOnSuccessListener {

//                    database.userDao().insertUser(userVO)
//                            .subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe {
//                                onSuccess(Observable.just(userVO))
//                            }
                }
                .addOnFailureListener {
                    onFailure(it.localizedMessage)
                }
    }

    override fun getUserById(
        userId: String,
        onSuccess: (Observable<UserVO>) -> Unit,
        onFailure: (String?) -> Unit
    ) {
        var observable : Observable<UserVO>
        firestoreRef.collection(USER).whereEqualTo(FACEBOOK_ID, userId)

            .get()
            .addOnSuccessListener {
                if (it.documents.isNotEmpty()) {
//                    val userEntity = it.documents[0].toObject(UserEntity::class.java)
//                    val userMapper = UserMapper(PhotoEntityToVoMapper())
//                    observable = Observable.just(userMapper.map(userEntity))
//                    onSuccess(observable)
                }
            }
            .addOnFailureListener {
            }
    }

    private val database : MyDatabase by inject()


    @SuppressLint("CheckResult")
    override fun getUserProfile(
        onSuccess : (UserVO)-> Unit,
        onFailure : (String?)-> Unit) {
//        if(database.userDao().userInDbExist()) {
//            database.userDao().getUser()
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe {
//                    onSuccess(it)
//                }
//        }
//        else onFailure("User is not existed.")

//        firestoreRef.collection(USER)
//            .whereEqualTo(USER_ID,auth.currentUser!!.uid)
//            .get()
//            .addOnSuccessListener {snap->
//                val userVO = snap.documents[0].toObject(UserVO::class.java)
//                if (userVO != null) {
//
//
//                    database.userDao().insertUser(userVO)
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe {
//                            onSuccess(userVO)
//                        }
//
//                }
//            }
    }
    @SuppressLint("CheckResult")
    override fun getUserFromDb(onSuccess: (UserVO) -> Unit, onFailure: (String?) -> Unit) {
//        if(database.userDao().userInDbExist()) {
//            database.userDao().getUser()
//                    .subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe {
//                        onSuccess(it)
//                    }
//        }
//        else onFailure("User is not existed.")
    }

    override fun logOut() {
        database.userDao().deleteUser()
        auth.signOut()
    }


}