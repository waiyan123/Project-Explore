package com.itachi.explore.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.common.Resource
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.interactors.GetAllPagodas
import com.itachi.core.interactors.GetLanguage
import com.itachi.core.interactors.GetPagodaBanner
import com.itachi.explore.activities.PagodasActivity
import com.itachi.explore.framework.Interactors
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PagodaViewModel @Inject constructor(
    private val getPagodaBanner: GetPagodaBanner,
    private val getAllPagodas: GetAllPagodas,
    private val getLanguage: GetLanguage
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
            getPagodaBanner().collect {resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let { list->
                            if(list.size>2) {
                                bannerPhotos.postValue(ArrayList(list))
                            } else errorGettingBanner.postValue("Banner photos are less than 2")
                        }
                    }
                    is Resource.Error -> {
                        errorGettingBanner.postValue(resource.message)
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }

    fun getPagodaList()  {

        viewModelScope.launch {
            getAllPagodas().collect {resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let { list ->
                            pagodaListLiveData.postValue(list)
                            pagodaList.clear()
                            pagodaList.addAll(list)
                        }
                    }
                    is Resource.Error -> {
                        errorGettingPagodaList.postValue(resource.message)
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
            getLanguage().collect {lang->
                checkLanguage.postValue(lang)
            }
        }
        return checkLanguage
    }
}