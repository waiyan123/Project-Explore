package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.itachi.core.domain.AncientVO
import com.itachi.explore.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent

class AncientViewModel(interactors: Interactors) : AppViewmodel(interactors),KoinComponent {

    private val ancientList  = MutableLiveData<ArrayList<AncientVO>>()
    val errorStr = MutableLiveData<String>()
    private val ancientBgImage = MutableLiveData<String>()
    private val ancientItem = MutableLiveData<AncientVO>()
    private val checkLanguage = MutableLiveData<String>()

    fun getAncientBackground() : MutableLiveData<String>{
        GlobalScope.launch {
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

    fun getAncients() : MutableLiveData<ArrayList<AncientVO>>{
        GlobalScope.launch {
            ancientList.postValue(ArrayList(interactors.getAllAncient.fromRoom()))
            interactors.getAllAncient.fromFirebase(
                {
                    ancientList.postValue(ArrayList(it))
                    GlobalScope.launch {
                        interactors.addAllAncients.toRoom(it)
                    }
                },
                {
                    errorStr.postValue(it)
                }
            )

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