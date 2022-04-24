package com.itachi.explore.mvvm.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.itachi.core.data.FirestoreResult
import com.itachi.core.domain.AncientVO
import com.itachi.explore.framework.Interactors
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collect
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
        GlobalScope.async {
            ancientList.postValue(ArrayList(interactors.getAllAncient.fromRoom()))
            interactors.getAllAncient.fromFirebase(
                {
//                    ancientList.postValue(ArrayList(it))
//                    GlobalScope.launch {
//                        interactors.addAllAncients.toRoom(it)
//                    }
                    ancientList.postValue(ArrayList(it))
                Log.d("list---","list from onSuccess ${it.size}")
                },
                {
//                    errorStr.postValue(it)
                }
            ).collect {
                ancientList.postValue(ArrayList(it.data))
                Log.d("list---","List from Result ${it.data?.size}")
                errorStr.postValue(it.errorMessage)
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