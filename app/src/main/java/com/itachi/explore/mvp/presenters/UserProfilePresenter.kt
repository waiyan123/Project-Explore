package com.itachi.explore.mvp.presenters

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.lifecycle.viewModelScope
import com.itachi.core.domain.UserVO
import com.itachi.explore.mvvm.model.UploadModelImpl
import com.itachi.explore.mvvm.model.UserModelImpl
import com.itachi.explore.mvp.views.UserProfileView
import com.itachi.explore.utils.REQUEST_BACKGROUND_PIC
import com.itachi.explore.utils.REQUEST_PROFILE_PIC
import com.itachi.explore.utils.Util
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.MimeType
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sangcomz.fishbun.define.Define
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class UserProfilePresenter : BasePresenter<UserProfileView>(), KoinComponent {

    private val userModel: UserModelImpl by inject()
    private val uploadModel: UploadModelImpl by inject()

    private lateinit var mUserVO: UserVO
    private var upload_action = ""
    private var user_mode = ""

    private var profilePic = ""
    private var backgroundPic = ""

    fun onUiReady(userVO: UserVO?) {
        userModel.getUserFromDb(
                {
                    mUserVO = it
                    if(userVO!=null) {
                        mView.showUserProfile(userVO)
                    }
                    else {
                        mView.showUserProfile(mUserVO)
                        if(user_mode!="edit") mView.goToNormalMode()
                    }
                },
                {
                    mView.showError(it!!)
                }
        )

    }

    fun setUpMode(mode : String) {
        user_mode = mode
    }

    fun onClickedEditButton() {
        if (mUserVO != null) mView.goToEditMode(mUserVO)

    }

    fun onClickedDone(context: Context,name : String) {
        mView.displayLoading()
        mUserVO.name = name
        userModel.updateUserProfile(mUserVO,
            {observable ->
                observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        profilePic = ""
                        backgroundPic = ""
                        upload_action = ""
                        mView.showUploadSuccessFul("Successfully updated")
                        mView.goToNormalMode()
                    }
            },
            {

            })
    }

    fun onClickedCancel() {
        profilePic = ""
        backgroundPic = ""
        mView.dismissDialog()
    }

    fun onClickedChangeBtn(context: Context) {
        mView.displayLoading()
        if (profilePic!="") {
            val list = ArrayList<String>()
            list.add(profilePic)

            if(mUserVO.profile_pic!!.id!="") userModel.deleteUserPhoto(mUserVO.profile_pic,mUserVO.profile_pic!!.id.toString())

            var geoPointsList = ArrayList<String>()
            GlobalScope.launch {
                list.forEach {
                    var geoPoint = Util.getGeoPointFromImage(Util.getRealPathFromUrl(context, it)!!)
                    geoPointsList.add(geoPoint)
                }
            }
            uploadModel.uploadPhoto(list,geoPointsList,context){ observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            profilePic = it[0].url.toString()
                            mUserVO.profile_pic = it[0]
                            userModel.updateUserProfile(mUserVO,
                                    {observable ->
                                        observable.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe { userVO->
                                                    mUserVO = userVO
                                                    profilePic = ""
                                                    mView.showUploadSuccessFul("Successfully uploaded profile picture")
                                                    mView.showUserProfilePic(mUserVO.profile_pic!!.url.toString())
                                                }
                                    },
                                    {

                                    })

                        }
            }
        }
        if(backgroundPic!="") {
            val list = ArrayList<String>()
            list.add(backgroundPic)

            if(mUserVO.background_pic!!.id!="") userModel.deleteUserPhoto(mUserVO.background_pic,mUserVO.background_pic!!.id.toString())

            var geoPointsList = ArrayList<String>()
            GlobalScope.launch {
                list.forEach {
                    var geoPoint = Util.getGeoPointFromImage(Util.getRealPathFromUrl(context, it)!!)
                    geoPointsList.add(geoPoint)
                }
            }
            uploadModel.uploadPhoto(list,geoPointsList ,context){ observable ->
                observable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe {
                            backgroundPic = it[0].url.toString()
                            mUserVO.background_pic = it[0]
                            userModel.updateUserProfile(mUserVO,
                                    {observable ->
                                        observable.subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe { userVO->
                                                    mUserVO = userVO
                                                    backgroundPic = ""
                                                    mView.showUploadSuccessFul("Successfully uploaded background picture")
                                                    mView.showUserBackgroundPic(mUserVO.background_pic!!.url.toString())
                                                }
                                    },
                                    {

                                    })
                        }
            }
        }
    }

    fun onClickedProfilePic(context: Context) {
        upload_action = "profile"
        checkPermission(context, REQUEST_PROFILE_PIC
        ) {
            mView.showProfileDialog(mUserVO.profile_pic!!.url!!,"Pick up")
        }
    }

    fun onClickedBackgroundPic(context: Context) {
        upload_action = "background"
        checkPermission(context, REQUEST_BACKGROUND_PIC
        ) {
            mView.showProfileDialog(mUserVO.background_pic!!.url!!,"Pick up")
        }
    }

    fun chooseSinglePhoto(context: Context) {

        FishBun.with(context as Activity)
                .setImageAdapter(GlideAdapter())
                .setMaxCount(1)
                .setActionBarColor(Color.parseColor("#795548"), Color.parseColor("#5D4037"), false)
                .setActionBarTitleColor(Color.parseColor("#ffffff"))
                .setAlbumSpanCount(2, 3)
                .setButtonInAlbumActivity(false)
                .setCamera(true)
                .setAllViewTitle("All")
                .setMenuAllDoneText("All Done")
                .setActionBarTitle("Choose Images ...")
                .textOnNothingSelected("Please select three !")
                .exceptMimeType(listOf(MimeType.GIF))
                .startAlbum()
    }

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            Define.ALBUM_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    val path = data?.getParcelableArrayListExtra<Uri>(Define.INTENT_PATH)
                    if (path != null) {
//                        for (image in path) {
//                            pickUpImages.add(image.toString())
//                        }
                        when (upload_action) {
                            "profile" -> {
                                profilePic = path[0].toString()

                                mView.showProfileDialog(profilePic,"Change")
                            }
                            "background" -> {
                                backgroundPic = path[0].toString()
                                mView.showProfileDialog(backgroundPic,"Change")
//                                mView.showUserBackgroundPic(path[0].toString())
                            }
                        }
                    }
                }
            }
        }
    }

}