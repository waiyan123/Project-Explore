package com.itachi.explore.mvvm.viewmodel

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.widget.EditText
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itachi.core.domain.*
import com.itachi.explore.framework.Interactors
import com.itachi.explore.mvvm.model.UploadModelImpl
import com.itachi.explore.utils.*
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.MimeType
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sangcomz.fishbun.define.Define
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import kotlin.collections.ArrayList

class FormViewModel(interactors: Interactors) : AppViewmodel(interactors), KoinComponent {

    private val uploadModel: UploadModelImpl by inject()

    private val mPickupImages = ArrayList<String>()
    private var mPhotoVOList = MutableLiveData<ArrayList<PhotoVO>>()
    val progressLoading = MutableLiveData<Boolean>()
    val successMsg = MutableLiveData<String>()
    val errorMsg = MutableLiveData<String>()
    private lateinit var mUserVO: UserVO
    private val itemId = UUID.randomUUID().toString()

    init {
        GlobalScope.launch {
            interactors.getUser.fromRoom(
                {
                    mUserVO = it
                },
                {
                    errorMsg.postValue(it)
                }
            )
        }
    }

    fun checkValidateText(et: EditText): Boolean {
        return Util.checkEditTextValidattion(et)
    }

    private fun chooseMultiplePhotos(context: Context) {

        FishBun.with(context as Activity)
            .setImageAdapter(GlideAdapter())
            .setMaxCount(3)
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
                        for (image in path) {
                            mPickupImages.add(image.toString())
                        }

                    }
                }
            }
        }
    }

    @SuppressLint("CheckResult")
    private fun uploadPhotos(type: String, context: Context) {

        progressLoading.postValue(true)

        Util.getGeoPointsFromImageList(context, mPickupImages)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { geoPointsList ->
                uploadModel.uploadPhoto(mPickupImages, geoPointsList, context,
                    { observable ->
                        observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe { photoVOList ->
                                mPhotoVOList.postValue(photoVOList)
                                photoVOList.forEachIndexed { index, photoVO ->
                                    uploadModel.uploadPhotoUrl(
                                        photoVO.url!!,
                                        mUserVO.user_id,
                                        itemId,
                                        type,
                                        geoPointsList[index]
                                    )
                                }

                            }
                    },
                    {
                        errorMsg.postValue(it)
                    }
                )
            }
    }

    fun addItem(name: String, created: String, festival: String, about: String, type: String,context: Context) {
        when (type) {
            PAGODA_TYPE -> {
                addPagoda(name, created, festival, about, type,context)
            }

            VIEW_TYPE -> {
                addView(name, created, festival, about, type,context)
            }

            ANCIENT_TYPE -> {
                addAncient(name, created, festival, about, type,context)
            }

            FOOD_TYPE -> {

            }

            ACCESSORY_TYPE -> {

            }

            SUPPORT_TYPE -> {

            }
        }
    }

    private fun addPagoda(
        name: String,
        created: String,
        festival: String,
        about: String,
        type: String,
        context: Context
    ) {
        var isFestival = false

        if (festival.isNotEmpty()) isFestival = true
        val pagodaVO = PagodaVO(
            about,
            emptyList(),
            created, festival,
            isFestival,
            itemId,
            type,
            emptyList(),
            name,
            mUserVO.user_id
        )

        if (!MDetect.isUnicode()) {
            val mAbout = Rabbit.zg2uni(about)
            val mName = Rabbit.zg2uni(name)
            val mCreatedDate = Rabbit.zg2uni(created)
            val mFestivalDate = Rabbit.zg2uni(festival)
            pagodaVO.about = mAbout
            pagodaVO.title = mName
            pagodaVO.created_date = mCreatedDate
            pagodaVO.festival_date = mFestivalDate
        }

        if (mPickupImages.size > 0) {
            progressLoading.postValue(true)
            uploadPhotos(type, context)
            mPhotoVOList.observeForever {photoVOList->
                pagodaVO.photos = photoVOList
                GlobalScope.launch {
                    interactors.addPagoda.toFirebase(pagodaVO,
                        { success ->
                            successMsg.postValue(success)
                        },
                        { error ->
                            errorMsg.postValue(error)
                        })
                }
            }

        } else {
            errorMsg.postValue("Pick up photo")
        }
    }

    private fun addView(name: String, created: String, festival: String, about: String, type: String,context: Context) {
        var isFestival = false
        if (festival.isNotEmpty()) isFestival = true
        val viewVO = ViewVO(
            about,
            emptyList(),
            created, festival,
            isFestival,
            itemId,
            type,
            emptyList(),
            name,
            mUserVO.user_id
        )

        if (!MDetect.isUnicode()) {
            val mAbout = Rabbit.zg2uni(about)
            val mName = Rabbit.zg2uni(name)
            val mCreatedDate = Rabbit.zg2uni(created)
            val mFestivalDate = Rabbit.zg2uni(festival)
            viewVO.about = mAbout
            viewVO.title = mName
            viewVO.created_date = mCreatedDate
            viewVO.festival_date = mFestivalDate
        }

        if (mPickupImages.size > 0) {
            progressLoading.postValue(true)
            uploadPhotos(type,context)
            mPhotoVOList.observeForever { photoVOList->
                viewVO.photos = photoVOList
                GlobalScope.launch {
                    interactors.addView.toFirebase(viewVO,
                        {success->
                            successMsg.postValue(success)
                        },
                        {error->
                            errorMsg.postValue(error)
                        })
                }
            }

        } else {
            errorMsg.postValue("Pick up photo")
        }
    }
    private fun addAncient(name: String, created: String, festival: String, about: String, type: String,context: Context) {
        var isFestival = false
        if (festival.isNotEmpty()) isFestival = true
        val ancientVO = AncientVO(
            about,
            emptyList(),
            created, festival,
            isFestival,
            itemId,
            type,
            emptyList(),
            name,
            mUserVO.user_id
        )

        if (!MDetect.isUnicode()) {
            val mAbout = Rabbit.zg2uni(about)
            val mName = Rabbit.zg2uni(name)
            val mCreatedDate = Rabbit.zg2uni(created)
            val mFestivalDate = Rabbit.zg2uni(festival)
            ancientVO.about = mAbout
            ancientVO.title = mName
            ancientVO.created_date = mCreatedDate
            ancientVO.festival_date = mFestivalDate
        }

        if (mPickupImages.size > 0) {
            progressLoading.postValue(true)
            uploadPhotos(type,context)
            mPhotoVOList.observeForever { photoVOList->
                ancientVO.photos = photoVOList
                GlobalScope.launch {
                    interactors.addAncient.toFirebase(ancientVO,
                        {success->
                            successMsg.postValue(success)
                        },
                        {error->
                            errorMsg.postValue(error)
                        })
                }
            }

        } else {
            errorMsg.postValue("Pick up photo")
        }
    }
}