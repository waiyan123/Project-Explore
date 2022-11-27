package com.itachi.explore.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.data.AncientRepository
import com.itachi.core.domain.AncientVO
import com.itachi.explore.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class AncientViewModel(interactors: Interactors) : AppViewmodel(interactors),KoinComponent {

    private val ancientList  = MutableLiveData<List<AncientVO>>()
    val errorStr = MutableLiveData<String>()
    private val ancientBgImage = MutableLiveData<String>()
    private val ancientItem = MutableLiveData<AncientVO>()
    private val checkLanguage = MutableLiveData<String>()

    fun getAncientBackground() : MutableLiveData<String>{
        viewModelScope.launch {
            interactors.getAncient.getBackground(
                {
                    ancientBgImage.postValue(it)
                },
                {
                    errorStr.postValue(it)
                }
            )
        }
        return ancientBgImage
    }

    fun getAncients() : MutableLiveData<List<AncientVO>>{
        viewModelScope.launch {
            ancientList.postValue(ArrayList(interactors.getAllAncient.fromRoom()))
            interactors.getAllAncient.fromFirebase().collect {resource->
                when(resource) {
                    is Resource.Success-> {
                        resource.data?.let {
                            ancientList.postValue(it)
                        }
                    }
                    is Resource.Error -> {
                        errorStr.postValue(resource.message)
                    }
                    is Resource.Loading -> {

                    }
                }


            }

        }
        return ancientList
    }

    fun clickAncientItem(index : Int) : MutableLiveData<AncientVO>{
        ancientList.observeForever { list->
            ancientItem.postValue(list[index])
        }
        return ancientItem
    }

    fun checkLanguage() : MutableLiveData<String>{
        checkLanguage.postValue("")
        return checkLanguage
    }
}