package com.itachi.explore.mvvm.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.data.ViewRepository
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.explore.utils.LANGUAGE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.myatminsoe.mdetect.MDetect
import org.koin.core.KoinComponent
import javax.inject.Inject

@HiltViewModel
class ViewsViewModel @Inject constructor(
    private val viewRepository: ViewRepository,
    private val sharPreferences: SharedPreferences
) : ViewModel(), KoinComponent {

    private val uploadedPhotoVOList = MutableLiveData<ArrayList<UploadedPhotoVO>>()
    private val errorMsg = MutableLiveData<String>()
    private val checkLanguage = MutableLiveData<String>()

//    private val sharPreferences : SharedPreferences by inject()

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

    fun showPhotoList(): MutableLiveData<ArrayList<UploadedPhotoVO>> {
        viewModelScope.launch {
            viewRepository.getAllPhotoViewsFromFirebase(
                {
                    uploadedPhotoVOList.postValue(ArrayList(it))
                },
                {
                    errorMsg.postValue(it)
                }
            )
            viewRepository.getAllPhotoViewsFromFirebase(
                {
                    uploadedPhotoVOList.postValue(ArrayList(it))
                },
                {
                    errorMsg.postValue(it)
                }
            )
        }
        return uploadedPhotoVOList
    }

    fun checkLanguage(): MutableLiveData<String> {
        checkLanguage.postValue("")
        return checkLanguage
    }
}