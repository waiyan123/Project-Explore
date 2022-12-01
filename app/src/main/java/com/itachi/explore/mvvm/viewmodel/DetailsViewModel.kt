package com.itachi.explore.mvvm.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.itachi.core.domain.ItemVO
import com.itachi.core.domain.PagodaVO
import com.itachi.core.common.Resource
import com.itachi.core.interactors.DeletePagoda
import com.itachi.core.interactors.GetLanguage
import com.itachi.core.interactors.GetPagoda
import com.itachi.core.interactors.GetUser
import com.itachi.explore.framework.Interactors
import com.itachi.explore.mvvm.model.LanguageModelImpl
import com.itachi.explore.utils.ANCIENT_TYPE
import com.itachi.explore.utils.PAGODA_TYPE
import com.itachi.explore.utils.VIEW_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.koin.core.KoinComponent
import org.koin.core.inject
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    private val getPagoda : GetPagoda,
    private val getLanguage: GetLanguage,
    private val getUser: GetUser,
    private val deletePagoda: DeletePagoda
) : ViewModel(){

    val mItemVO = MutableLiveData<ItemVO>()
    val isUploader = MutableLiveData<Boolean>()
    val successMsg = MutableLiveData<String>()
    val errorMsg = MutableLiveData<String>()
    val language = MutableLiveData<String>()

    init {
        viewModelScope.launch {
            getLanguage()
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
                        getPagoda(itemVO.item_id).collect { resource->
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
                        deletePagoda(itemVO as PagodaVO).collect { resource->
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