package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.models.UploadedPhotoVO
import com.itachi.core.domain.usecases.GetAllViewsPhotoUseCase
import com.itachi.core.domain.usecases.GetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewsViewModel @Inject constructor(
    private val getAllViewsPhotoUseCase: GetAllViewsPhotoUseCase,
    private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {

    private val uploadedPhotoVOList = MutableLiveData<List<UploadedPhotoVO>>()
    private val errorMsg = MutableLiveData<String>()
    private val checkLanguage = MutableLiveData<String>()


    init {

    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMsg
    }

    fun showPhotoList(): MutableLiveData<List<UploadedPhotoVO>> {
        viewModelScope.launch {
            getAllViewsPhotoUseCase()
                .buffer()
                .collect(FlowCollector{ resource->
                    when(resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                uploadedPhotoVOList.postValue(it)
                            }
                        }
                        is Resource.Error -> {
                            errorMsg.postValue(resource.message ?: "Unexpected error occur")
                        }
                        is Resource.Loading -> {

                        }
                    }
                })

        }
        return uploadedPhotoVOList
    }

    fun checkLanguage(): MutableLiveData<String> {
        viewModelScope.launch {
            getLanguageUseCase()
                .buffer()
                .collect(FlowCollector{
                    checkLanguage.postValue(it)
                })
        }
        return checkLanguage
    }
}