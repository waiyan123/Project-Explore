package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.AncientVO
import com.itachi.core.interactors.GetAllAncientUseCase
import com.itachi.core.interactors.GetAncientBackgroundUseCase
import com.itachi.core.interactors.GetLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AncientViewModel @Inject constructor(
    private val getAncientBackgroundUseCase: GetAncientBackgroundUseCase,
    private val getAllAncientUseCase: GetAllAncientUseCase,
    private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {

    private val ancientList  = MutableLiveData<List<AncientVO>>()
    val errorStr = MutableLiveData<String>()
    private val ancientBgImage = MutableLiveData<String>()
    private val ancientItem = MutableLiveData<AncientVO>()
    private val checkLanguage = MutableLiveData<String>()

    fun getAncientBg() : MutableLiveData<String>{
        viewModelScope.launch {
            getAncientBackgroundUseCase()
                .buffer()
                .collect(FlowCollector{ resource->
                    when(resource) {
                        is Resource.Success -> {
                            ancientBgImage.postValue(resource.data ?: "")
                        }
                        is Resource.Error -> {
                            errorStr.postValue(resource.message ?: "Unexpected error occur")
                        }
                        is Resource.Loading -> {

                        }
                    }
                })
        }
        return ancientBgImage
    }

    fun getAncients() : MutableLiveData<List<AncientVO>>{
        viewModelScope.launch {
            getAllAncientUseCase()
                .buffer()
                .collect {resource->
                    when(resource) {
                        is Resource.Success -> {
                            resource.data?.let {
                                ancientList.postValue(it)
                            }
                        }
                        is Resource.Error -> {
                            errorStr.postValue(resource.message ?: "Unexpected error occur!")
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
        viewModelScope.launch {
            getLanguageUseCase()
                .buffer()
                .collect {
                    checkLanguage.postValue(it)
                }
        }
        return checkLanguage
    }
}