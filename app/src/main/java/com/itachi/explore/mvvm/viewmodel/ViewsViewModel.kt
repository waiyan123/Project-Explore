package com.itachi.explore.mvvm.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.data.ViewRepository
import com.itachi.core.data.ViewRepositoryImpl
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.interactors.GetAllViewsPhotoUseCase
import com.itachi.core.interactors.GetLanguageUseCase
import com.itachi.explore.utils.LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.myatminsoe.mdetect.MDetect
import org.koin.core.KoinComponent
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
                .collect {resource->
                    when(resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                uploadedPhotoVOList.postValue(it)
                            }
                        }
                        is Resource.Error -> {
                            errorMsg.postValue(resource.message)
                        }
                        is Resource.Loading -> {

                        }
                    }
                }

        }
        return uploadedPhotoVOList
    }

    fun checkLanguage(): MutableLiveData<String> {
        viewModelScope.launch {
            getLanguageUseCase()
                .collect {
                    checkLanguage.postValue(it)
                }
        }
        return checkLanguage
    }
}