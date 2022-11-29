package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.*
import com.itachi.core.interactors.*
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PreviewViewModel @Inject constructor(
    private val getPagoda: GetPagoda,
    private val getViewById: GetViewById,
//    private val getAncient: GetAncient,
    private val getUser: GetUser,
    private val getLanguage: GetLanguage
) : ViewModel() {

    private lateinit var uploadedPhotoVO: UploadedPhotoVO

    private val pagodaVOLiveData = MutableLiveData<PagodaVO>()

    private val viewVOLiveData = MutableLiveData<ViewVO>()

    private val ancientVOLiveData = MutableLiveData<AncientVO>()

    private lateinit var userVO: UserVO

    private lateinit var currentImgUrl: String

    val geoPointsAvailableLiveData = MutableLiveData<Boolean>()

    val errorMessageLiveData = MutableLiveData<String>()

    fun handleIntent(photoVO: UploadedPhotoVO) {
        uploadedPhotoVO = photoVO
        currentImgUrl = photoVO.url
        onCheckedGeoPoints(photoVO.geo_points)
    }

    private fun onCheckedGeoPoints(geoPoints: String) {
        if (geoPoints != "" && geoPoints != "0.0,0.0") {
            geoPointsAvailableLiveData.postValue(true)
        } else geoPointsAvailableLiveData.postValue(false)
    }


    fun getFullInfoItem(id: String) {
        when (uploadedPhotoVO.item_type) {
            PAGODA_TYPE -> {
                viewModelScope.launch {
                    getPagoda(id).collect {resource->
                        when(resource) {
                            is Resource.Success-> {
                                resource.data?.let {pagodaVO ->
                                    pagodaVOLiveData.postValue(pagodaVO)
                                }
                            }
                            is Resource.Error-> {
                                errorMessageLiveData.postValue(resource.message)
                            }
                            is Resource.Loading -> {

                            }
                        }
                    }
                }
            }
            VIEW_TYPE -> {
                viewModelScope.launch {
                    getViewById(id)
                        .collect { resource->
                            when(resource) {
                                is Resource.Success -> {
                                    resource.data?.let {
                                        viewVOLiveData.postValue(it)
                                    }
                                }
                                is Resource.Error -> {
                                    errorMessageLiveData.postValue(resource.message)
                                }
                                is Resource.Loading -> {

                                }
                            }
                        }
                }
            }
            ANCIENT_TYPE -> {
                viewModelScope.launch {
//                    getAncient.fromFirebase(id,
//                        {
//                            ancientVOLiveData.postValue(it)
//                        },
//                        {
//                            errorMessageLiveData.postValue(it)
//                        })
                }
            }

        }

    }
}