package com.itachi.explore.mvvm.viewmodel

import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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
class ViewsViewModel @Inject constructor(private val viewRepository: ViewRepository,
private val sharPreferences : SharedPreferences) : ViewModel(),KoinComponent {

    private val uploadedPhotoVOList = MutableLiveData<ArrayList<UploadedPhotoVO>>()
    private val photoVoItem = MutableLiveData<UploadedPhotoVO>()
    private val errorMsg = MutableLiveData<String>()
    private val checkLanguage = MutableLiveData<String>()

//    private val sharPreferences : SharedPreferences by inject()

    init {
        when(sharPreferences.getString(LANGUAGE,"en")) {
            null -> checkLanguage.postValue("en")
            "en" -> checkLanguage.postValue("en")
            "mm" -> {
                if(MDetect.isUnicode()){
                    Log.d("test---","mm unicode")
                    checkLanguage.postValue("mm_unicode")
                }
                else {
                    Log.d("test---","mm zawgyi")
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
//            interactors.getAllPhotoViews.fromFirebase(
//                {
//                    uploadedPhotoVOList.postValue(ArrayList(it))
//                },
//                {
//                    errorMsg.postValue(it)
//                }
//            )
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