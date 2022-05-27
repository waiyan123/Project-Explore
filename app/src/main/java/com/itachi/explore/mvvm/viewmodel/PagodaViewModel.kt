package com.itachi.explore.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.itachi.core.domain.AncientVO
import com.itachi.core.domain.PagodaVO
import com.itachi.explore.activities.PagodasActivity
import com.itachi.explore.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class PagodaViewModel(interactors: Interactors) : AppViewmodel(interactors) {

    val bannerPhotos = MutableLiveData<ArrayList<String>>()
    val errorGettingBanner = MutableLiveData<String>()
    val errorGettingPagodaList = MutableLiveData<String>()
    val pagodaListOb = MutableLiveData<ArrayList<PagodaVO>>()
    val pagodaList = ArrayList<PagodaVO>()

    val pagodaItemOb = MutableLiveData<PagodaVO>()
    val checkLanguage = MutableLiveData<String>()

    init {
        getBannerPhotos()
        getPagodaList()
    }

    private fun getBannerPhotos(){
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
    }

    fun getPagodaList()  {

        GlobalScope.launch {
            Log.d("test---","pagoda list from room "+interactors.getAllPagodas.fromRoom().size)
            pagodaListOb.postValue(ArrayList(interactors.getAllPagodas.fromRoom()))
            interactors.getAllPagodas.fromFirebase(
                {list->
                    pagodaListOb.postValue(ArrayList(list))
                    pagodaList.clear()
                    pagodaList.addAll(list)
                    Log.d("test---","list "+list.size)
                    GlobalScope.launch {
                        interactors.addAllPagodas.toRoom(list)
                    }
                },
                {
                    errorGettingPagodaList.postValue(it)
                }
            )
        }

    }

    fun clickPagodaItem(index : Int) {
        if(pagodaList.isNotEmpty()) {
            pagodaItemOb.postValue(pagodaList[index])
        }
    }

    fun checkLanguage() : MutableLiveData<String>{
        checkLanguage.postValue("")
        return checkLanguage
    }
}