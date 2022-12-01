package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.AncientVO
import com.itachi.core.interactors.GetAllAncient
import com.itachi.core.interactors.GetAncientBackground
import com.itachi.core.interactors.GetAncientById
import com.itachi.explore.framework.Interactors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import javax.inject.Inject

@HiltViewModel
class AncientViewModel @Inject constructor(
    private val getAncientBackground: GetAncientBackground,
    private val getAllAncient: GetAllAncient
) : ViewModel() {

    private val ancientList  = MutableLiveData<List<AncientVO>>()
    val errorStr = MutableLiveData<String>()
    private val ancientBgImage = MutableLiveData<String>()
    private val ancientItem = MutableLiveData<AncientVO>()
    private val checkLanguage = MutableLiveData<String>()

    fun getAncientBg() : MutableLiveData<String>{
        viewModelScope.launch {
            getAncientBackground()
                .collect { resource->
                    when(resource) {
                        is Resource.Success -> {
                            ancientBgImage.postValue(resource.data)
                        }
                        is Resource.Error -> {
                            errorStr.postValue(resource.message)
                        }
                        is Resource.Loading -> {

                        }
                    }
                }
        }
        return ancientBgImage
    }

    fun getAncients() : MutableLiveData<List<AncientVO>>{
        viewModelScope.launch {
            getAllAncient()
                .collect {resource->
                    when(resource) {
                        is Resource.Success -> {
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