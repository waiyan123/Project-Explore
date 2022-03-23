package com.itachi.explore.mvvm.viewmodel

import android.content.SharedPreferences
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.itachi.core.domain.UploadedPhotoVO
import com.itachi.core.domain.ViewVO
import com.itachi.explore.framework.Interactors
import com.itachi.explore.utils.LANGUAGE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import me.myatminsoe.mdetect.MDetect
import org.koin.core.KoinComponent
import org.koin.core.inject

class ViewsViewModel(interactors : Interactors) : AppViewmodel(interactors),KoinComponent {

    private val uploadedPhotoVOList = MutableLiveData<ArrayList<UploadedPhotoVO>>()
    private val photoVoItem = MutableLiveData<UploadedPhotoVO>()
    private val errorMsg = MutableLiveData<String>()
    private val checkLanguage = MutableLiveData<String>()

    private val sharPreferences : SharedPreferences by inject()

    init {
        when(sharPreferences.getString(LANGUAGE,"en")) {
            null -> checkLanguage.postValue("en")
            "en" -> checkLanguage.postValue("en")
            "mm" -> {
                if(MDetect.isUnicode()){
                    checkLanguage.postValue("mm_unicode")
                }
                else {
                    checkLanguage.postValue("mm_zawgyi")
                }
            }
        }
    }

    fun getErrorMessage() : MutableLiveData<String> {
        return errorMsg
    }

    fun showPhotoList() : MutableLiveData<ArrayList<UploadedPhotoVO>>{
        GlobalScope.launch {
            interactors.getAllPhotoViews.fromFirebase(
                {
                    uploadedPhotoVOList.postValue(it)
                },
                {
                    errorMsg.postValue(it)
                }
            )
        }
        return uploadedPhotoVOList
    }

    fun clickViewItem(index : Int) : MutableLiveData<UploadedPhotoVO>{
        uploadedPhotoVOList.observeForever {
            photoVoItem.postValue(it[index])
        }
        return photoVoItem
    }

    fun checkLanguage() : MutableLiveData<String>{
        checkLanguage.postValue("")
        return checkLanguage
    }
}