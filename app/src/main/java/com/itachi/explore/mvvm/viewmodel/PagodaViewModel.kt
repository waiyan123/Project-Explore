package com.itachi.explore.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.models.PagodaVO
import com.itachi.core.domain.usecases.GetAllPagodasUseCase
import com.itachi.core.domain.usecases.GetLanguageUseCase
import com.itachi.core.domain.usecases.GetPagodaBannerUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagodaViewModel @Inject constructor(
    private val getPagodaBannerUseCase: GetPagodaBannerUseCase,
    private val getAllPagodasUseCase: GetAllPagodasUseCase,
    private val getLanguageUseCase: GetLanguageUseCase
) : ViewModel() {

    val bannerPhotos = MutableLiveData<ArrayList<String>>()
    val errorGettingBanner = MutableLiveData<String>()
    val errorGettingPagodaList = MutableLiveData<String>()
    val pagodaListLiveData = MutableLiveData<List<PagodaVO>>()
    val pagodaList = ArrayList<PagodaVO>()

    val pagodaItemOb = MutableLiveData<PagodaVO>()
    val checkLanguage = MutableLiveData<String>()

    init {
        getBannerPhotos()
        getPagodaList()
    }

    private fun getBannerPhotos(){
        viewModelScope.launch {
            getPagodaBannerUseCase()
                .buffer()
                .collect { resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let { list->
                            if(list.size>2) {
                                bannerPhotos.postValue(ArrayList(list))
                            } else errorGettingBanner.postValue("Banner photos are less than 2")
                        }
                    }
                    is Resource.Error -> {
                        errorGettingBanner.postValue(resource.message ?: "Unexpected error occur")
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun getPagodaList()  {

        viewModelScope.launch {
            getAllPagodasUseCase()
                .buffer()
                .collect { resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let { list ->
                            Log.d("test---","pagoda list in viewmodel ${list.size}")
                            pagodaListLiveData.postValue(list)
                            pagodaList.clear()
                            pagodaList.addAll(list)
                        }
                    }
                    is Resource.Error -> {
                        errorGettingPagodaList.postValue(resource.message ?: "Unexpected error occur")
                    }
                    is Resource.Loading -> {

                    }
                }
            }

        }

    }

    fun clickPagodaItem(index : Int) {
        if(pagodaList.isNotEmpty()) {
            pagodaItemOb.postValue(pagodaList[index])
        }
    }

    fun checkLanguage() : MutableLiveData<String>{
        viewModelScope.launch {
            getLanguageUseCase()
                .buffer()
                .collect { lang->
                checkLanguage.postValue(lang)
            }
        }
        return checkLanguage
    }
}