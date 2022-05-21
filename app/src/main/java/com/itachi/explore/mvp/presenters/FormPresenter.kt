package com.itachi.explore.mvp.presenters

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
import com.itachi.explore.mvvm.model.*
import com.itachi.explore.mvp.views.FormView
import com.itachi.explore.utils.*
import com.sangcomz.fishbun.FishBun
import com.sangcomz.fishbun.MimeType
import com.sangcomz.fishbun.adapter.image.impl.GlideAdapter
import com.sangcomz.fishbun.define.Define
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import me.myatminsoe.mdetect.MDetect
import me.myatminsoe.mdetect.Rabbit
import org.koin.core.KoinComponent
import org.koin.core.inject
import java.util.*
import kotlin.collections.ArrayList

class FormPresenter : BasePresenter<FormView>(), KoinComponent {

    private lateinit var mContext : Context

    private var pickUpImages: ArrayList<String> = ArrayList()

    private var existingImagesUris: ArrayList<Uri> = ArrayList()

    private var photoVOList = ArrayList<PhotoVO>()

    private val uploadModel: UploadModelImpl by inject()

//    private val pagodaModel: PagodaModelImpl by inject()

    private val viewModel: ViewsModelImpl by inject()

//    private val ancientModel: AncientModelImpl by inject()

    private val userModel: UserModelImpl by inject()

    private val languageModel : LanguageModelImpl by inject()

    private lateinit var userVO: UserVO

    private var mPagodaVO: PagodaVO? = null
    private var mViewVO: ViewVO? = null
    private var mAncientVO: AncientVO? = null

    var language = ""
    var formType = "Add"

    private var itemId = UUID.randomUUID().toString()

    init {
        userModel.getUserProfile(
            {
                userVO = it
            },
            {

            }
        )
    }

    fun addContext(context: Context) {
        mContext = context
    }

    fun chooseMultiplePhotos(context: Context) {

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
                    pickUpImages = ArrayList()
                    val path = data?.getParcelableArrayListExtra<Uri>(Define.INTENT_PATH)
                    if (path != null) {
                        for (image in path) {
                            pickUpImages.add(image.toString())
                        }
                        mView.showPickUpImages(path)
                    }
                }
            }
        }
    }

    fun addItem(name: String, created: String, festival: String, about: String, type: String) {
        when (type) {
            PAGODA_TYPE -> {
                addPagoda(name, created, festival, about, type)
            }

            VIEW_TYPE -> {
                addView(name, created, festival, about, type)
            }

            ANCIENT_TYPE -> {
                addAncient(name, created, festival, about, type)
            }

            FOOD_TYPE -> {

            }

            ACCESSORY_TYPE -> {

            }

            SUPPORT_TYPE -> {

            }
        }
    }

    fun updateItem(name: String, created: String, festival: String, about: String, type: String) {

        var mAbout = about
        var mName = name
        var mCreatedDate = created
        var mFestivalDate = festival

        if (!MDetect.isUnicode()) {
            mAbout = Rabbit.zg2uni(about)
            mName = Rabbit.zg2uni(name)
            mCreatedDate = Rabbit.zg2uni(created)
            mFestivalDate = Rabbit.zg2uni(festival)

        }

        when (type) {
            PAGODA_TYPE -> {
                mPagodaVO!!.title = mName
                mPagodaVO!!.created_date = mCreatedDate
                mPagodaVO!!.festival_date = mFestivalDate
                mPagodaVO!!.about = mAbout
                mView.showProgressLoading()
                if (pickUpImages.size > 0) {
//                    pagodaModel.deletePhotos(mPagodaVO?.photos, mPagodaVO!!.item_id)
                    uploadPhotos(type)
                        .observeForever {
                            photoVOList = it
                            mPagodaVO!!.photos = photoVOList
//                            pagodaModel.updatePagoda(mPagodaVO!!)
//                                .observeForever {
//                                    mView.successUpdatingItem(it)
//                                }
                        }
                }
                else {
//                    pagodaModel.updatePagoda(mPagodaVO!!)
//                        .observeForever {
//                            mView.successUpdatingItem(it)
//                        }
                }
            }

            VIEW_TYPE -> {
                mViewVO!!.title = mName
                mViewVO!!.created_date = mCreatedDate
                mViewVO!!.festival_date = mFestivalDate
                mViewVO!!.about = mAbout
                mView.showProgressLoading()
                if (pickUpImages.size > 0) {
//                    viewModel.deletePhotos(mViewVO?.photos, mViewVO!!.item_id)
                    uploadPhotos(type)
                        .observeForever {
                            photoVOList = it
                            mViewVO!!.photos = photoVOList
                            viewModel.updateView(mViewVO!!)
                                .observeForever {
                                    mView.successUpdatingItem(it)
                                }
                        }
                }
                else {
                    viewModel.updateView(mViewVO!!)
                        .observeForever {
                            mView.successUpdatingItem(it)
                        }
                }
            }

            ANCIENT_TYPE -> {
                mAncientVO!!.title = mName
                mAncientVO!!.created_date = mCreatedDate
                mAncientVO!!.festival_date = mFestivalDate
                mAncientVO!!.about = mAbout
                mView.showProgressLoading()
                if (pickUpImages.size > 0) {
//                    ancientModel.deletePhotos(mAncientVO?.photos, mAncientVO!!.item_id)
//                    uploadPhotos(type)
//                        .observeForever {
//                            photoVOList = it
//                            mAncientVO!!.photos = photoVOList
//                            ancientModel.updateAncient(mAncientVO!!)
//                                .observeForever {
//                                    mView.successUpdatingItem(it)
//                                }
//                        }
                }
                else {
//                    ancientModel.updateAncient(mAncientVO!!)
//                        .observeForever {
//                            mView.successUpdatingItem(it)
//                        }
                }
            }

            FOOD_TYPE -> {

            }

            ACCESSORY_TYPE -> {

            }

            SUPPORT_TYPE -> {

            }
        }
    }

    private fun addAncient(name: String, created: String, festival: String, about: String, type: String) {
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
            userVO.user_id
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

        if (pickUpImages.size > 0) {
            var count = 0
            photoVOList = ArrayList()
            mView.showProgressLoading()
            uploadPhotos(type)
                .observeForever {
                    photoVOList = it
                    ancientVO.photos = photoVOList
//                    ancientModel.addAncient(ancientVO)
//                        .observeForever {str->
//                            mView.successAddingItem(str)
//                        }
                }
        } else {
            mView.showPhotoError("Pick up photo")
        }
    }

    private fun addView(name: String, created: String, festival: String, about: String, type: String) {
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
            userVO.user_id
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

        if (pickUpImages.size > 0) {
            var count = 0
            photoVOList = ArrayList()
            mView.showProgressLoading()
            uploadPhotos(type)
                .observeForever {
                    photoVOList = it
                    viewVO.photos = photoVOList
                    viewModel.addView(viewVO)
                        .observeForever {str->
                            mView.successAddingItem(str)
                        }
                }
        } else {
            mView.showPhotoError("Pick up photo")
        }
    }

    private fun addPagoda(name: String, created: String, festival: String, about: String, type: String) {
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
            userVO.user_id
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

        if (pickUpImages.size > 0) {
            var count = 0
            photoVOList = ArrayList()
            mView.showProgressLoading()

            uploadPhotos(type)
                .observeForever {
                    photoVOList = it
                    pagodaVO.photos = photoVOList

//                    pagodaModel.addPagoda(pagodaVO)
//                        .observeForever {str->
//                            mView.successAddingItem(str)
//                        }
                }
        } else {
            mView.showPhotoError("Pick up photo")
        }
    }

    fun checkValidate(et: EditText): Boolean {
        return Util.checkEditTextValidattion(et)
    }

    fun onEditPagodaDetails(pagodaVO: PagodaVO) {
        formType = "Update"
        mPagodaVO = pagodaVO
        photoVOList = ArrayList(pagodaVO.photos)
        itemId = pagodaVO.item_id
        pickUpImages.clear()
        existingImagesUris.clear()

        photoVOList.forEach {
            existingImagesUris.add(Uri.parse(it.url!!))
        }
        mView.showPickUpImages(existingImagesUris)
        mView.showEditDetails(
            pagodaVO.title!!,
            pagodaVO.created_date!!,
            pagodaVO.festival_date!!,
            pagodaVO.about!!,
            pagodaVO.item_type!!
        )
    }

    fun onEditViewDetails(viewVO: ViewVO) {
        formType = "Update"
        mViewVO = viewVO
        photoVOList = ArrayList(viewVO.photos)
        itemId = viewVO.item_id
        pickUpImages.clear()

        photoVOList.forEach {
            existingImagesUris.add(Uri.parse(it.url!!))
        }

        mView.showPickUpImages(existingImagesUris)
        mView.showEditDetails(
            viewVO.title!!,
            viewVO.created_date!!,
            viewVO.festival_date!!,
            viewVO.about!!,
            viewVO.item_type!!
        )
    }

    fun onEditAncientDetails(ancientVO: AncientVO) {
        formType = "Update"
        mAncientVO = ancientVO
        photoVOList = ArrayList(ancientVO.photos)
        itemId = ancientVO.item_id
        pickUpImages.clear()

        photoVOList.forEach {
            existingImagesUris.add(Uri.parse(it.url!!))
        }
        mView.showPickUpImages(existingImagesUris)
        mView.showEditDetails(
            ancientVO.title!!, ancientVO.created_date!!,
            ancientVO.festival_date!!, ancientVO.about!!, ancientVO.item_type!!
        )
    }

    @SuppressLint("CheckResult")
    private fun uploadPhotos(type : String) : LiveData<ArrayList<PhotoVO>>{

        photoVOList = ArrayList()
        val liveData = MutableLiveData<ArrayList<PhotoVO>>()
        mView.showProgressLoading()

        Util.getGeoPointsFromImageList(mContext,pickUpImages)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {geoPointsList->
                uploadModel.uploadPhoto(pickUpImages,geoPointsList,mContext,
                    {observable->
                        observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                liveData.value = it
                                photoVOList = it
                                it.forEachIndexed { index,photoVO->
                                    uploadModel.uploadPhotoUrl(photoVO.url!!,userVO.user_id,itemId,type,geoPointsList[index])
                                }
                            }
                    },
                    {

                    }
                )
            }

        return liveData
    }

    fun checkLanguage() {
        languageModel.getLanguage {
            language = it
            mView.checkLanguage(it)
        }
    }
}