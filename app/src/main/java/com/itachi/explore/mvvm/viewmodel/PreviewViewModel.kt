package com.itachi.explore.mvvm.viewmodel

import android.util.Log
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
    private val getPagodaByIdUseCase: GetPagodaByIdUseCase,
    private val getViewByIdUseCase: GetViewByIdUseCase,
    private val getAncientByIdUseCase: GetAncientByIdUseCase,
    private val getUser: GetUser,
    private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {

    private lateinit var uploadedPhotoVO: UploadedPhotoVO
    val uploadedPhotoVOLiveData = MutableLiveData<UploadedPhotoVO>()

    val mItemVO = MutableLiveData<ItemVO>()

    private lateinit var userVO: UserVO
    val uploaderVO = MutableLiveData<UserVO>()

    private lateinit var currentImgUrl: String

    val language = MutableLiveData<String>()

    val errorMessageLiveData = MutableLiveData<String>()

    fun handleIntent(photoVO: UploadedPhotoVO) {
        uploadedPhotoVO = photoVO
        uploadedPhotoVOLiveData.postValue(photoVO)
        currentImgUrl = photoVO.url
        getFullInfoItem()
        checkLanguage()
        getUploader(photoVO.uploader_id)
    }

    private fun getFullInfoItem() {
        when (uploadedPhotoVO.item_type) {
            PAGODA_TYPE -> {
                viewModelScope.launch {
                    getPagodaByIdUseCase(uploadedPhotoVO.item_id).collect { resource->
                        when(resource) {
                            is Resource.Success-> {
                                resource.data?.let {pagodaVO ->
                                    mItemVO.postValue(pagodaVO)
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
                    getViewByIdUseCase(uploadedPhotoVO.item_id)
                        .collect { resource->
                            when(resource) {
                                is Resource.Success -> {
                                    resource.data?.let {
                                        mItemVO.postValue(it)
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
                    getAncientByIdUseCase(uploadedPhotoVO.item_id)
                        .collect {resource->
                            when(resource) {
                                is Resource.Success -> {
                                    resource.data?.let {
                                        mItemVO.postValue(it)
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
        }
    }

    private fun getUploader(id : String) {
        viewModelScope.launch {
            getUser(id)
                .collect { resource->
                    resource.data?.let {
                        uploaderVO.postValue(it)
                        userVO = it
                    }
                }
        }
    }

    private fun checkLanguage() {
        viewModelScope.launch {
            getLanguageUseCase()
                .collect {
                    language.postValue(it)
                }
        }
    }
}