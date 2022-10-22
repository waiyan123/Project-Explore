package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.common.Resource
import com.itachi.explore.framework.Interactors
import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject

class DetailsViewModel(interactors: Interactors) : AppViewmodel(interactors),KoinComponent{

    private val languageModel : LanguageModelImpl by inject()

    val mItemVO = MutableLiveData<ItemVO>()
    val isUploader = MutableLiveData<Boolean>()
    val successMsg = MutableLiveData<String>()
    val errorMsg = MutableLiveData<String>()
    val language = MutableLiveData<String>()

    init {
        languageModel.getLanguage {
            language.postValue(it)
        }
    }

    fun refreshDetails() {
        mItemVO.observeForever {
            when(it.item_type){
                PAGODA_TYPE -> {
                    GlobalScope.launch {
                        interactors.getPagoda.fromFirebase(it.item_id,
                            {pagodaVO->
                                mItemVO.postValue(pagodaVO as ItemVO)
                            },
                            {

                            })
                    }
                }
                VIEW_TYPE -> {

                }
                ANCIENT_TYPE -> {

                }

            }
        }
    }


    fun setupItemVO(itemVO: ItemVO) {
        mItemVO.postValue(itemVO)
        GlobalScope.launch {
            interactors.getUser().collect {resource->
                when(resource) {
                    is Resource.Success -> {
                        resource.data?.let {
                            isUploader.postValue(itemVO.uploader_id==it.user_id)
                        }
                    }
                    is Resource.Error -> {
                        resource.message?.let {
                            errorMsg.postValue(it)
                        }
                    }
                    is Resource.Loading -> {

                    }
                }
            }
        }
    }
    fun deleteItem() {
        mItemVO.observeForever { itemVO->
            when(itemVO.item_type) {
                PAGODA_TYPE->{
                    GlobalScope.launch {
                        interactors.deletePagoda.fromFirebase(itemVO as PagodaVO,
                            {
                                successMsg.postValue(it)
                            },
                            {
                                errorMsg.postValue(it)
                            })
                        interactors.deletePagoda.fromRoom(itemVO)
                    }
                }
                VIEW_TYPE->{

                }
                ANCIENT_TYPE->{

                }
            }
        }
    }

}