package com.itachi.explore.mvp.presenters

import android.content.Intent
import com.itachi.core.domain.*
import com.itachi.explore.activities.PreviewActivity
import com.itachi.explore.mvvm.model.*
import com.itachi.explore.mvp.views.PreviewView
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.koin.core.KoinComponent
import org.koin.core.inject

class PreviewPresenter : BasePresenter<PreviewView>(),KoinComponent{

    private lateinit var uploadedPhotoVO : UploadedPhotoVO

    private lateinit var pagodaVO: PagodaVO

    private lateinit var viewVO : ViewVO

    private lateinit var ancientVO : AncientVO

    private lateinit var userVO : UserVO

    private lateinit var currentImgUrl : String

    private lateinit var mGeoPoints : String

//    private val pagodaModel : PagodaModelImpl by inject()

    private val viewsModel : ViewsModelImpl by inject()

//    private val ancientModel : AncientModelImpl by inject()

    private val userModel : UserModelImpl by inject()

    private val languageModel : LanguageModelImpl by inject()

    fun handleIntent(intent : Intent) {
        uploadedPhotoVO = intent.getSerializableExtra(PreviewActivity.EXTRA_EVENT_ID) as UploadedPhotoVO
        mView.showPreview(uploadedPhotoVO)
        mGeoPoints = uploadedPhotoVO.geo_points!!
        currentImgUrl = uploadedPhotoVO.url!!
        onCheckedGeoPoints()
    }

    fun getFullInfoItem(id : String) {
        when(uploadedPhotoVO.item_type) {
            PAGODA_TYPE -> {
//                pagodaModel.getPagodaById(id,
//                    {observable ->
//                        observable.subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe {
//                                pagodaVO = it
//                                mView.showFullInfo(it.title!!, ArrayList(it.photos))
//                            }
//                    },
//                    {
//                        mView.showError(it)
//                    })
            }
            VIEW_TYPE -> {
                viewsModel.getViewById(id,
                    {observable ->
                        observable.subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe {
                                viewVO = it
                                mView.showFullInfo(it.title!!, ArrayList(it.photos))
                            }
                    },
                    {
                        mView.showError(it)
                    })
            }
            ANCIENT_TYPE -> {
//                ancientModel.getAncientById(id,
//                    {observable ->
//                        observable.subscribeOn(Schedulers.io())
//                            .observeOn(AndroidSchedulers.mainThread())
//                            .subscribe {
//                                ancientVO = it
//                                mView.showFullInfo(it.title!!, it.photos!!)
//                            }
//                    },
//                    {
//                        mView.showError(it)
//                    })
            }

        }

    }

    private fun onCheckedGeoPoints() {
        if(mGeoPoints != "" && mGeoPoints != "0.0,0.0") {
            mView.isGeoPointsValid(true)
        }
        else mView.isGeoPointsValid(false)
    }

    fun onClickedNavigateToGoogleMap() {
        mView.navigateToGoogleMap(mGeoPoints)
    }

    fun onClickedBackButton() {
        mView.onClickedBackButton()
    }

    fun onClickedDetailsButton() {
        when(uploadedPhotoVO.item_type) {
            PAGODA_TYPE -> {
                mView.onClickedDetailButtonWithPagodaVO(pagodaVO)
            }
            VIEW_TYPE -> {
                mView.onClickedDetailButtonWithViewVO(viewVO)
            }
            ANCIENT_TYPE -> {
                mView.onClickedDetailButtonWithAncientVO(ancientVO)
            }
        }

    }

    fun onClickedImageItem(index : Int) {
        when(uploadedPhotoVO.item_type) {
            PAGODA_TYPE -> {
                pagodaVO.photos?.get(index)?.let {
                    mView.onClickedImageItem(it.url!!)
                    mGeoPoints = it.geo_points!!
                    currentImgUrl = it.url!!
                }
            }
            VIEW_TYPE -> {
                viewVO.photos?.get(index)?.let {
                    mView.onClickedImageItem(it.url!!)
                    mGeoPoints = it.geo_points!!
                    currentImgUrl = it.url!!
                }
            }
            ANCIENT_TYPE -> {
                ancientVO.photos?.get(index)?.let {
                    mView.onClickedImageItem(it.url!!)
                    mGeoPoints = it.geo_points!!
                    currentImgUrl = it.url!!
                }
            }
        }
        onCheckedGeoPoints()

    }

    fun getUploadedUser(id : String) {
        userModel.getUserById(id,
            {observable ->
                observable.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe {
                        userVO = it
                        mView.showUploader(it)
                    }

            },
            {
                it?.let { it1 -> mView.showError(it1) }
            })
    }

    fun onCheckedLanguage() {
        languageModel.getLanguage {
            mView.checkLanguage(it)
        }
    }

    fun onClickedImageView() {
        if(currentImgUrl!=null) {
            mView.showZoomageDialog(currentImgUrl)
        }
    }
}
