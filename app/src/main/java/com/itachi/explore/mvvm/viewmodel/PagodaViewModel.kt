package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO
import com.itachi.explore.activities.PagodasActivity
import com.itachi.explore.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PagodaViewModel(interactors: Interactors) : AppViewmodel(interactors) {

    private val bannerPhotos = MutableLiveData<ArrayList<String>>()
    val errorGettingBanner = MutableLiveData<String>()
    val errorGettingPagodaList = MutableLiveData<String>()
    private val pagodaList = MutableLiveData<ArrayList<PagodaVO>>()
    private val pagodaItem = MutableLiveData<PagodaVO>()
    private val checkLanguage = MutableLiveData<String>()

    fun getBannerPhotos() : MutableLiveData<ArrayList<String>>{
        GlobalScope.launch {
            interactors.getPagoda.getBanners(
                {
                    if(it.size>2) {
                        bannerPhotos.postValue(ArrayList(it))
                    } else errorGettingBanner.postValue("Banner photos are less than 2")
                },
                {
                    errorGettingBanner.postValue(it)
                }
            )
        }
        return bannerPhotos
    }

    fun getPagodaList() : MutableLiveData<ArrayList<PagodaVO>> {

        GlobalScope.launch {
            pagodaList.postValue(ArrayList(interactors.getAllPagodas.fromRoom()))
            interactors.getAllPagodas.fromFirebase(
                {list->
                    pagodaList.postValue(ArrayList(list))
                    GlobalScope.launch {
                        interactors.addAllPagodas.toRoom(list)
                    }
                },
                {
                    errorGettingPagodaList.postValue(it)
                }
            )
        }
        return pagodaList
    }

    fun clickPagodaItem(index : Int) : MutableLiveData<PagodaVO>{
        pagodaList.observeForever { list->
            pagodaItem.postValue(list[index])
        }
        return pagodaItem
    }

    fun checkLanguage() : MutableLiveData<String>{
        checkLanguage.postValue("")
        return checkLanguage
    }
}