package com.itachi.explore.mvvm.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.data.ViewRepository
import com.itachi.core.data.ViewRepositoryImpl
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.explore.utils.LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import me.myatminsoe.mdetect.MDetect
import org.koin.core.KoinComponent
import javax.inject.Inject

@HiltViewModel
class ViewsViewModel @Inject constructor(
    private val viewRepository: ViewRepository,
    private val sharPreferences: SharedPreferences
) : ViewModel() {

    private val uploadedPhotoVOList = MutableLiveData<List<UploadedPhotoVO>>()
    private val errorMsg = MutableLiveData<String>()
    private val checkLanguage = MutableLiveData<String>()


    init {
        when (sharPreferences.getString(LANGUAGE, "en")) {
            null -> checkLanguage.postValue("en")
            "en" -> checkLanguage.postValue("en")
            "mm" -> {
                if (MDetect.isUnicode()) {
                    checkLanguage.postValue("mm_unicode")
                } else {
                    checkLanguage.postValue("mm_zawgyi")
                }
            }
        }
    }

    fun getErrorMessage(): MutableLiveData<String> {
        return errorMsg
    }

    fun showPhotoList(): MutableLiveData<List<UploadedPhotoVO>> {
        viewModelScope.launch {
            viewRepository.getAllViewsPhoto()
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
        checkLanguage.postValue("")
        return checkLanguage
    }
}