package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.common.Resource
import com.itachi.core.interactors.DeletePagodaUseCase
import com.itachi.core.interactors.GetLanguageUseCase
import com.itachi.core.interactors.GetPagodaUseCase
import com.itachi.core.interactors.GetUser
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPagodaUseCase : GetPagodaUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val getUser: GetUser,
    private val deletePagodaUseCase: DeletePagodaUseCase
) : ViewModel(){

    val mItemVO = MutableLiveData<ItemVO>()
    val isUploader = MutableLiveData<Boolean>()
    val successMsg = MutableLiveData<String>()
    val errorMsg = MutableLiveData<String>()
    val language = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            getLanguageUseCase()
                .collect {
                    language.postValue(it)
                }
        }
    }

    fun refreshDetails() {
        mItemVO.observeForever {itemVO->
            when(itemVO.item_type){
                PAGODA_TYPE -> {
                    viewModelScope.launch {
                        getPagodaUseCase(itemVO.item_id).collect { resource->
                            when(resource) {
                                is Resource.Success -> {
                                    resource.data?.let { pagodaVO->
                                        mItemVO.postValue(pagodaVO)
                                    }
                                }
                                is Resource.Error -> {

                                }
                                is Resource.Loading -> {

                                }
                            }
                        }
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
        viewModelScope.launch {
            getUser().collect {resource->
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
                    viewModelScope.launch {
                        deletePagodaUseCase(itemVO as PagodaVO).collect { resource->
                            when(resource) {
                                is Resource.Success -> {
                                    resource.data?.let {strSuccess->
                                        successMsg.postValue(strSuccess)
                                    }
                                }
                                is Resource.Error -> {
                                    errorMsg.postValue(resource.message)
                                }
                                is Resource.Loading -> {

                                }
                            }
                        }
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